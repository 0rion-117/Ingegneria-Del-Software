package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.portfolio.GestioneCaricaContenutiCtl;
import it.afam.control.portfolio.GestioneModificaContenutiCtl;
import it.afam.model.Contenuto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class FormContenutoController {
    @FXML private TextField fileField;
    @FXML private TextField titoloField;
    @FXML private TextArea descrizioneArea;
    @FXML private RadioButton pubblicoRadio;
    @FXML private RadioButton privatoRadio;
    @FXML private Label statoInvioLabel;
    @FXML private Button confermaButton;
    private boolean invioInCorso;

    @FXML
    public void initialize() {
        Contenuto contenuto = Sessione.getContenutoCorrente();
        if ("modifica".equals(Sessione.getModalitaFormContenuto()) && contenuto != null) {
            MostraFormContenutoPrecompilato(contenuto);
        } else {
            MostraFormContenuto("", "", "", "");
        }
    }

    public void MostraFormContenuto(String file, String titolo, String descrizione, String visibilita) {
        fileField.setText(file);
        titoloField.setText(titolo);
        descrizioneArea.setText(descrizione);
        if ("Pubblico".equals(visibilita)) {
            pubblicoRadio.setSelected(true);
        } else if ("Privato".equals(visibilita)) {
            privatoRadio.setSelected(true);
        }
    }

    public void MostraFormContenutoPrecompilato(Contenuto contenuto) {
        MostraFormContenuto(contenuto.getFile(), contenuto.getTitolo(), contenuto.getDescrizione(), contenuto.getVisibilita());
    }

    public void InserisciDatiContenuto(String file, String titolo, String descrizione, String visibilita) {
        MostraFormContenuto(file, titolo, descrizione, visibilita);
    }

    public void ModificaCampiContenuto(String file, String titolo, String descrizione, String visibilita) {
        MostraFormContenuto(file, titolo, descrizione, visibilita);
    }

    public void CompilaTitolo(String titolo) {
        titoloField.setText(titolo);
    }

    public void SelezionaLivelloVisibilita(String visibilita) {
        if ("Pubblico".equals(visibilita)) {
            pubblicoRadio.setSelected(true);
        } else if ("Privato".equals(visibilita)) {
            privatoRadio.setSelected(true);
        }
    }

    public void SelezionaFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleziona File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("Audio", "*.mp3", "*.wav", "*.flac", "*.aac"),
                new FileChooser.ExtensionFilter("Video", "*.mp4", "*.mov", "*.avi"),
                new FileChooser.ExtensionFilter("Documenti", "*.pdf", "*.docx"),
                new FileChooser.ExtensionFilter("Spartiti", "*.musicxml", "*.mscz"),
                new FileChooser.ExtensionFilter("Tutti i file", "*.*"));
        File file = chooser.showOpenDialog(fileField.getScene().getWindow());
        if (file != null) {
            fileField.setText(file.getAbsolutePath());
        }
    }

    public void ClickConferma() {
        if (invioInCorso) {
            return;
        }
        setInvioInCorso(true);
        String testoOperazione = "modifica".equals(Sessione.getModalitaFormContenuto())
                ? "Salvataggio in corso..."
                : "Caricamento in corso...";
        statoInvioLabel.setText(testoOperazione);
        confermaButton.setText(testoOperazione);

        InviaDatiForm();
    }

    private void InviaDatiForm() {
        String visibilita = visibilitaSelezionata();
        try {
            if ("modifica".equals(Sessione.getModalitaFormContenuto()) && Sessione.getContenutoCorrente() != null) {
                new GestioneModificaContenutiCtl().InviaDatiModificati(
                        Sessione.getContenutoCorrente().getIdContenuto(),
                        fileField.getText(),
                        titoloField.getText(),
                        descrizioneArea.getText(),
                        visibilita);
            } else {
                new GestioneCaricaContenutiCtl().InviaDatiContenuto(
                        fileField.getText(),
                        titoloField.getText(),
                        descrizioneArea.getText(),
                        visibilita);
            }
        } catch (RuntimeException e) {
            String messaggioErrore = "modifica".equals(Sessione.getModalitaFormContenuto())
                    ? "Errore durante la modifica"
                    : "Errore File";
            Navigazione.MostraPannelloErrore(messaggioErrore);
            Navigazione.MostraInterfacciaPrincipaleStudente();
        } finally {
            setInvioInCorso(false);
        }
    }

    public void ClickIndietro() {
        if (invioInCorso) {
            return;
        }
        Navigazione.MostraInterfacciaPrincipaleStudente();
    }

    private String visibilitaSelezionata() {
        if (pubblicoRadio.isSelected()) {
            return "Pubblico";
        }
        if (privatoRadio.isSelected()) {
            return "Privato";
        }
        return "";
    }

    private void setInvioInCorso(boolean invioInCorso) {
        this.invioInCorso = invioInCorso;
        confermaButton.setDisable(invioInCorso);
        if (!invioInCorso) {
            confermaButton.setText("Conferma");
            statoInvioLabel.setText("");
        }
    }
}
