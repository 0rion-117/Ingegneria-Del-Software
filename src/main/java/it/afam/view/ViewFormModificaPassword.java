package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.control.account.GestioneModificaPasswordCtl;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class ViewFormModificaPassword {
    @FXML private PasswordField passwordCorrenteField;
    @FXML private PasswordField nuovaPasswordField;
    @FXML private PasswordField confermaNuovaPasswordField;

    public void MostraFormModificaPassword() {
    }

    public void InserisciDatiPassword(String passwordCorrente, String nuovaPassword, String confermaNuovaPassword) {
        passwordCorrenteField.setText(passwordCorrente);
        nuovaPasswordField.setText(nuovaPassword);
        confermaNuovaPasswordField.setText(confermaNuovaPassword);
    }

    public void ClickConferma() {
        new GestioneModificaPasswordCtl().InviaDatiPassword(
                passwordCorrenteField.getText(),
                nuovaPasswordField.getText(),
                confermaNuovaPasswordField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraGestioneProfilo();
    }
}
