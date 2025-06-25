package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.Curator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuratorRepository extends JpaRepository<Curator, Long> {

    // Find curators by their specialization
    List<Curator> findBySpecializationContainingIgnoreCase(String specialization);
}