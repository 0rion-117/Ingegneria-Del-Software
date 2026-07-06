package it.afam.control.comune;

import it.afam.app.Navigazione;
import it.afam.persistence.DBMSBoundary;

public class GestioneErroreComunicazioneDBMSCtl {
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();
    private int tentativi;

    public void RilevaMancataComunicazioneDBMS() {
        if (RichiediRiconnessioneDBMS()) {
            RiprendiOperazioneInterrotta();
        } else {
            Navigazione.MostraPannelloErrore("Errore DBMS");
            MostraSchermataPrecedente();
        }
    }

    public boolean RichiediRiconnessioneDBMS() {
        tentativi = 0;
        while (tentativi < 3) {
            tentativi++;
            if (dbmsBoundary.RichiediRiconnessioneDBMS()) {
                return true;
            }
        }
        return false;
    }

    public void RiprendiOperazioneInterrotta() {
        Navigazione.MostraSchermataCorrenteAggiornata();
    }

    public void MostraSchermataPrecedente() {
        Navigazione.MostraSchermataPrecedente();
    }
}
