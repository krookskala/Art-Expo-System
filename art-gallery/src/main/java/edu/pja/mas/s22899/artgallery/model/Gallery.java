package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gallery {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Gallery Name Is Required.")
    @Size(min = 2, max = 255)
    private String name;

    @Embedded
    @NotNull(message = "Address Must Be Provided.")
    private Address address;

    @Min(value = 1500, message = "Established Year Must Be After 1500.")
    @Max(value = 2025, message = "Established Year Cannot Be In The Future.")
    private int establishedYear;


    // Associations

    // Aggregation: The lifecycle of a GalleryMember is independent of the Gallery.
    @OneToMany(mappedBy = "employedBy", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GalleryMember> members = new HashSet<>();

    // Composition: The lifecycle of a Section depends on the Gallery.
    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Section> sections = new HashSet<>();

    // Qualified Association: The lifecycle of an Artwork is independent.
    @OneToMany(mappedBy = "galleryCatalogueLink", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapKey(name = "inventoryNumber")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Map<Integer, Artwork> cataloguedArtworks = new HashMap<>();

    // Methods
    public void addMember(GalleryMember member) {
        if (member != null && !this.members.contains(member)) {
            member.setGallery(this);
        }
    }

    public void removeMember(GalleryMember member) {
        if (member != null && this.members.contains(member)) {
            member.removeGallery();
        }
    }

    public void addSection(Section section) {
        if (section != null && !this.sections.contains(section)) {
            this.sections.add(section);
            section.setGallery(this);
        }
    }

    public void removeSection(Section section) {
        if (section != null && this.sections.remove(section)) {
            section.setGallery(null);
        }
    }

    public void addArtworkToCatalogue(Artwork artwork) {
        if (artwork != null && artwork.getInventoryNumber() != 0) {
            artwork.setGalleryCatalogueLink(this);
        }
    }

    public void removeArtworkFromCatalogue(Artwork artwork) {
        if (artwork != null && this.cataloguedArtworks.containsKey(artwork.getInventoryNumber())) {
            artwork.setGalleryCatalogueLink(null);
        }
    }

    public Artwork getArtworkByInventoryNumber(Integer inventoryNumber) {
        return this.cataloguedArtworks.get(inventoryNumber);
    }
}