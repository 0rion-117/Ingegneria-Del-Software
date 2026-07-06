package it.afam.control.condivisione;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.List;

public class GestioneCreaCartellaCtl {
    private int idStudente;
    private String nomeCartella;
    private List<Integer> listaIdContenuti;
    private List<Contenuto> listaContenuti;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaCreaCartella() {
        idStudente = Sessione.getIdStudente();
        try {
            listaContenuti = dbmsBoundary.RichiediContenutiPortfolio(idStudente);
            if (!VerificaPresenzaContenuti(listaContenuti)) {
                Navigazione.MostraPannelloAvviso("Nessun Contenuto disponibile nel Portfolio");
                Navigazione.MostraGestioneCartelle();
                return;
            }
            Sessione.setModalitaFormCartella("crea");
            Sessione.setListaContenuti(listaContenuti);
            Sessione.setCartellaCorrente(null);
            Sessione.setListaIdContenutiCartella(List.of());
            Navigazione.MostraFormGestioneCartella();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaDatiCartella(String nomeCartella, List<Integer> listaIdContenuti) {
        this.nomeCartella = nomeCartella;
        this.listaIdContenuti = listaIdContenuti;
        if (nomeCartella == null || nomeCartella.isBlank()) {
            Navigazione.MostraPannelloErrore("Input non valido");
            return;
        }
        try {
            boolean esitoNomeCartella = dbmsBoundary.RichiediVerificaNomeCartella(nomeCartella);
            if (esitoNomeCartella) {
                Navigazione.MostraPannelloErrore("Cartella già esistente");
                return;
            }
            new Cartella().CreaCartella(nomeCartella, listaIdContenuti);
            boolean esitoCreazioneCartella = dbmsBoundary.RichiediCreazioneCartella(nomeCartella, listaIdContenuti);
            if (esitoCreazioneCartella) {
                Navigazione.MostraPannelloAvviso("Creazione Avvenuta");
                Navigazione.MostraGestioneCartelleAggiornata();
            } else {
                Navigazione.MostraPannelloErrore("Errore durante la creazione");
                Navigazione.MostraGestioneCartelle();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaNomeCartella(String nomeCartella) {
        this.nomeCartella = nomeCartella;
    }

    public boolean VerificaPresenzaContenuti(List<Contenuto> listaContenuti) {
        return listaContenuti != null && !listaContenuti.isEmpty();
    }
}
