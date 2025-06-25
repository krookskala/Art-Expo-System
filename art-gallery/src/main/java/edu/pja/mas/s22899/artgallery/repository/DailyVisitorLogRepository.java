package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.DailyVisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyVisitorLogRepository extends JpaRepository<DailyVisitorLog, Long> {
}
