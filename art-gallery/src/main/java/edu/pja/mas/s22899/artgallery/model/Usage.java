package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"exhibition_id", "section_id"})
})
public class Usage {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    @NotNull(message = "Exhibition Must Be Specified.")
    private Exhibition exhibition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @NotNull(message = "Section Must Be Specified.")
    private Section section;

    @NotNull(message = "Date From Is Required.")
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @NotNull(message = "Date To Is Required.")
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Transient
    @AssertTrue(message = "Display End Date Must Be After or Equal To Display Start Date.")
    public boolean isValidDateRange() {
        if (dateFrom == null || dateTo == null) {
            return true;
        }
        return !dateTo.isBefore(dateFrom);
    }

    @Transient
    @AssertTrue(message = "The Usage Period Must Be Within The Exhibition's Own Start And End Dates.")
    public boolean isUsagePeriodWithinExhibitionPeriod() {
        if (exhibition == null || exhibition.getStartDate() == null || exhibition.getEndDate() == null || dateFrom == null || dateTo == null) {
            return true;
        }
        return !dateFrom.isBefore(exhibition.getStartDate()) && !dateTo.isAfter(exhibition.getEndDate());
    }
}