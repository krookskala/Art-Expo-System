package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    // Find exhibitions that are currently active
    List<Exhibition> findByEndDateAfter(LocalDate date);

    // Find exhibitions by a part of their title
    List<Exhibition> findByTitleContainingIgnoreCase(String title);

    // This finds all exhibitions of a specific type (for example only permanent ones).
    @Query("SELECT e FROM Exhibition e WHERE TYPE(e) = :type")
    <T extends Exhibition> List<T> findByType(@Param("type") Class<T> type);
}
