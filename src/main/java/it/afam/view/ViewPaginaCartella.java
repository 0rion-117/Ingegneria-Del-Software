package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.model.Contenuto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class ViewPaginaCartella {
    @FXML private ListView<Contenuto> contenutiList;

    @FXML
    public void initialize() {
        MostraPaginaCartella(Sessione.getListaContenuti());
    }

    public void MostraPaginaCartella(List<Contenuto> contenutiCartella) {
        contenutiList.setItems(FXCollections.observableArrayList(contenutiCartella));
    }

    public void ClickIndietro() {
        Navigazione.MostraGestioneCartelle();
    }
}
