package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.account.GestioneAutenticazioneSPIDCtl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProviderBoundaryController {
    @FXML private Label providerLabel;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    public void initialize() {
        providerLabel.setText(Sessione.getProviderSelezionato());
    }

    public void MostraProviderBoundary(String provider) {
        providerLabel.setText(provider);
    }

    public void CompilaCredenzialiProvider(String email, String password) {
        emailField.setText(email);
        passwordField.setText(password);
    }

    public void RichiediAutenticazioneProvider(String email, String password) {
    }

    public void ClickConferma() {
        new GestioneAutenticazioneSPIDCtl().InviaCredenzialiProvider(emailField.getText(), passwordField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraElencoProviderBoundary();
    }
}
