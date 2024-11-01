package com.enotes.api.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Data
@Builder
@Entity
@MappedSuperclass
public class BaseModel {
}
