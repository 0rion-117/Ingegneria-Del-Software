package it.afam.control.feedback;

import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Notifica;
import it.afam.persistence.DBMSBoundary;

import java.time.LocalDateTime;

public class RegistraVisualizzazioneCtl {
    private int idCartella;
    private int numeroVisualizzazioni;
    private int numeroVisualizzazioniAggiornato;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void RegistraVisualizzazione(int idCartella) {
        this.idCartella = idCartella;
        try {
            numeroVisualizzazioni = dbmsBoundary.RichiediVisualizzazioniCartella(idCartella);
            numeroVisualizzazioniAggiornato = new Cartella().SetNumeroVisualizzazioni(numeroVisualizzazioni + 1);
            dbmsBoundary.RichiediAggiornamentoVisualizzazioni(idCartella, numeroVisualizzazioniAggiornato);
            int idStudente = dbmsBoundary.RichiediIdStudenteCartella(idCartella);
            String tipo = "Visualizzazione";
            LocalDateTime dataOraGenerazione = LocalDateTime.now();
            new Notifica().CreaNotifica(idStudente, idCartella, tipo, "Da leggere", dataOraGenerazione);
            dbmsBoundary.RichiediInserimentoNotifica(idStudente, idCartella, tipo, "Da leggere", dataOraGenerazione);
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }
}
