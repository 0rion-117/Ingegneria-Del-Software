package it.afam.control.portfolio;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.exception.FileNonSupportatoException;
import it.afam.model.Contenuto;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

import java.io.IOException;

public class GestioneCaricaContenutiCtl {
    private static boolean caricamentoInCorso;
    private String file;
    private String titolo;
    private String descrizione;
    private String visibilita;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaCaricaContenuto() {
        Sessione.setModalitaFormContenuto("carica");
        Sessione.setContenutoCorrente(null);
        Navigazione.MostraFormContenuto();
    }

    public void InviaDatiContenuto(String file, String titolo, String descrizione, String visibilita) {
        if (!iniziaCaricamento()) {
            Navigazione.MostraPannelloAvviso("Caricamento gia in corso");
            return;
        }
        try {
            InviaDatiContenutoElaborati(file, titolo, descrizione, visibilita);
        } finally {
            terminaCaricamento();
        }
    }

    private void InviaDatiContenutoElaborati(String file, String titolo, String descrizione, String visibilita) {
        this.file = file;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.visibilita = visibilita;
        if (!ElaboraDatiContenuto(file, titolo, descrizione, visibilita)) {
            Navigazione.MostraPannelloErrore("Errore File");
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        if (!VerificaTitoloNonVuoto(titolo)) {
            Navigazione.MostraPannelloErrore("Il campo Titolo non può essere vuoto");
            return;
        }
        if (!VerificaVisibilitaSelezionata(visibilita)) {
            Navigazione.MostraPannelloErrore("Seleziona Pubblico o Privato");
            return;
        }
        boolean fileGiaGestito = FunzioniGeneriche.fileContenutoGestito(file);
        String fileSalvato;
        try {
            fileSalvato = FunzioniGeneriche.salvaFileContenuto(file, Sessione.getUsernameStudente());
            this.file = fileSalvato;
        } catch (IOException | FileNonSupportatoException e) {
            Navigazione.MostraPannelloErrore("Errore File");
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        try {
            new Contenuto().CreaContenuto(fileSalvato, titolo, descrizione, visibilita);
            boolean esitoSalvataggioContenuto = dbmsBoundary.RichiediSalvataggioContenuto(fileSalvato, titolo, descrizione, visibilita);
            if (esitoSalvataggioContenuto) {
                Navigazione.MostraPannelloAvviso("Caricamento Avvenuto");
                Navigazione.MostraInterfacciaPrincipaleStudenteAggiornata();
            } else {
                if (!fileGiaGestito) {
                    FunzioniGeneriche.eliminaFileContenutoGestito(fileSalvato);
                }
                Navigazione.MostraPannelloErrore("Errore File");
                Navigazione.MostraInterfacciaPrincipaleStudente();
            }
        } catch (DBMSException e) {
            if (!fileGiaGestito) {
                FunzioniGeneriche.eliminaFileContenutoGestito(fileSalvato);
            }
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ElaboraDatiContenuto(String file, String titolo, String descrizione, String visibilita) {
        try {
            return FunzioniGeneriche.validaFile(file);
        } catch (FileNonSupportatoException e) {
            return false;
        }
    }

    public boolean VerificaTitoloNonVuoto(String titolo) {
        return titolo != null && !titolo.isBlank();
    }

    public boolean VerificaVisibilitaSelezionata(String visibilita) {
        return "Pubblico".equals(visibilita) || "Privato".equals(visibilita);
    }

    private static synchronized boolean iniziaCaricamento() {
        if (caricamentoInCorso) {
            return false;
        }
        caricamentoInCorso = true;
        return true;
    }

    private static synchronized void terminaCaricamento() {
        caricamentoInCorso = false;
    }
}
