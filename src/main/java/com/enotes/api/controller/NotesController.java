package com.enotes.api.controller;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.entity.FileDetails;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
        if(!CollectionUtils.isEmpty(notesDtoList)){
            return new ResponseEntity<>(notesDtoList, HttpStatus.FOUND);
        }
        return null;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws FileNotFoundException, IOException {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);
        HttpHeaders headers = new HttpHeaders();
        String contentType = getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/getAllNotes/{id}")
    public ResponseEntity<?> getAllNotesByUser(@PathVariable Integer id){
        notesService.getAllNotesByUser(id);
        return null;
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateNotes(String notes, @RequestParam MultipartFile file, @PathVariable String title) throws IOException {
       NotesDto notesDto = notesService.updateNotes(notes, file, title);
       if(!ObjectUtils.isEmpty(notesDto)){
           return new ResponseEntity<>("Notes updated successfully with this title : "+title, HttpStatus.ACCEPTED);
       }
        return null;

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteNotes(@PathVariable String name){
        boolean response = notesService.deleteNotes(name);
        if(response){
            return new ResponseEntity<>("Notes deleted successfully!", HttpStatus.ACCEPTED);
        }
        return null;
    }

    public static String getContentType(String originalFileName){
        String extension = FilenameUtils.getExtension(originalFileName);

        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plan";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpg";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default:
                return "application/octet-stream";
        }
    }
}
