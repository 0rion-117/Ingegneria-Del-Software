package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.account.GestioneAutenticazioneSPIDCtl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewElencoProvider {
    @FXML private ListView<String> providerList;

    @FXML
    public void initialize() {
        providerList.setItems(FXCollections.observableArrayList(Sessione.getProvider()));
        if (!providerList.getItems().isEmpty()) {
            providerList.getSelectionModel().selectFirst();
        }
    }

    public void MostraElencoProviderBoundary() {
    }

    public void SelezionaFornitoreIdentitaDigitale(String provider) {
        new GestioneAutenticazioneSPIDCtl().InviaProviderSelezionato(provider);
    }

    public void ClickSelezionaProvider() {
        SelezionaFornitoreIdentitaDigitale(providerList.getSelectionModel().getSelectedItem());
    }

    public void ClickIndietro() {
        Navigazione.MostraModuloLogin();
    }
}
