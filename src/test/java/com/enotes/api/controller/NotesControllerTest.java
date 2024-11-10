package com.enotes.api.controller;

import static org.mockito.Mockito.when;

import com.enotes.api.dto.NotesDto;
import com.enotes.api.entity.Category;
import com.enotes.api.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {NotesController.class})
@ExtendWith(SpringExtension.class)
class NotesControllerTest {
    @Autowired
    private NotesController notesController;

    @MockBean
    private NotesService notesService;

    /**
     * Test {@link NotesController#createNotes(NotesDto)}.
     * <p>
     * Method under test: {@link NotesController#createNotes(NotesDto)}
     */
    @Test
    @DisplayName("Test createNotes(NotesDto)")
    void testCreateNotes() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId(1);
        category.setCreatedBy(1);
        category.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        category.setDescription("The characteristics of someone or something");
        category.setIsActive(true);
        category.setIsDeleted(true);
        category.setName("Name");
        category.setUpdatedBy(1);
        category.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        NotesDto.NotesDtoBuilder createdByResult = NotesDto.builder().category(category).createdBy(1);
        NotesDto.NotesDtoBuilder updatedByResult = createdByResult
                .createdOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .description("The characteristics of someone or something")
                .title("Dr")
                .updatedBy(1);
        NotesDto buildResult = updatedByResult
                .updatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .build();
        when(notesService.createNotes(Mockito.<NotesDto>any())).thenReturn(buildResult);

        Category category2 = new Category();
        category2.setCategoryId(1);
        category2.setCreatedBy(1);
        category2.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        category2.setDescription("The characteristics of someone or something");
        category2.setIsActive(true);
        category2.setIsDeleted(true);
        category2.setName("Name");
        category2.setUpdatedBy(1);
        category2.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

        NotesDto notesDto = new NotesDto();
        notesDto.setCategory(category2);
        notesDto.setCreatedBy(1);
        notesDto.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        notesDto.setDescription("The characteristics of someone or something");
        notesDto.setTitle("Dr");
        notesDto.setUpdatedBy(1);
        notesDto.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        String content = (new ObjectMapper()).writeValueAsString(notesDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/notes/save-notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(notesController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Notes saved Successfully!"));
    }

    /**
     * Test {@link NotesController#getNotes()}.
     * <p>
     * Method under test: {@link NotesController#getNotes()}
     */
    @Test
    @DisplayName("Test getNotes()")
    void testGetNotes() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId(1);
        category.setCreatedBy(1);
        category.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        category.setDescription("The characteristics of someone or something");
        category.setIsActive(true);
        category.setIsDeleted(true);
        category.setName("Name");
        category.setUpdatedBy(1);
        category.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        NotesDto.NotesDtoBuilder createdByResult = NotesDto.builder().category(category).createdBy(1);
        NotesDto.NotesDtoBuilder updatedByResult = createdByResult
                .createdOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .description("The characteristics of someone or something")
                .title("Dr")
                .updatedBy(1);
        NotesDto buildResult = updatedByResult
                .updatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .build();

        ArrayList<NotesDto> notesDtoList = new ArrayList<>();
        notesDtoList.add(buildResult);
        when(notesService.getAllNotes()).thenReturn(notesDtoList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/notes/");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(notesController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"createdBy\":1,\"updatedBy\""
                                        + ":1,\"createdOn\":0,\"updatedOn\":0,\"category\":{\"categoryId\":1,\"name\":\"Name\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"isActive\":true,\"isDeleted\":true,\"createdBy\":1,\"createdOn\":0,\"updatedBy\":1"
                                        + ",\"updatedOn\":0}}]"));
    }
}
