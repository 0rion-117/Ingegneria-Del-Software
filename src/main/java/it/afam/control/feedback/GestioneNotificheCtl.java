package it.afam.control.feedback;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Notifica;
import it.afam.persistence.DBMSBoundary;
import javafx.application.Platform;

public class GestioneNotificheCtl {
    private int idStudente;
    private int idNotifica;
    private Object dettagliNotifica;
    private String testoNotifica;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void RilevaNotificaDaLeggere(int idStudente) {
        this.idStudente = idStudente;
        if (idStudente <= 0) {
            return;
        }
        try {
            dettagliNotifica = dbmsBoundary.RichiediDettagliNotifica(idStudente);
            if (dettagliNotifica == null) {
                return;
            }
            if (!VerificaRecuperoNotifica(dettagliNotifica)) {
                Navigazione.MostraPannelloErrore("Errore DBMS");
                Navigazione.MostraSchermataCorrenteNonAggiornata();
                return;
            }
            Notifica notifica = (Notifica) dettagliNotifica;
            idNotifica = notifica.getIdNotifica();
            Sessione.setNotificaCorrente(notifica);
            testoNotifica = new Notifica().GetDettagliNotifica(dettagliNotifica);
            Platform.runLater(() -> Navigazione.MostraBannerNotifica(testoNotifica));
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean VerificaRecuperoNotifica(Object dettagliNotifica) {
        return dettagliNotifica instanceof Notifica;
    }

    public void PresaVisioneNotifica() {
        Notifica notifica = Sessione.getNotificaCorrente();
        if (notifica == null) {
            return;
        }
        try {
            notifica.SetStatoNotifica("Letta");
            dbmsBoundary.RichiediAggiornamentoStatoNotifica(notifica.getIdNotifica(), "Letta");
            Navigazione.MostraSchermataCorrenteAggiornata();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
