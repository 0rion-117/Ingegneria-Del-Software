package it.afam.view;

import it.afam.control.feedback.GestioneNotificheCtl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ViewBannerNotifica {
    @FXML private Label testoLabel;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void MostraBannerNotifica(String testoNotifica) {
        testoLabel.setText(testoNotifica);
    }

    public void ClickChiudi() {
        new GestioneNotificheCtl().PresaVisioneNotifica();
        ChiudiBannerNotifica();
    }

    public void ChiudiBannerNotifica() {
        if (stage != null) {
            stage.close();
        }
    }
}
