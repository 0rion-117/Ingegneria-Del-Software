package it.afam.control.condivisione;

import it.afam.app.Navigazione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.persistence.DBMSBoundary;

public class GestioneEliminazioneCartellaCtl {
    private int idCartella;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaEliminaCartella(int idCartella) {
        this.idCartella = idCartella;
        boolean conferma = Navigazione.MostraPannelloAvvisoConferma("Vuoi Eliminare?");
        if (!conferma) {
            Navigazione.MostraGestioneCartelle();
            return;
        }
        boolean eliminazioneElaborata = ElaboraRichiestaEliminazioneCartella(idCartella);
        if (!eliminazioneElaborata) {
            Navigazione.MostraPannelloErrore("Errore durante l'eliminazione");
            Navigazione.MostraGestioneCartelle();
            return;
        }
        try {
            new Cartella().EliminaCartella(idCartella);
            boolean esitoEliminazioneCartella = dbmsBoundary.RichiediEliminazioneCartella(idCartella);
            if (esitoEliminazioneCartella) {
                Navigazione.MostraPannelloAvviso("Avvenuta Eliminazione");
                Navigazione.MostraGestioneCartelleAggiornata();
            } else {
                Navigazione.MostraPannelloErrore("Errore durante l'eliminazione");
                Navigazione.MostraGestioneCartelle();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ElaboraRichiestaEliminazioneCartella(int idCartella) {
        return idCartella > 0;
    }
}
