package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

public class GestioneRecuperoPasswordCtl {
    private String email;
    private String tokenReset;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaRecuperoPassword() {
        Navigazione.MostraFormRecuperoPassword();
    }

    public void InviaEmailRecupero(String email) {
        this.email = email;
        if (!ValidaFormatoEmail(email)) {
            Navigazione.MostraPannelloErrore("E-mail non valida");
            return;
        }
        try {
            boolean esitoVerificaEmail = dbmsBoundary.RichiediVerificaAccountEmail(email);
            if (!esitoVerificaEmail) {
                Navigazione.MostraPannelloErrore("Nessun account associato all'indirizzo email inserito");
                Navigazione.MostraFormRecuperoPassword();
                return;
            }
            Studente studente = dbmsBoundary.RichiediStudentePerEmail(email);
            if (studente == null) {
                Navigazione.MostraPannelloErrore("Nessun account associato all'indirizzo email inserito");
                Navigazione.MostraFormRecuperoPassword();
                return;
            }
            tokenReset = GeneraTokenUnivocoReset();
            String tokenCifrato = FunzioniGeneriche.criptaPassword(tokenReset);
            dbmsBoundary.RichiediSalvataggioTokenReset(email, tokenCifrato);
            studente.SetPassword(tokenCifrato);
            boolean esitoAggiornamentoPassword =
                    dbmsBoundary.RichiediAggiornamentoPassword(studente.getIdStudente(), tokenCifrato);
            if (!esitoAggiornamentoPassword) {
                Navigazione.MostraPannelloErrore("Errore DBMS");
                Navigazione.MostraFormRecuperoPassword();
                return;
            }
            InviaEmailLinkReset(email, tokenReset);
            Navigazione.MostraPannelloAvviso("Email inviata");
            Navigazione.MostraModuloLogin();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ValidaFormatoEmail(String email) {
        return FunzioniGeneriche.validaEmail(email);
    }

    public String GeneraTokenUnivocoReset() {
        return FunzioniGeneriche.generaToken();
    }

    public void InviaEmailLinkReset(String email, String tokenReset) {
        System.out.println("Link reset per " + email + ": https://afam.it/reset/" + tokenReset);
        System.out.println("Password temporanea per " + email + ": " + tokenReset);
    }
}
