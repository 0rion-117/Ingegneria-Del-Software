package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

public class GestioneAutenticazioneCtl {
    private String email;
    private String password;
    private String passwordCifrata;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaAutenticazione() {
        Navigazione.MostraFormAutenticati();
    }

    public void InviaCredenziali(String email, String password) {
        this.email = email;
        this.password = password;
        if (!ValidaFormatoEmail(email)) {
            Navigazione.MostraPannelloErrore("E-mail non valida");
            return;
        }
        try {
            boolean esitoVerificaEmail = dbmsBoundary.RichiediVerificaEmail(email);
            if (!VerificaEmailRegistrata(esitoVerificaEmail)) {
                Navigazione.MostraPannelloErrore("Studente non registrato");
                return;
            }
            passwordCifrata = CriptaPassword(password);
            boolean esitoVerificaPassword = dbmsBoundary.RichiediVerificaPassword(email, passwordCifrata);
            if (!VerificaPasswordCorretta(esitoVerificaPassword)) {
                Navigazione.MostraPannelloErrore("Password errata");
                return;
            }
            Studente studente = dbmsBoundary.RichiediStudentePerEmail(email);
            Sessione.preparaVerifica2FA(studente);
            new GestioneVerifica2FACtl().AvviaVerifica2FA(email);
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ValidaFormatoEmail(String email) {
        return FunzioniGeneriche.validaEmail(email);
    }

    public void InviaEmail(String email) {
        this.email = email;
    }

    public boolean VerificaEmailRegistrata(boolean esitoVerificaEmail) {
        return esitoVerificaEmail;
    }

    public String CriptaPassword(String password) {
        return FunzioniGeneriche.criptaPassword(password);
    }

    public void InviaPassword(String password) {
        this.password = password;
    }

    public boolean VerificaPasswordCorretta(boolean esitoVerificaPassword) {
        return esitoVerificaPassword;
    }
}
