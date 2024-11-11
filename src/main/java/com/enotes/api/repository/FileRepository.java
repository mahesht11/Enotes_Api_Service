package com.enotes.api.repository;

import com.enotes.api.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
