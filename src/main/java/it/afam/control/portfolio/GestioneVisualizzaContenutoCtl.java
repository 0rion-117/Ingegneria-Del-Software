package it.afam.control.portfolio;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

public class GestioneVisualizzaContenutoCtl {
    private int idContenuto;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaVisualizzaContenuto(int idContenuto) {
        this.idContenuto = idContenuto;
        try {
            Contenuto contenuto = dbmsBoundary.RichiediContenuto(idContenuto);
            if (!VerificaRecuperoContenuto(contenuto)) {
                Navigazione.MostraPannelloErrore("Errore nella visualizzazione del Contenuto");
                Navigazione.MostraSchermataPrecedente();
                return;
            }
            Sessione.setContenutoCorrente(contenuto);
            Navigazione.MostraPaginaContenuto();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean VerificaRecuperoContenuto(Contenuto contenuto) {
        return contenuto != null;
    }
}
