package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Find artists by their last name
    List<Artist> findByLastNameIgnoreCase(String lastName);
}