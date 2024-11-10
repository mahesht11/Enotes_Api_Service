package com.enotes.api.serviceImpl;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.entity.Category;
import com.enotes.api.entity.Notes;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.exception.ResourseExistException;
import com.enotes.api.repository.CategoryRepository;
import com.enotes.api.repository.NotesRepository;
import com.enotes.api.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public NotesDto createNotes(NotesDto notesDto) {
        log.info(" NotesServiceImpl Class and createNotes method : ");
        Notes notes = notesRepository.findByTitle(notesDto.getTitle());
        if(ObjectUtils.isEmpty(notes)){
            Optional<Category> category = categoryRepository.findByName(notesDto.getCategory().getName());
            Notes notes1 = modelMapper.map(notesDto, Notes.class);
            if(category.isPresent()) {
                notes1.setCategoryId(category.get().getCategoryId());
            }else{
                throw new ResourceNotFoundException("Category is not available to this notes with name : "+notesDto.getTitle());
            }
            notes1.setCreatedOn(new Date());
            Notes notes2 = notesRepository.saveAndFlush(notes1);
            return modelMapper.map(notes2, NotesDto.class);

        }else{
            throw new ResourseExistException("Given notes already existed!");
        }
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
