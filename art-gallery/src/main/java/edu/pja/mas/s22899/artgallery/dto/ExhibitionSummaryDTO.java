package edu.pja.mas.s22899.artgallery.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExhibitionSummaryDTO {
    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
