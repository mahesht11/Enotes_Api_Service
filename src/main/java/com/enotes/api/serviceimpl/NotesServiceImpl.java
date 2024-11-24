package com.enotes.api.serviceimpl;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.dto.NotesResponse;
import com.enotes.api.entity.Category;
import com.enotes.api.entity.FileDetails;
import com.enotes.api.entity.Notes;
import com.enotes.api.exception.IllegalFileFormatException;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.exception.ResourseExistException;
import com.enotes.api.repository.CategoryRepository;
import com.enotes.api.repository.FileRepository;
import com.enotes.api.repository.NotesRepository;
import com.enotes.api.service.NotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    ModelMapper modelMapper;

    //@Value("${file.upload.path}")
    public static final String uploadPath = "notes/";

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public NotesDto createNotes(String note, MultipartFile file) throws IOException {
        log.info(" NotesServiceImpl Class and createNotes method : ");
        ObjectMapper objectMapper = new ObjectMapper();
        NotesDto notesDto = objectMapper.readValue(note, NotesDto.class);

        FileDetails fileDetails = saveFileDetails(file);
        if(ObjectUtils.isEmpty(fileDetails))
            throw new ResourceNotFoundException(" file details not saved");

        Notes notes = notesRepository.findByTitle(notesDto.getTitle());
        if(ObjectUtils.isEmpty(notes)){
            Category category = categoryRepository.findByName(notesDto.getCategory().getName()).orElseThrow(() -> new ResourceNotFoundException("Category is not available to this notes with name : "+notesDto.getTitle()));
            Notes notes1 = modelMapper.map(notesDto, Notes.class);
            if(!ObjectUtils.isEmpty(category)) {
                notes1.setCategory(category);
            }
            notes1.setCreatedOn(new Date());
            notes1.setFileDetails(fileDetails);
            Notes notes2 = notesRepository.saveAndFlush(notes1);
            return modelMapper.map(notes2, NotesDto.class);
        }else{
            throw new ResourseExistException("Given notes already existed!");
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        String originalName  = file.getOriginalFilename();
        String extraction = FilenameUtils.getExtension(originalName);
        List<String> extentionTypes = Arrays.asList("pdf","xml","docx","jpg","jpeg");
        if(!extentionTypes.contains(extraction)){
            throw new IllegalFileFormatException("file format should be in jpg, jpeg, pdf, xml and docx!");
        }

        if(!file.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            File saveFile = new File(uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdir();
            }
            String storePath = uploadPath.concat(uuid+"."+extension);

            FileDetails fileDetails = FileDetails.builder()
                    .originalFileName(file.getOriginalFilename())
                    .uploadFileName(uuid+"."+extension)
                    .displayName(getDisplayName(file.getOriginalFilename()))
                    .filePath(storePath)
                    .size(file.getSize()).build();
           long upload =  Files.copy(file.getInputStream(), Paths.get(storePath));
           if(upload!=0){
             return fileRepository.saveAndFlush(fileDetails);
           }
        }
        return null;
    }

    private String getDisplayName(String originalFilename) {

        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if(fileName.length() > 8){
            fileName = fileName.substring(0,7);
        }
        fileName = fileName+"."+extension;
        return fileName;
    }

    @Override
    public List<NotesDto> getAllNotes() {
        log.info(" NotesServiceImpl Class and getAllNotes method : ");
        List<Notes> notesList = notesRepository.findAll();
        List<NotesDto> notesDtoList =  notesList.stream()
                .map(note ->modelMapper.map(note, NotesDto.class)).collect(Collectors.toList());
        List<NotesDto> notesDtoList1 = new ArrayList<>();
       for(NotesDto dto : notesDtoList){
           Category category = categoryRepository.findById(dto.getCategory().getCategoryId()).orElseThrow(() ->new ResourceNotFoundException("Category is not available to this notes!"));
           if(!ObjectUtils.isEmpty(category)){
           dto.setCategory(category);
           }
           notesDtoList1.add(dto);
       }
       return notesDtoList1;
    }

    /**
     * @param title
     * @return
     */
    @Override
    public boolean deleteNotes(String title) {
       Notes notes = notesRepository.findByTitle(title);
       if(ObjectUtils.isEmpty(notes)){
           throw new ResourceNotFoundException("Notes is not available in DB with this title : "+title);
       }else{
          notesRepository.delete(notes);
          return true;
       }
    }

    /**
     * @param note
     * @param file
     * @param title
     * @return
     */
    @Override
    public NotesDto updateNotes(String note, MultipartFile file, String title) throws JsonProcessingException, IOException {
        Notes notes = notesRepository.findByTitle(title);
        if(ObjectUtils.isEmpty(notes)){
            throw new ResourceNotFoundException("Notes is not available in DB with this title : "+title);
        }else{
            ObjectMapper mapper = new ObjectMapper();
            NotesDto notesDto1 = mapper.readValue(note, NotesDto.class);
            Notes notes1 = modelMapper.map(note, Notes.class);
            FileDetails fileDetails = saveFileDetails(file);
            notes.setFileDetails(fileDetails);
            notes.setUpdatedOn(new Date());
            notes.setUpdatedBy(1);
            notes.setDescription(notes1.getDescription().length()>0?notes1.getDescription():notes.getDescription());
            Notes notes2 = notesRepository.saveAndFlush(notes1);
            return modelMapper.map(notes2, NotesDto.class);
        }
    }

    /**
     * @param id
     */
    @Override
    public NotesResponse getAllNotesByUser(Integer id) {
        Pageable pageable = PageRequest.of(1,5);
        Page<Notes> notesPage = notesRepository.findByCreatedBy(id, pageable);
        List<NotesDto> notesDtos = notesPage.get().map(m -> modelMapper.map(m, NotesDto.class)).collect(Collectors.toList());
        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDtos)
                .pageNo(notesPage.getNumber())
                .pageSize(notesPage.getSize())
                .totalElements(notesPage.getNumberOfElements())
                .totalPages(notesPage.getTotalPages())
                .isFirst(notesPage.isFirst())
                .isLast(notesPage.isLast())
                .build();
        return notesResponse;

    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws FileNotFoundException, IOException {
        InputStream io = new FileInputStream(fileDetails.getFilePath());
        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id){
        return fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File details not available with this id : "+id));

    }
}
