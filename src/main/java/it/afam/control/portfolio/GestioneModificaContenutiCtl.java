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

public class GestioneModificaContenutiCtl {
    private static boolean modificaInCorso;
    private int idContenuto;
    private String file;
    private String titolo;
    private String descrizione;
    private String visibilita;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaModificaContenuto(int idContenuto) {
        this.idContenuto = idContenuto;
        try {
            Contenuto contenuto = dbmsBoundary.RichiediContenuto(idContenuto);
            if (contenuto == null) {
                Navigazione.MostraPannelloErrore("Errore durante la modifica");
                Navigazione.MostraInterfacciaPrincipaleStudente();
                return;
            }
            Sessione.setModalitaFormContenuto("modifica");
            Sessione.setContenutoCorrente(contenuto);
            Navigazione.MostraFormContenuto();
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaDatiModificati(int idContenuto, String file, String titolo, String descrizione, String visibilita) {
        if (!iniziaModifica()) {
            Navigazione.MostraPannelloAvviso("Modifica gia in corso");
            return;
        }
        try {
            InviaDatiModificatiElaborati(idContenuto, file, titolo, descrizione, visibilita);
        } finally {
            terminaModifica();
        }
    }

    private void InviaDatiModificatiElaborati(int idContenuto, String file, String titolo, String descrizione, String visibilita) {
        this.idContenuto = idContenuto;
        this.file = file;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.visibilita = visibilita;
        if (!ElaboraDatiModificati(file, titolo, descrizione, visibilita)) {
            Navigazione.MostraPannelloErrore("Errore durante la modifica");
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        String filePrecedente = Sessione.getContenutoCorrente() == null ? null : Sessione.getContenutoCorrente().getFile();
        boolean fileGiaGestito = FunzioniGeneriche.fileContenutoGestito(file);
        String fileSalvato;
        try {
            fileSalvato = FunzioniGeneriche.salvaFileContenuto(file, Sessione.getUsernameStudente());
            this.file = fileSalvato;
        } catch (IOException | FileNonSupportatoException e) {
            Navigazione.MostraPannelloErrore("Errore durante la modifica");
            Navigazione.MostraInterfacciaPrincipaleStudente();
            return;
        }
        try {
            new Contenuto().AggiornaContenuto(idContenuto, fileSalvato, titolo, descrizione, visibilita);
            boolean esitoAggiornamentoContenuto =
                    dbmsBoundary.RichiediAggiornamentoContenuto(idContenuto, fileSalvato, titolo, descrizione, visibilita);
            if (esitoAggiornamentoContenuto) {
                if (filePrecedente != null && !filePrecedente.equals(fileSalvato)) {
                    FunzioniGeneriche.eliminaFileContenutoGestito(filePrecedente);
                }
                Navigazione.MostraPannelloAvviso("Modifica avvenuta");
                Navigazione.MostraInterfacciaPrincipaleStudenteAggiornata();
            } else {
                if (!fileGiaGestito) {
                    FunzioniGeneriche.eliminaFileContenutoGestito(fileSalvato);
                }
                Navigazione.MostraPannelloErrore("Errore durante la modifica");
                Navigazione.MostraInterfacciaPrincipaleStudente();
            }
        } catch (DBMSException e) {
            if (!fileGiaGestito) {
                FunzioniGeneriche.eliminaFileContenutoGestito(fileSalvato);
            }
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public boolean ElaboraDatiModificati(String file, String titolo, String descrizione, String visibilita) {
        try {
            return FunzioniGeneriche.validaFile(file)
                    && titolo != null && !titolo.isBlank()
                    && ("Pubblico".equals(visibilita) || "Privato".equals(visibilita));
        } catch (FileNonSupportatoException e) {
            return false;
        }
    }

    private static synchronized boolean iniziaModifica() {
        if (modificaInCorso) {
            return false;
        }
        modificaInCorso = true;
        return true;
    }

    private static synchronized void terminaModifica() {
        modificaInCorso = false;
    }
}
