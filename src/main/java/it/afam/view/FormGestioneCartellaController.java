package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.condivisione.GestioneCreaCartellaCtl;
import it.afam.control.condivisione.GestioneModificaCartellaCtl;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FormGestioneCartellaController {
    @FXML private TextField nomeCartellaField;
    @FXML private VBox contenutiBox;
    @FXML private Button confermaButton;
    @FXML private Button salvaModificheButton;
    private final Map<CheckBox, Integer> selezioni = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        contenutiBox.getChildren().clear();
        selezioni.clear();
        List<Integer> giaSelezionati = Sessione.getListaIdContenutiCartella();
        for (Contenuto contenuto : Sessione.getListaContenuti()) {
            CheckBox checkBox = new CheckBox(contenuto.toString());
            checkBox.setSelected(giaSelezionati.contains(contenuto.getIdContenuto()));
            selezioni.put(checkBox, contenuto.getIdContenuto());
            contenutiBox.getChildren().add(checkBox);
        }
        Cartella cartella = Sessione.getCartellaCorrente();
        if (cartella != null) {
            nomeCartellaField.setText(cartella.getNomeCartella());
        }
        boolean modifica = "modifica".equals(Sessione.getModalitaFormCartella());
        confermaButton.setVisible(!modifica);
        confermaButton.setManaged(!modifica);
        salvaModificheButton.setVisible(modifica);
        salvaModificheButton.setManaged(modifica);
        nomeCartellaField.setDisable(modifica);
    }

    public void MostraFormGestioneCartella(List<Contenuto> listaContenuti) {
        Sessione.setListaContenuti(listaContenuti);
        initialize();
    }

    public void MostraFormGestioneCartellaSelezionePrecedente() {
        initialize();
    }

    public void SelezionaContenuti(List<Integer> listaIdContenuti) {
        for (Map.Entry<CheckBox, Integer> entry : selezioni.entrySet()) {
            entry.getKey().setSelected(listaIdContenuti.contains(entry.getValue()));
        }
    }

    public void ModificaSelezioneContenuti(List<Integer> listaIdContenuti) {
        SelezionaContenuti(listaIdContenuti);
    }

    public void InserisciNomeCartella(String nomeCartella) {
        nomeCartellaField.setText(nomeCartella);
    }

    public void ClickConferma() {
        new GestioneCreaCartellaCtl().InviaDatiCartella(nomeCartellaField.getText(), selezionati());
    }

    public void ClickSalvaModifiche() {
        Cartella cartella = Sessione.getCartellaCorrente();
        if (cartella != null) {
            new GestioneModificaCartellaCtl().InviaModificheCartella(cartella.getIdCartella(), selezionati());
        }
    }

    public void ClickIndietro() {
        Navigazione.MostraGestioneCartelle();
    }

    private List<Integer> selezionati() {
        List<Integer> listaIdContenuti = new ArrayList<>();
        for (Map.Entry<CheckBox, Integer> entry : selezioni.entrySet()) {
            if (entry.getKey().isSelected()) {
                listaIdContenuti.add(entry.getValue());
            }
        }
        return listaIdContenuti;
    }
}
