package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.portfolio.GestioneVisualizzaContenutoCtl;
import it.afam.model.Contenuto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class PaginaProfiloPubblicoController {
    @FXML private Label usernameLabel;
    @FXML private ListView<Contenuto> contenutiList;

    @FXML
    public void initialize() {
        MostraPaginaProfiloPubblico(Sessione.getProfiloPubblico());
        contenutiList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Contenuto contenuto = contenutiList.getSelectionModel().getSelectedItem();
                if (contenuto != null) {
                    ClickContenuto(contenuto.getIdContenuto());
                }
            }
        });
    }

    public void ClickContenuto(int idContenuto) {
        new GestioneVisualizzaContenutoCtl().AvviaVisualizzaContenuto(idContenuto);
    }

    public void MostraPaginaProfiloPubblico(List<Contenuto> profiloPubblico) {
        usernameLabel.setText(Sessione.getProfiloPubblicoUsername());
        contenutiList.setItems(FXCollections.observableArrayList(profiloPubblico));
    }

    public void ClickIndietro() {
        Navigazione.MostraModuloLogin();
    }
}
