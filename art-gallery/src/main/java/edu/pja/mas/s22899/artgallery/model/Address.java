package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @NotBlank(message = "Street Is Required.")
    @Size(max = 100)
    private String street;

    @NotBlank(message = "City Is Required.")
    @Size(max = 100)
    private String city;

    @NotBlank(message = "Postal Code Is Required.")
    @Size(max = 10)
    private String postalCode;

    @NotBlank(message = "Country Is Required.")
    @Size(max = 100)
    private String country;
}
