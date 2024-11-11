package com.enotes.api.dto;

import com.enotes.api.entity.Category;
import com.enotes.api.entity.FileDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    @NotBlank
    private String title;
    private String description;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;
    private Category category;
    private FileDetails fileDetails;
}
