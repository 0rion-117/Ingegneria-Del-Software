package it.afam.control.portfolio;

import it.afam.app.Navigazione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

public class GestioneEliminaContenutiCtl {
    private int idContenuto;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaEliminaContenuto(int idContenuto) {
        this.idContenuto = idContenuto;
        boolean conferma = Navigazione.MostraPannelloAvvisoConferma("Vuoi eliminare il Contenuto selezionato?");
        if (!conferma) {
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        boolean eliminazioneElaborata = ElaboraRichiestaEliminazione(idContenuto);
        if (!eliminazioneElaborata) {
            Navigazione.MostraPannelloErrore("errore durante l'eliminazione");
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        try {
            Contenuto contenutoDaEliminare = dbmsBoundary.RichiediContenuto(idContenuto);
            if (contenutoDaEliminare == null) {
                Navigazione.MostraPannelloErrore("errore durante l'eliminazione");
                Navigazione.MostraInterfacciaPrincipaleStudente();
                return;
            }
            new Contenuto().EliminaContenuto(idContenuto);
            boolean esitoEliminazioneContenuto = dbmsBoundary.RichiediEliminazioneContenuto(idContenuto);
            if (esitoEliminazioneContenuto) {
                FunzioniGeneriche.eliminaFileContenutoGestito(contenutoDaEliminare.getFile());
                Navigazione.MostraPannelloAvviso("eliminazione avvenuta");
                Navigazione.MostraInterfacciaPrincipaleStudenteAggiornata();
            } else {
                Navigazione.MostraPannelloErrore("errore durante l'eliminazione");
                Navigazione.MostraInterfacciaPrincipaleStudente();
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ElaboraRichiestaEliminazione(int idContenuto) {
        return idContenuto > 0;
    }
}
