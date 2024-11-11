package com.enotes.api.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name="FILEDETAILS")
@NoArgsConstructor
@AllArgsConstructor
public class FileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;
    private String uploadFileName;
    private String originalFileName;
    private String displayName;
    private String filePath;
    private Long size;
}
