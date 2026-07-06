package it.afam.control.feedback;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.List;

public class GestioneAccessoEsternoCtl {
    private String linkDedicato;
    private int idCartella;
    private List<Contenuto> contenutiCartella;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaAccessoTramiteLink(String linkDedicato) {
        this.linkDedicato = linkDedicato;
        try {
            boolean esitoVerificaLink = dbmsBoundary.RichiediVerificaLink(linkDedicato);
            if (!esitoVerificaLink) {
                Navigazione.MostraPannelloErrore("Link non valido o scaduto");
                Navigazione.MostraPaginaErroreLink();
                return;
            }
            contenutiCartella = dbmsBoundary.RichiediContenutiCartellaDaLink(linkDedicato);
            idCartella = dbmsBoundary.RichiediIdCartellaDaLink(linkDedicato);
            new RegistraVisualizzazioneCtl().RegistraVisualizzazione(idCartella);
            Sessione.setLinkDedicato(linkDedicato);
            Sessione.setContenutiCartellaGuest(contenutiCartella);
            Navigazione.MostraPaginaAccessoGuest();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
