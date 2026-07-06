package it.afam.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ViewPannelloAvviso {
    @FXML private Label messaggioLabel;
    @FXML private Button confermaButton;
    private Stage stage;
    private boolean confermato;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void MostraPannelloAvviso(String messaggio) {
        messaggioLabel.setText(messaggio);
    }

    public void setConfermaVisibile(boolean confermaVisibile) {
        confermaButton.setVisible(confermaVisibile);
        confermaButton.setManaged(confermaVisibile);
    }

    public void setTestoConferma(String testoConferma) {
        confermaButton.setText(testoConferma);
    }

    public boolean isConfermato() {
        return confermato;
    }

    public void ClickConferma() {
        confermato = true;
        ChiudiPannello();
    }

    public void ClickEliminaAccount() {
        ClickConferma();
    }

    public void ClickChiudi() {
        confermato = false;
        ChiudiPannello();
    }

    public void ChiudiPannello() {
        if (stage != null) {
            stage.close();
        }
    }
}
