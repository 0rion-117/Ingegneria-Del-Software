package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

public class GestioneModificaPasswordCtl {
    private int idStudente;
    private String passwordCorrente;
    private String nuovaPassword;
    private String confermaNuovaPassword;
    private String passwordCorrenteCifrata;
    private String passwordCifrata;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaModificaPassword() {
        Navigazione.MostraFormModificaPassword();
    }

    public void InviaDatiPassword(String passwordCorrente, String nuovaPassword, String confermaNuovaPassword) {
        idStudente = Sessione.getIdStudente();
        this.passwordCorrente = passwordCorrente;
        this.nuovaPassword = nuovaPassword;
        this.confermaNuovaPassword = confermaNuovaPassword;
        try {
            passwordCorrenteCifrata = CriptaPassword(passwordCorrente);
            boolean esitoVerificaPasswordCorrente = dbmsBoundary.RichiediVerificaPassword(idStudente, passwordCorrenteCifrata);
            if (!esitoVerificaPasswordCorrente) {
                Navigazione.MostraPannelloErrore("Password non corretta");
                Navigazione.MostraGestioneProfilo();
                return;
            }
            if (!ValidaNuovaPassword(passwordCorrente, nuovaPassword, confermaNuovaPassword)) {
                Navigazione.MostraPannelloErrore("Errore Password");
                Navigazione.MostraGestioneProfilo();
                return;
            }
            passwordCifrata = CriptaPassword(nuovaPassword);
            new Studente().SetPassword(passwordCifrata);
            boolean esitoAggiornamentoPassword = dbmsBoundary.RichiediAggiornamentoPassword(idStudente, passwordCifrata);
            if (esitoAggiornamentoPassword) {
                Navigazione.MostraPannelloAvviso("Modifica Avvenuta");
            } else {
                Navigazione.MostraPannelloErrore("Errore Password");
            }
            Navigazione.MostraGestioneProfilo();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ValidaNuovaPassword(String passwordCorrente, String nuovaPassword, String confermaNuovaPassword) {
        return FunzioniGeneriche.validaPassword(nuovaPassword)
                && nuovaPassword.equals(confermaNuovaPassword)
                && !nuovaPassword.equals(passwordCorrente);
    }

    public String CriptaPassword(String nuovaPassword) {
        return FunzioniGeneriche.criptaPassword(nuovaPassword);
    }
}
