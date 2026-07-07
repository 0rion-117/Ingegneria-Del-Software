package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.control.account.GestioneLogoutCtl;
import it.afam.control.feedback.GestioneNotificheCtl;
import it.afam.control.portfolio.GestioneCaricaContenutiCtl;
import it.afam.control.portfolio.GestioneEliminaContenutiCtl;
import it.afam.control.portfolio.GestioneModificaContenutiCtl;
import it.afam.control.portfolio.GestioneVisualizzaContenutoCtl;
import it.afam.control.portfolio.GestioneVisualizzaPortfolioCtl;
import it.afam.model.Contenuto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class InterfacciaPrincipaleController {
    @FXML private Label studenteLabel;
    @FXML private ListView<Contenuto> contenutiList;

    @FXML
    public void initialize() {
        studenteLabel.setText(Sessione.getUsernameStudente() == null ? "" : Sessione.getUsernameStudente());
        contenutiList.setItems(FXCollections.observableArrayList(
                new GestioneVisualizzaPortfolioCtl().AvviaVisualizzaPortfolio(Sessione.getIdStudente())));
        contenutiList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Contenuto contenuto = contenutiList.getSelectionModel().getSelectedItem();
                if (contenuto != null) {
                    ClickContenuto(contenuto.getIdContenuto());
                }
            }
        });
        new GestioneNotificheCtl().RilevaNotificaDaLeggere(Sessione.getIdStudente());
    }

    public void ClickCaricaNuovoContenuto() {
        new GestioneCaricaContenutiCtl().AvviaCaricaContenuto();
    }

    public void ClickModificaContenuto() {
        Contenuto contenuto = selezionaContenuto();
        if (contenuto != null) {
            ClickModificaContenuto(contenuto.getIdContenuto());
        }
    }

    public void ClickModificaContenuto(int idContenuto) {
        new GestioneModificaContenutiCtl().AvviaModificaContenuto(idContenuto);
    }

    public void ClickContenuto() {
        Contenuto contenuto = selezionaContenuto();
        if (contenuto != null) {
            ClickContenuto(contenuto.getIdContenuto());
        }
    }

    public void ClickContenuto(int idContenuto) {
        new GestioneVisualizzaContenutoCtl().AvviaVisualizzaContenuto(idContenuto);
    }

    public void ClickEliminaContenuto() {
        Contenuto contenuto = selezionaContenuto();
        if (contenuto != null) {
            ClickEliminaContenuto(contenuto.getIdContenuto());
        }
    }

    public void ClickEliminaContenuto(int idContenuto) {
        new GestioneEliminaContenutiCtl().AvviaEliminaContenuto(idContenuto);
    }

    public void ClickLogout() {
        new GestioneLogoutCtl().AvviaLogout();
    }

    public void MostraInterfacciaPrincipaleStudente() {
    }

    public void MostraInterfacciaPrincipaleStudenteAggiornata() {
        initialize();
    }

    public void MostraSchermataPrecedente() {
        Navigazione.MostraSchermataPrecedente();
    }

    public void ClickGestioneProfilo() {
        Navigazione.MostraGestioneProfilo();
    }

    public void ClickGestioneCartelle() {
        Navigazione.MostraGestioneCartelle();
    }

    private Contenuto selezionaContenuto() {
        Contenuto contenuto = contenutiList.getSelectionModel().getSelectedItem();
        if (contenuto == null) {
            Navigazione.MostraPannelloErrore("Errore nella visualizzazione del Contenuto");
        }
        return contenuto;
    }
}
