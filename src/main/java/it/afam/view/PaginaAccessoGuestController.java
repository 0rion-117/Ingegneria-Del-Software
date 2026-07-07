package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.feedback.GestioneAccessoEsternoCtl;
import it.afam.model.Contenuto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class PaginaAccessoGuestController {
    @FXML private ListView<Contenuto> contenutiList;

    @FXML
    public void initialize() {
        MostraPaginaAccessoGuest(Sessione.getContenutiCartellaGuest());
    }

    public void ClickLinkDedicato(String linkDedicato) {
        new GestioneAccessoEsternoCtl().AvviaAccessoTramiteLink(linkDedicato);
    }

    public void MostraPaginaAccessoGuest(List<Contenuto> contenutiCartella) {
        contenutiList.setItems(FXCollections.observableArrayList(contenutiCartella));
    }

    public void ClickChiudi() {
        Navigazione.MostraModuloLogin();
    }
}
