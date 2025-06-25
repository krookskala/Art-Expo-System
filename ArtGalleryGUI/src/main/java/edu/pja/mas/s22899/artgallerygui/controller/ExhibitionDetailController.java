package edu.pja.mas.s22899.artgallerygui.controller;

import edu.pja.mas.s22899.artgallerygui.ApiService;
import edu.pja.mas.s22899.artgallerygui.dto.ArtworkSummaryDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExhibitionDetailController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label datesLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ProgressIndicator artworkLoadingIndicator;
    @FXML
    private TableView<ArtworkSummaryDTO> artworksTable;
    @FXML
    private TableColumn<ArtworkSummaryDTO, String> titleColumn;
    @FXML
    private TableColumn<ArtworkSummaryDTO, String> artistColumn;
    @FXML
    private TableColumn<ArtworkSummaryDTO, Integer> yearColumn;
    @FXML
    private TableColumn<ArtworkSummaryDTO, String> genreColumn;

    private final ApiService apiService = new ApiService();


    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistNames"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("creationYear"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

    public void loadExhibitionData(long exhibitionId) {
        artworkLoadingIndicator.setVisible(true);
        artworksTable.setVisible(false);

        new Thread(() -> {
            try {
                var exhibitionDetails = apiService.getExhibitionDetails(exhibitionId);
                var associatedArtworks = apiService.getArtworksForExhibition(exhibitionId);

                Platform.runLater(() -> {
                    titleLabel.setText(exhibitionDetails.getTitle());
                    typeLabel.setText(exhibitionDetails.getType());
                    datesLabel.setText(exhibitionDetails.getStartDate() + " to " + exhibitionDetails.getEndDate());
                    descriptionLabel.setText(exhibitionDetails.getDescription());

                    artworksTable.setItems(FXCollections.observableArrayList(associatedArtworks));

                    if (associatedArtworks.isEmpty()) {
                        artworksTable.setPlaceholder(new Label("This Exhibition Currently Has No Artworks Assigned To It."));
                    }

                    artworkLoadingIndicator.setVisible(false);
                    artworksTable.setVisible(true);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    artworkLoadingIndicator.setVisible(false);
                    titleLabel.setText("Failed To Load Exhibition Data.");
                    artworksTable.setPlaceholder(new Label("Error: " + e.getMessage()));
                    artworksTable.setVisible(true);
                });
            }
        }).start();
    }
}