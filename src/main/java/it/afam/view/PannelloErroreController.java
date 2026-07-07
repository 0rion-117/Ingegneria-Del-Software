package it.afam.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PannelloErroreController {
    @FXML private Label messaggioLabel;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void MostraPannelloErrore(String messaggio) {
        messaggioLabel.setText(messaggio);
    }

    public void ClickChiudi() {
        ChiudiPannello();
    }

    public void ChiudiPannello() {
        if (stage != null) {
            stage.close();
        }
    }
}
