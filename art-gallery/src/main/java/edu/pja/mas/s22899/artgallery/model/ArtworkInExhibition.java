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
        @UniqueConstraint(columnNames = {"artwork_id", "exhibition_id"})
})
public class ArtworkInExhibition {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    @NotNull(message = "Artwork Must Be Specified.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Artwork artwork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    @NotNull(message = "Exhibition Must Be Specified.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Exhibition exhibition;

    @NotNull(message = "Display Start Date Is Required.")
    @Column(name = "display_start_date", nullable = false)
    private LocalDate displayStartDate;

    @NotNull(message = "Display End Date Is Required.")
    @Column(name = "display_end_date", nullable = false)
    private LocalDate displayEndDate;

    @Transient
    @AssertTrue(message = "Display End Date Must Be After or Equal To Display Start Date.")
    public boolean isValidDateRange() {
        if (displayStartDate == null || displayEndDate == null) {
            return true;
        }
        return !displayEndDate.isBefore(displayStartDate);
    }

    @Transient
    @AssertTrue(message = "The Artwork Display Period Must Be Within The Exhibition's Own Start And End Dates.")
    public boolean isDisplayPeriodWithinExhibitionPeriod() {
        if (exhibition == null || exhibition.getStartDate() == null || exhibition.getEndDate() == null || displayStartDate == null || displayEndDate == null) {
            return true;
        }
        return !displayStartDate.isBefore(exhibition.getStartDate()) && !displayEndDate.isAfter(exhibition.getEndDate());
    }
}