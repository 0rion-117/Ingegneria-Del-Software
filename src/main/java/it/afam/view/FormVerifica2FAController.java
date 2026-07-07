package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.control.account.GestioneVerifica2FACtl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormVerifica2FAController {
    @FXML private TextField codiceField;

    public void MostraFormVerifica2FA() {
    }

    public void InserisciCodice(String codiceInserito) {
        codiceField.setText(codiceInserito);
    }

    public void ClickVerifica() {
        new GestioneVerifica2FACtl().InviaCodice(codiceField.getText());
    }

    public void ClickIndietro() {
        Navigazione.MostraFormAutenticati();
    }
}
