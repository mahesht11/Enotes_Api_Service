package com.enotes.api.service;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.dto.NotesResponse;
import com.enotes.api.entity.FileDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface NotesService {
    NotesDto createNotes(String note, MultipartFile file) throws IOException;

    List<NotesDto> getAllNotes();

    boolean deleteNotes(String name);

    NotesDto updateNotes(String notes, MultipartFile file, String name) throws JsonProcessingException, IOException;

    NotesResponse getAllNotesByUser(Integer id);

    byte[] downloadFile(FileDetails fileDetails) throws FileNotFoundException, IOException;

    public FileDetails getFileDetails(Integer id);
}
