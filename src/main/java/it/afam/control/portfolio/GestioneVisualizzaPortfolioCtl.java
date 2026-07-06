package it.afam.control.portfolio;

import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;

import java.util.ArrayList;
import java.util.List;

public class GestioneVisualizzaPortfolioCtl {
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public List<Contenuto> AvviaVisualizzaPortfolio(int idStudente) {
        try {
            List<Contenuto> contenuti = dbmsBoundary.RichiediContenutiPortfolio(idStudente);
            Sessione.setListaContenuti(contenuti);
            return contenuti;
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
            return new ArrayList<>();
        }
    }
}
