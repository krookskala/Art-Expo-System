package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "individual_temporary_exhibition")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class IndividualTemporaryExhibition extends TemporaryExhibition {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "featured_artist_id", nullable = false)
    @NotNull(message = "Individual Exhibition Must Have A Featured Artist.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Artist featuredArtist;
}
