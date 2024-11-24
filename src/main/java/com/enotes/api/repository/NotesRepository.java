package com.enotes.api.repository;

import com.enotes.api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {
    Notes findByTitle(@NotBlank String title);

    Page<Notes> findByCreatedBy(Integer id, Pageable pageable);
}
