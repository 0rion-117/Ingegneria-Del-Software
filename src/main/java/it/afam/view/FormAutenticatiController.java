package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.control.account.GestioneAutenticazioneCtl;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FormAutenticatiController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    public void MostraFormAutenticati() {
    }

    public void CompilaEmail(String email) {
        emailField.setText(email);
    }

    public void CompilaPassword(String password) {
        passwordField.setText(password);
    }

    public void CompilaCampi(String email, String password) {
        CompilaEmail(email);
        CompilaPassword(password);
    }

    public void ClickContinua() {
        new GestioneAutenticazioneCtl().InviaCredenziali(emailField.getText(), passwordField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraModuloLogin();
    }
}
