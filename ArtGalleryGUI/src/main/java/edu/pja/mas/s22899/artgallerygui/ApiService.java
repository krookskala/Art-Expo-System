package edu.pja.mas.s22899.artgallerygui;

import edu.pja.mas.s22899.artgallerygui.dto.ArtworkSummaryDTO;
import edu.pja.mas.s22899.artgallerygui.dto.ExhibitionDetailDTO;
import edu.pja.mas.s22899.artgallerygui.dto.ExhibitionSummaryDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiService {
    // A single HttpClient can be reused for all requests.
    private final HttpClient client = HttpClient.newHttpClient();

    // The ObjectMapper is used to convert JSON to Java objects. It's thread-safe.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // The base URL of backend API.
    private final String BASE_URL = "http://localhost:8080/api";

    public ApiService() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Fetches a list of all exhibitions from the backend.
     * Corresponds to the GET /api/exhibitions endpoint.
     * @return A list of ExhibitionSummaryDTO objects.
     * @throws Exception if the network request fails or JSON parsing fails.
     */
    public List<ExhibitionSummaryDTO> getAllExhibitions() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/exhibitions"))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // The TypeReference is needed to tell Jackson to convert the JSON array into a List of our DTOs.
        return objectMapper.readValue(response.body(), new TypeReference<List<ExhibitionSummaryDTO>>() {});
    }

    /**
     * Fetches the detailed information for a single exhibition.
     * Corresponds to the GET /api/exhibitions/{id} endpoint.
     * @param id The ID of the exhibition to fetch.
     * @return An ExhibitionDetailDTO object.
     * @throws Exception if the network request fails or JSON parsing fails.
     */
    public ExhibitionDetailDTO getExhibitionDetails(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/exhibitions/" + id))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), ExhibitionDetailDTO.class);
    }

    /**
     * Fetches the list of artworks associated with a single exhibition.
     * Corresponds to the GET /api/exhibitions/{id}/artworks endpoint.
     * @param id The ID of the exhibition.
     * @return A list of ArtworkSummaryDTO objects.
     * @throws Exception if the network request fails or JSON parsing fails.
     */
    public List<ArtworkSummaryDTO> getArtworksForExhibition(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/exhibitions/" + id + "/artworks"))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<ArtworkSummaryDTO>>() {});
    }
}