package com.enotes.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="NOTES")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name="category", nullable=false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="filedetails", nullable=false)
    private FileDetails fileDetails;
}
