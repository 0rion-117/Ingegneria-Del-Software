package it.afam.view;

import it.afam.control.account.GestioneAutenticazioneCtl;
import it.afam.control.account.GestioneAutenticazioneSPIDCtl;
import it.afam.control.account.GestioneRecuperoPasswordCtl;
import it.afam.control.account.GestioneRegistrazioneCtl;
import it.afam.control.feedback.GestioneCercaProfiloCtl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewModuloLogin {
    @FXML
    private TextField usernameField;

    public void ClickRegistrati() {
        new GestioneRegistrazioneCtl().AvviaRegistrazione();
    }

    public void ClickAccedi() {
        new GestioneAutenticazioneCtl().AvviaAutenticazione();
    }

    public void ClickAccediConSPID() {
        new GestioneAutenticazioneSPIDCtl().AvviaAutenticazioneSPID();
    }

    public void SelezionaRecuperoPassword() {
        new GestioneRecuperoPasswordCtl().AvviaRecuperoPassword();
    }

    public void ClickBarraRicerca() {
        usernameField.requestFocus();
    }

    public void InserisciUsername(String usernameStudente) {
        usernameField.setText(usernameStudente);
    }

    public void ClickCerca() {
        new GestioneCercaProfiloCtl().InviaUsernameRicerca(usernameField.getText());
    }

    public void MostraModuloLogin() {
    }
}
