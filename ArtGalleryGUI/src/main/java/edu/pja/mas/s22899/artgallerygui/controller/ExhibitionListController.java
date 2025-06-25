package edu.pja.mas.s22899.artgallerygui.controller;

import edu.pja.mas.s22899.artgallerygui.ApiService;
import edu.pja.mas.s22899.artgallerygui.MainApplication;
import edu.pja.mas.s22899.artgallerygui.dto.ExhibitionSummaryDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ExhibitionListController {
    @FXML
    private ListView<ExhibitionSummaryDTO> exhibitionListView;
    @FXML
    private Button viewDetailsButton;
    @FXML
    private ProgressIndicator loadingIndicator;
    private final ApiService apiService = new ApiService();

    @FXML
    public void initialize() {
        viewDetailsButton.setDisable(true);
        exhibitionListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> viewDetailsButton.setDisable(newSelection == null)
        );

        exhibitionListView.setCellFactory(param -> new ListCell<>() {
            private final VBox vbox = new VBox();
            private final Label titleLabel = new Label();
            private final Label datesLabel = new Label();

            {
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.1em;");
                datesLabel.setStyle("-fx-font-size: 0.9em; -fx-text-fill: rgba(255, 255, 255, 0.7);");

                vbox.getChildren().addAll(titleLabel, datesLabel);
                vbox.setSpacing(4);
            }

            @Override
            protected void updateItem(ExhibitionSummaryDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setText(item.getTitle());
                    setStyle("-fx-font-size: 1.2em; -fx-padding: 3px;");
                }
            }
        });
        loadExhibitions();
    }

    private void loadExhibitions() {
        loadingIndicator.setVisible(true);
        exhibitionListView.setVisible(false);

        new Thread(() -> {
            try {
                var exhibitions = apiService.getAllExhibitions();
                Platform.runLater(() -> {
                    exhibitionListView.setItems(FXCollections.observableArrayList(exhibitions));
                    loadingIndicator.setVisible(false);
                    exhibitionListView.setVisible(true);
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Failed To Load Exhibitions: " + e.getMessage()));
            }
        }).start();
    }


    @FXML
    protected void handleViewDetailsClick() {
        ExhibitionSummaryDTO selected = exhibitionListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("exhibition-detail-view.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Exhibition Details");
            stage.setScene(new Scene(loader.load(), 700, 500));

            ExhibitionDetailController controller = loader.getController();
            controller.loadExhibitionData(selected.getId());
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Could Not Open The Detail View.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
