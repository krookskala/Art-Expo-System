package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"section_id", "log_date"})
})
public class DailyVisitorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @NotNull
    private Section section;

    @NotNull
    @PastOrPresent
    @Column(name = "log_date", nullable = false)
    private LocalDate date;

    @PositiveOrZero
    @Column(name = "visitor_count", nullable = false)
    private int visitorCount;
}
