package com.enotes.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull(message = "name should not be empty")
    @NotBlank(message = "name should not be blank")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 20, message = "name length should be 3 to 20")
    private String name;
    @NotNull(message = "description should not be empty")
    @NotBlank(message = "name should not be blank")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 20, message = "name length should be 3 to 20")
    private String description;
    private Boolean isActive;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;
}
