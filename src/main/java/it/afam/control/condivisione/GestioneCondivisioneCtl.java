package it.afam.control.condivisione;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;

import java.util.List;

public class GestioneCondivisioneCtl {
    private int idCartella;
    private String usernameStudente;
    private String linkCartella;
    private List<Contenuto> contenutiCartella;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvvioCondivisione(int idCartella) {
        this.idCartella = idCartella;
        try {
            contenutiCartella = dbmsBoundary.RichiediContenutiCartella(idCartella);
            Studente studente = dbmsBoundary.RichiediStudente(Sessione.getIdStudente());
            usernameStudente = studente == null ? Sessione.getUsernameStudente() : studente.GetUsernameStudente();
            linkCartella = GeneraLink(usernameStudente, idCartella);
            new Cartella().SetLinkCartella(linkCartella);
            boolean esitoAggiornamentoLink =
                    dbmsBoundary.RichiediAggiornamentoLinkCartella(idCartella, linkCartella);
            if (esitoAggiornamentoLink) {
                Navigazione.CreaPannelloLink(linkCartella);
                Navigazione.MostraGestioneCartelle();
            } else {
                Navigazione.MostraPannelloErrore("Errore DBMS");
                Navigazione.MostraGestioneCartelle();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public String GeneraLink(String usernameStudente, int idCartella) {
        return "https://afam.it/" + usernameStudente + "/" + idCartella;
    }
}
