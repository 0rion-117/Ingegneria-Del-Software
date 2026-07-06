package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.control.account.GestioneRegistrazioneCtl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ViewFormRegistrati {
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confermaPasswordField;
    @FXML private DatePicker dataNascitaPicker;
    @FXML private TextField codiceFiscaleField;
    @FXML private TextField usernameField;

    public void MostraFormRegistrati() {
    }

    public void CompilaCampiRegistrazione(String nome, String cognome, String email, String password,
                                          String confermaPassword, LocalDate dataNascita,
                                          String codiceFiscale, String username) {
        nomeField.setText(nome);
        cognomeField.setText(cognome);
        emailField.setText(email);
        passwordField.setText(password);
        confermaPasswordField.setText(confermaPassword);
        dataNascitaPicker.setValue(dataNascita);
        codiceFiscaleField.setText(codiceFiscale);
        usernameField.setText(username);
    }

    public void CompilaEmail(String email) {
        emailField.setText(email);
    }

    public void CompilaPassword(String password) {
        passwordField.setText(password);
    }

    public void CompilaConfermaPassword(String confermaPassword) {
        confermaPasswordField.setText(confermaPassword);
    }

    public void CompilaCodiceFiscale(String codiceFiscale) {
        codiceFiscaleField.setText(codiceFiscale);
    }

    public void CompilaUsername(String username) {
        usernameField.setText(username);
    }

    public void ClickContinua() {
        new GestioneRegistrazioneCtl().InviaDatiRegistrazione(
                nomeField.getText(),
                cognomeField.getText(),
                emailField.getText(),
                passwordField.getText(),
                confermaPasswordField.getText(),
                dataNascitaPicker.getValue(),
                codiceFiscaleField.getText(),
                usernameField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraModuloLogin();
    }
}
