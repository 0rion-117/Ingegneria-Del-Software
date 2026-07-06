package it.afam.view;

import it.afam.app.Sessione;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ViewPannelloStatistiche {
    @FXML private TextArea datiArea;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void MostraPannelloStatistiche(Object datiStatistici) {
        Sessione.setDatiStatistici(datiStatistici);
        datiArea.setText(Sessione.formattaDatiStatistici());
    }

    public void VisualizzaDati() {
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
