package com.enotes.api.serviceimpl;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.entity.Category;
import com.enotes.api.entity.FileDetails;
import com.enotes.api.entity.Notes;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.exception.ResourseExistException;
import com.enotes.api.repository.CategoryRepository;
import com.enotes.api.repository.FileRepository;
import com.enotes.api.repository.NotesRepository;
import com.enotes.api.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
            Optional<Category> category = categoryRepository.findByName(notesDto.getCategory().getName());
            Notes notes1 = modelMapper.map(notesDto, Notes.class);
            if(category.isPresent()) {
                notes1.setCategory(category.get());
            }else{
                throw new ResourceNotFoundException("Category is not available to this notes with name : "+notesDto.getTitle());
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
           Optional<Category> category = categoryRepository.findById(dto.getCategory().getCategoryId());
           if(category.isPresent()){
           dto.setCategory(category.get());
           }else{
               throw new ResourceNotFoundException("Category is not available to this notes!");
           }
           notesDtoList1.add(dto);
       }
       return notesDtoList1;
    }
}
