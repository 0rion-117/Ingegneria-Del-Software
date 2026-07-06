package it.afam.control.feedback;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.List;

public class GestioneCercaProfiloCtl {
    private String usernameStudente;
    private List<Contenuto> profiloPubblico;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void InviaUsernameRicerca(String usernameStudente) {
        this.usernameStudente = usernameStudente;
        if (usernameStudente == null || usernameStudente.isBlank()) {
            Navigazione.MostraPannelloErrore("Username non trovato");
            return;
        }
        try {
            boolean esitoVerificaUsername = dbmsBoundary.RichiediVerificaUsername(usernameStudente);
            if (!esitoVerificaUsername) {
                Navigazione.MostraPannelloErrore("Username non trovato");
                Navigazione.MostraModuloLogin();
                return;
            }
            profiloPubblico = dbmsBoundary.RichiediProfiloPubblico(usernameStudente);
            Sessione.setProfiloPubblicoUsername(usernameStudente);
            Sessione.setProfiloPubblico(profiloPubblico);
            Navigazione.MostraPaginaProfiloPubblico();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
