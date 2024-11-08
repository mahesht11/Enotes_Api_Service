package com.enotes.api.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;

}
