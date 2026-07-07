package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.control.account.GestioneRecuperoPasswordCtl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormRecuperoPasswordController {
    @FXML private TextField emailField;

    public void MostraFormRecuperoPassword() {
    }

    public void InserisciEmail(String email) {
        emailField.setText(email);
    }

    public void ClickConferma() {
        new GestioneRecuperoPasswordCtl().InviaEmailRecupero(emailField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraModuloLogin();
    }
}
