package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "exhibitions_base")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Exhibition {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Title Is Required.")
    @Size(min = 2, max = 255, message = "Title Must Be Between 2 and 255 Characters.")
    private String title;

    @NotNull(message = "Start Date Is Required.")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End Date Is Required.")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Transient
    @AssertTrue(message = "End Date Must Be After Start Date.")
    public boolean isValidPeriod() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }

    @Size(max = 1000, message = "Description Must Be At Most 1000 Characters.")
    private String description;

    @NotNull(message = "Artwork Capacity Must Be Specified.")
    @Min(value = 0, message = "Artwork Capacity Cannot Be Negative.")
    private int artworkCapacity;

    // Associations
    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<ArtworkInExhibition> artworkDisplayLinks = new HashSet<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Usage> sectionUsages = new HashSet<>();

    // Methods
    public void addArtworkToDisplay(Artwork artwork, LocalDate displayStart, LocalDate displayEnd) {
        if (this.artworkDisplayLinks.size() >= this.artworkCapacity) {
            throw new IllegalStateException("Cannot add artwork. Exhibition '" + this.title + "' is at full capacity.");
        }
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null.");
        }

        ArtworkInExhibition link = ArtworkInExhibition.builder()
                .artwork(artwork)
                .exhibition(this)
                .displayStartDate(displayStart)
                .displayEndDate(displayEnd)
                .build();
        this.addArtworkLink(link);
        artwork.addExhibitionLink(link);
    }

    public void removeArtworkFromDisplay(ArtworkInExhibition link) {
        if (link != null && link.getExhibition() == this) {
            this.removeArtworkLink(link);
            link.getArtwork().removeExhibitionLink(link);
        }
    }

    private void addArtworkLink(ArtworkInExhibition link) {
        if (link != null) {
            artworkDisplayLinks.add(link);
        }
    }

    private void removeArtworkLink(ArtworkInExhibition link) {
        artworkDisplayLinks.remove(link);
    }

    public void addSectionUsage(Usage usage) {
        if (usage != null && !this.sectionUsages.contains(usage)) {
            this.sectionUsages.add(usage);
            usage.setExhibition(this);
        }
    }

    public void removeSectionUsage(Usage usage) {
        if (usage != null && this.sectionUsages.remove(usage)) {
            usage.setExhibition(null);
        }
    }

    public abstract void closeExhibition();

    public Set<Artwork> getAssociatedArtworks() {
        return this.artworkDisplayLinks.stream()
                .map(ArtworkInExhibition::getArtwork)
                .collect(Collectors.toSet());
    }
}