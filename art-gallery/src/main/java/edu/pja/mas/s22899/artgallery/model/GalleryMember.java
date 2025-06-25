package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class GalleryMember {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "First Name Is Required.")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last Name Is Required.")
    @Size(min = 2, max = 50)
    private String lastName;

    @Past(message = "Date Of Birth Must Be In The Past!")
    private LocalDate dateOfBirth;

    @Email
    @NotBlank(message = "Email Is Required.")
    private String email;

    @Size(max = 1000)
    private String biography;

    // Associations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gallery employedBy;

    // Methods For Managing Association
    public void setGallery(Gallery gallery) {
        if (this.employedBy == gallery) {
            return;
        }
        if (this.employedBy != null) {
            this.employedBy.getMembers().remove(this);
        }
        this.employedBy = gallery;
        if (gallery != null) {
            gallery.getMembers().add(this);
        }
    }

    public void removeGallery() {
        setGallery(null);
    }
}