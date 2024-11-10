package com.enotes.api.service;

import com.enotes.api.dto.NotesDto;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public interface NotesService {
    NotesDto createNotes(@Valid NotesDto notesDto);

    List<NotesDto> getAllNotes();
}
