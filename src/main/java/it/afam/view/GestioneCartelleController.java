package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.condivisione.GestioneCondivisioneCtl;
import it.afam.control.condivisione.GestioneCreaCartellaCtl;
import it.afam.control.condivisione.GestioneEliminazioneCartellaCtl;
import it.afam.control.condivisione.GestioneModificaCartellaCtl;
import it.afam.control.condivisione.GestioneVisualizzaCartellaCtl;
import it.afam.control.feedback.GestioneStatisticheCtl;
import it.afam.model.Cartella;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class GestioneCartelleController {
    @FXML private ListView<Cartella> cartelleList;

    @FXML
    public void initialize() {
        cartelleList.setItems(FXCollections.observableArrayList(
                new GestioneVisualizzaCartellaCtl().CaricaCartelleStudente(Sessione.getIdStudente())));
    }

    public void ClickCreaCartella() {
        new GestioneCreaCartellaCtl().AvviaCreaCartella();
    }

    public void ClickModificaCartella() {
        Cartella cartella = selezionaCartella();
        if (cartella != null) {
            ClickModificaCartella(cartella.getIdCartella());
        }
    }

    public void ClickModificaCartella(int idCartella) {
        new GestioneModificaCartellaCtl().AvviaModificaCartella(idCartella);
    }

    public void ClickEliminaCartella() {
        Cartella cartella = selezionaCartella();
        if (cartella != null) {
            ClickEliminaCartella(cartella.getIdCartella());
        }
    }

    public void ClickEliminaCartella(int idCartella) {
        new GestioneEliminazioneCartellaCtl().AvviaEliminaCartella(idCartella);
    }

    public void ClickCartella() {
        Cartella cartella = selezionaCartella();
        if (cartella != null) {
            ClickCartella(cartella.getIdCartella());
        }
    }

    public void ClickCartella(int idCartella) {
        new GestioneVisualizzaCartellaCtl().AvviaVisualizzaCartella(idCartella);
    }

    public void ClickCondividiCartella() {
        Cartella cartella = selezionaCartella();
        if (cartella != null) {
            ClickCondividiCartella(cartella.getIdCartella());
        }
    }

    public void ClickCondividiCartella(int idCartella) {
        new GestioneCondivisioneCtl().AvvioCondivisione(idCartella);
    }

    public void ClickVisualizzaStatistiche() {
        Cartella cartella = selezionaCartella();
        if (cartella != null) {
            ClickVisualizzaStatistiche(cartella.getIdCartella());
        }
    }

    public void ClickVisualizzaStatistiche(int idCartella) {
        new GestioneStatisticheCtl().AvviaStatisticheCartella(idCartella);
    }

    public void MostraGestioneCartelle() {
    }

    public void MostraGestioneCartelleAggiornata() {
        initialize();
    }

    public void ClickIndietro() {
        Navigazione.MostraInterfacciaPrincipaleStudente();
    }

    private Cartella selezionaCartella() {
        Cartella cartella = cartelleList.getSelectionModel().getSelectedItem();
        if (cartella == null) {
            Navigazione.MostraPannelloErrore("Input non valido");
        }
        return cartella;
    }
}
