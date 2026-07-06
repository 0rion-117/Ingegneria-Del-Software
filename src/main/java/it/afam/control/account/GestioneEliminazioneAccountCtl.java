package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

public class GestioneEliminazioneAccountCtl {
    private int idStudente;
    private String password;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaEliminazioneAccount() {
        idStudente = Sessione.getIdStudente();
    }

    public void InviaPasswordEliminazione(String password) {
        this.password = password;
        idStudente = Sessione.getIdStudente();
        boolean conferma = Navigazione.MostraPannelloAvvisoEliminaAccount("Operazione Irreversibile (perdita di tutti i dati)");
        if (!conferma) {
            Navigazione.MostraGestioneProfilo();
            return;
        }
        try {
            String passwordCifrata = FunzioniGeneriche.criptaPassword(password);
            boolean esitoVerificaPassword = dbmsBoundary.RichiediVerificaPassword(idStudente, passwordCifrata);
            if (!esitoVerificaPassword) {
                Navigazione.MostraPannelloErrore("Password non corretta");
                Navigazione.MostraGestioneProfilo();
                return;
            }
            new Studente().RimuoviDatiStudente(idStudente);
            boolean esitoEliminazione = dbmsBoundary.RichiediEliminazioneStudente(idStudente);
            if (esitoEliminazione) {
                Sessione.TerminaSessioneStudente();
                Navigazione.MostraPannelloAvviso("Eliminazione Avvenuta");
                Navigazione.MostraModuloLogin();
            } else {
                Navigazione.MostraPannelloErrore("Errore DBMS");
                Navigazione.MostraGestioneProfilo();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
