package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Artist extends GalleryMember {
    // Attributes
    @NotBlank(message = "Art Style Is Required.")
    @Column(name = "art_style", nullable = false)
    private String artStyle;

    @NotBlank(message = "Primary Medium Is Required.")
    @Column(name = "primary_medium", nullable = false)
    private String primaryMedium;

    // Associations
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "artist_awards", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "award_description")
    @Builder.Default
    private Set<String> awards = new HashSet<>();

    @ManyToMany(mappedBy = "creators", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Artwork> createdArtworks = new HashSet<>();

    // Methods
    public void addCreatedArtwork(Artwork artwork) {
        if (artwork != null && !this.createdArtworks.contains(artwork)) {
            this.createdArtworks.add(artwork);
            artwork.getCreators().add(this);
        }
    }

    public void removeCreatedArtwork(Artwork artwork) {
        if (artwork != null && this.createdArtworks.contains(artwork)) {
            this.createdArtworks.remove(artwork);
            artwork.getCreators().remove(this);
        }
    }
}