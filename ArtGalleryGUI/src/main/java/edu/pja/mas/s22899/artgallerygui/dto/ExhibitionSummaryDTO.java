package edu.pja.mas.s22899.artgallerygui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionSummaryDTO {
    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}