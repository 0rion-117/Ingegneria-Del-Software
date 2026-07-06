package it.afam.control.condivisione;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.List;

public class GestioneModificaCartellaCtl {
    private int idStudente;
    private int idCartella;
    private List<Integer> listaIdContenuti;
    private List<Contenuto> listaContenutiConSelezione;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaModificaCartella(int idCartella) {
        idStudente = Sessione.getIdStudente();
        this.idCartella = idCartella;
        try {
            listaContenutiConSelezione = dbmsBoundary.RichiediContenutiPortfolioEAssociati(idStudente, idCartella);
            Sessione.setListaContenuti(listaContenutiConSelezione);
            Sessione.setListaIdContenutiCartella(dbmsBoundary.RichiediIdContenutiCartella(idCartella));
            Sessione.setModalitaFormCartella("modifica");
            Sessione.setCartellaCorrente(trovaCartellaCorrente(idCartella));
            Navigazione.MostraFormGestioneCartella();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaModificheCartella(int idCartella, List<Integer> listaIdContenuti) {
        this.idCartella = idCartella;
        this.listaIdContenuti = listaIdContenuti;
        if (!VerificaSelezioneContenuti(listaIdContenuti)) {
            Navigazione.MostraPannelloErrore("Selezionare almeno un Contenuto");
            Navigazione.MostraFormGestioneCartella();
            return;
        }
        try {
            new Cartella().AggiornaComposizioneCartella(idCartella, listaIdContenuti);
            boolean esitoSalvataggioModifiche =
                    dbmsBoundary.RichiediSalvataggioModificheCartella(idCartella, listaIdContenuti);
            if (esitoSalvataggioModifiche) {
                Navigazione.MostraPannelloAvviso("Modifica Avvenuta");
                Navigazione.MostraGestioneCartelleAggiornata();
            } else {
                Navigazione.MostraPannelloErrore("Errore durante il salvataggio");
                Navigazione.MostraGestioneCartelle();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean VerificaSelezioneContenuti(List<Integer> listaIdContenuti) {
        return listaIdContenuti != null && !listaIdContenuti.isEmpty();
    }

    private Cartella trovaCartellaCorrente(int idCartella) throws DBMSException {
        for (Cartella cartella : dbmsBoundary.RichiediCartelleStudente(idStudente)) {
            if (cartella.getIdCartella() == idCartella) {
                return cartella;
            }
        }
        return null;
    }
}
