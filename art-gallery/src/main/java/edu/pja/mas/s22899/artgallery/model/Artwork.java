package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artwork {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Title Is Required.")
    @Size(min = 2, max = 100, message = "Title Must Be Between 2 and 100 Characters.")
    private String title;

    @PositiveOrZero(message = "Price Must Be Between Zero Or Positive.")
    private double price;

    @Min(value = 1000, message = "Creation Year Must Be 1000 or Later.")
    @Max(value = 2025, message = "Creation Year Must Not Exceed The Current Year.")
    private int creationYear;

    @Enumerated
    @NotNull(message = "Art Genre Must Be Specified.")
    private ArtGenre genre;

    @Size(max = 1000, message = "Description Must Be At Most 1000 Characters.")
    private String description;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Inventory Number Is Required.")
    private int inventoryNumber;

    // Associations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cataloguing_gallery_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gallery galleryCatalogueLink;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "artwork_creators",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @NotEmpty(message = "Artwork Must Have At Least One Creator.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Artist> creators = new HashSet<>();

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<ArtworkInExhibition> exhibitionDisplayLinks = new HashSet<>();

    // Methods
    public void setGalleryCatalogueLink(Gallery gallery) {
        if (this.galleryCatalogueLink == gallery) {
            return;
        }
        if (this.galleryCatalogueLink != null) {
            this.galleryCatalogueLink.getCataloguedArtworks().remove(this.getInventoryNumber());
        }
        this.galleryCatalogueLink = gallery;
        if (gallery != null) {
            gallery.getCataloguedArtworks().put(this.getInventoryNumber(), this);
        }
    }

    public void addCreator(Artist artist) {
        if (artist != null && !this.creators.contains(artist)) {
            this.creators.add(artist);
            artist.getCreatedArtworks().add(this);
        }
    }

    public void removeCreator(Artist artist) {
        if (artist != null && this.creators.remove(artist)) {
            artist.getCreatedArtworks().remove(this);
        }
    }

    public void addExhibitionLink(ArtworkInExhibition link) {
        if (link != null && !this.exhibitionDisplayLinks.contains(link)) {
            this.exhibitionDisplayLinks.add(link);
            link.setArtwork(this);
        }
    }

    public void removeExhibitionLink(ArtworkInExhibition link) {
        if (link != null && this.exhibitionDisplayLinks.remove(link)) {
            link.setArtwork(null);
        }
    }

    public String getArtworkDetails() {
        String creatorNames = this.creators.stream()
                .map(creator -> creator.getFirstName() + " " + creator.getLastName())
                .collect(Collectors.joining(", "));
        return String.format(
                "Artwork Details:\n" +
                        "\tTitle: %s (Inv# %d)\n" +
                        "\tCreators: %s\n" +
                        "\tGenre: %s\n" +
                        "\tCreated: %d\n" +
                        "\tPrice: $%.2f",
                this.title, this.inventoryNumber, creatorNames, this.genre, this.creationYear, this.price
        );
    }

    @Transient
    public int getTotalVisitorsView() {
        int totalVisitors = 0;
        for (ArtworkInExhibition artworkLink : this.getExhibitionDisplayLinks()) {
            Exhibition exhibition = artworkLink.getExhibition();
            LocalDate artworkDisplayStart = artworkLink.getDisplayStartDate();
            LocalDate artworkDisplayEnd = artworkLink.getDisplayEndDate();

            if (exhibition == null) continue;

            for (Usage usage : exhibition.getSectionUsages()) {
                Section section = usage.getSection();
                if (section == null || section.getVisitorLogs() == null) continue;

                totalVisitors += section.getVisitorLogs().stream()
                        .filter(log -> !log.getDate().isBefore(artworkDisplayStart) && !log.getDate().isAfter(artworkDisplayEnd))
                        .mapToInt(DailyVisitorLog::getVisitorCount)
                        .sum();
            }
        }
        return totalVisitors;
    }
}
