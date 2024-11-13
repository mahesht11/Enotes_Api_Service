package com.enotes.api.service;

import com.enotes.api.dto.NotesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public interface NotesService {
    NotesDto createNotes(String note, MultipartFile file) throws IOException;

    List<NotesDto> getAllNotes();
}
