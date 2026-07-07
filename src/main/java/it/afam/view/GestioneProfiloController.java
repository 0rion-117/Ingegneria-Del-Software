package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.account.GestioneEliminazioneAccountCtl;
import it.afam.control.account.GestioneModificaPasswordCtl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class GestioneProfiloController {
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private PasswordField passwordField;

    @FXML
    public void initialize() {
        usernameLabel.setText(Sessione.getUsernameStudente());
        emailLabel.setText(Sessione.getEmailStudente());
    }

    public void ClickModificaPassword() {
        new GestioneModificaPasswordCtl().AvviaModificaPassword();
    }

    public void SelezionaEliminaAccount() {
        new GestioneEliminazioneAccountCtl().AvviaEliminazioneAccount();
    }

    public void RichiediPasswordConfermaIdentita() {
        passwordField.requestFocus();
    }

    public void InserisciPassword(String password) {
        passwordField.setText(password);
    }

    public void ClickConferma() {
        new GestioneEliminazioneAccountCtl().InviaPasswordEliminazione(passwordField.getText());
    }

    public void MostraGestioneProfilo() {
    }

    public void ClickIndietro() {
        Navigazione.MostraInterfacciaPrincipaleStudente();
    }
}
