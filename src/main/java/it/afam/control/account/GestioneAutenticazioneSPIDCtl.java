package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;

public class GestioneAutenticazioneSPIDCtl {
    private String provider;
    private String email;
    private String password;
    private String codiceFiscale;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaAutenticazioneSPID() {
        try {
            Sessione.setProvider(dbmsBoundary.RichiediElencoProvider());
            Navigazione.MostraElencoProviderBoundary();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaProviderSelezionato(String provider) {
        this.provider = provider;
        Sessione.setProviderSelezionato(provider);
        Navigazione.MostraProviderBoundary();
    }

    public void InviaCredenzialiProvider(String email, String password) {
        this.email = email;
        this.password = password;
        try {
            Studente studenteDaProvider = dbmsBoundary.RichiediStudentePerEmail(email);
            codiceFiscale = studenteDaProvider == null ? "" : studenteDaProvider.getCodiceFiscale();
            boolean esitoVerificaCodiceFiscale = dbmsBoundary.RichiediVerificaCodiceFiscale(codiceFiscale);
            if (!esitoVerificaCodiceFiscale) {
                Navigazione.MostraPannelloErrore("Studente non registrato");
                Navigazione.MostraModuloLogin();
                return;
            }
            Studente studente = dbmsBoundary.RichiediStudenteDaCodiceFiscale(codiceFiscale);
            if (studente != null) {
                Sessione.avviaStudente(studente.getIdStudente(), studente.getUsername(), studente.getEmail());
            }
            Navigazione.MostraPannelloAvviso("Avvenuta autenticazione");
            Navigazione.MostraInterfacciaPrincipaleStudente();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
