package com.enotes.api.controller;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    @PostMapping("/save-notes")
    public ResponseEntity<String> createNotes(String notes, @RequestParam MultipartFile file) throws IOException {
        log.info(" NotesController class and createNotes method : ");
        NotesDto notesDto1 = notesService.createNotes(notes,file);
        if(ObjectUtils.isEmpty(notesDto1)){
            return new ResponseEntity<>("Notes is not saved successfully!", HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>("Notes saved Successfully!", HttpStatus.CREATED);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<NotesDto>> getNotes(){
        log.info(" NotesController class and getNotes method : ");
        List<NotesDto> notesDtoList = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notesDtoList)){
            throw new ResourceNotFoundException("List of Notes not found!");
        }else{
            return new ResponseEntity<>(notesDtoList, HttpStatus.FOUND);
        }
    }
}
