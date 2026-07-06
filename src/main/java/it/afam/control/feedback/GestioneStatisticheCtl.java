package it.afam.control.feedback;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.persistence.DBMSBoundary;

public class GestioneStatisticheCtl {
    private int idCartella;
    private Object datiStatistici;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaStatisticheCartella(int idCartella) {
        this.idCartella = idCartella;
        try {
            datiStatistici = dbmsBoundary.RichiediDatiStatistici(idCartella);
            if (!VerificaRecuperoDati(datiStatistici)) {
                Navigazione.MostraPannelloErrore("Errore DBMS");
                Navigazione.MostraGestioneCartelle();
                return;
            }
            Sessione.setDatiStatistici(datiStatistici);
            Navigazione.MostraPannelloStatistiche(datiStatistici);
            Navigazione.MostraGestioneCartelle();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean VerificaRecuperoDati(Object datiStatistici) {
        return datiStatistici != null;
    }
}
