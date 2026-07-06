package it.afam.control.condivisione;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.ArrayList;
import java.util.List;

public class GestioneVisualizzaCartellaCtl {
    private int idCartella;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaVisualizzaCartella(int idCartella) {
        this.idCartella = idCartella;
        try {
            List<Contenuto> contenutiCartella = dbmsBoundary.RichiediContenutiCartella(idCartella);
            if (!VerificaPresenzaContenuti(contenutiCartella)) {
                Navigazione.MostraPannelloAvviso("Cartella Vuota");
                Navigazione.MostraGestioneCartelle();
                return;
            }
            Sessione.setListaContenuti(contenutiCartella);
            Navigazione.MostraPaginaCartella();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean VerificaPresenzaContenuti(List<Contenuto> contenutiCartella) {
        return contenutiCartella != null && !contenutiCartella.isEmpty();
    }

    public List<Cartella> CaricaCartelleStudente(int idStudente) {
        try {
            List<Cartella> cartelle = dbmsBoundary.RichiediCartelleStudente(idStudente);
            Sessione.setListaCartelle(cartelle);
            return cartelle;
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
            return new ArrayList<>();
        }
    }
}
