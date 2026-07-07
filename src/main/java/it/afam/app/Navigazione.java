package it.afam.app;

import it.afam.view.BannerNotificaController;
import it.afam.view.PannelloAvvisoController;
import it.afam.view.PannelloErroreController;
import it.afam.view.PannelloLinkController;
import it.afam.view.PannelloStatisticheController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class Navigazione {
    private static Stage stage;
    private static String schermataCorrente;
    private static String schermataPrecedente;

    private Navigazione() {
    }

    public static void inizializza(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Sistema Identita Digitale Studenti AFAM");
        stage.setFullScreenExitHint("");
    }

    public static void MostraModuloLogin() {
        caricaScena("ModuloLogin.fxml", "ModuloLogin");
    }

    public static void MostraFormRegistrati() {
        caricaScena("FormRegistrati.fxml", "FormRegistrati");
    }

    public static void MostraFormAutenticati() {
        caricaScena("FormAutenticati.fxml", "FormAutenticati");
    }

    public static void MostraFormVerifica2FA() {
        caricaScena("FormVerifica2FA.fxml", "FormVerifica2FA");
    }

    public static void MostraFormRecuperoPassword() {
        caricaScena("FormRecuperoPassword.fxml", "FormRecuperoPassword");
    }

    public static void MostraElencoProviderBoundary() {
        caricaScena("ElencoProviderBoundary.fxml", "ElencoProviderBoundary");
    }

    public static void MostraProviderBoundary() {
        caricaScena("ProviderBoundary.fxml", "ProviderBoundary");
    }

    public static void MostraInterfacciaPrincipaleStudente() {
        caricaScena("InterfacciaPrincipaleStudente.fxml", "InterfacciaPrincipaleStudente");
    }

    public static void MostraInterfacciaPrincipaleStudenteAggiornata() {
        MostraInterfacciaPrincipaleStudente();
    }

    public static void MostraGestioneProfilo() {
        caricaScena("GestioneProfilo.fxml", "GestioneProfilo");
    }

    public static void MostraFormModificaPassword() {
        caricaScena("FormModificaPassword.fxml", "FormModificaPassword");
    }

    public static void MostraFormContenuto() {
        caricaScena("FormContenuto.fxml", "FormContenuto");
    }

    public static void MostraPaginaContenuto() {
        caricaScena("PaginaContenuto.fxml", "PaginaContenuto");
    }

    public static void MostraGestioneCartelle() {
        caricaScena("GestioneCartelle.fxml", "GestioneCartelle");
    }

    public static void MostraGestioneCartelleAggiornata() {
        MostraGestioneCartelle();
    }

    public static void MostraFormGestioneCartella() {
        caricaScena("FormGestioneCartella.fxml", "FormGestioneCartella");
    }

    public static void MostraPaginaCartella() {
        caricaScena("PaginaCartella.fxml", "PaginaCartella");
    }

    public static void MostraPaginaProfiloPubblico() {
        caricaScena("PaginaProfiloPubblico.fxml", "PaginaProfiloPubblico");
    }

    public static void MostraPaginaAccessoGuest() {
        caricaScena("PaginaAccessoGuest.fxml", "PaginaAccessoGuest");
    }

    public static void MostraPaginaErroreLink() {
        caricaScena("PaginaErroreLink.fxml", "PaginaErroreLink");
    }

    public static void MostraSchermataCorrenteNonAggiornata() {
        if (schermataCorrente != null) {
            caricaScena(schermataCorrente, "SchermataCorrente");
        }
    }

    public static void MostraSchermataCorrenteAggiornata() {
        MostraSchermataCorrenteNonAggiornata();
    }

    public static void MostraSchermataPrecedente() {
        if (schermataPrecedente != null) {
            caricaScena(schermataPrecedente, titoloDaFxml(schermataPrecedente));
        } else {
            MostraModuloLogin();
        }
    }

    public static void MostraPannelloErrore(String messaggio) {
        try {
            FXMLLoader loader = loader("PannelloErrore.fxml");
            Parent root = loader.load();
            Stage dialog = modal(root, "PannelloErrore");
            PannelloErroreController controller = loader.getController();
            controller.setStage(dialog);
            controller.MostraPannelloErrore(messaggio);
            dialog.showAndWait();
        } catch (IOException e) {
            throw new IllegalStateException("PannelloErrore non caricabile", e);
        }
    }

    public static void MostraPannelloAvviso(String messaggio) {
        MostraPannelloAvvisoConferma(messaggio, false, "Conferma");
    }

    public static boolean MostraPannelloAvvisoConferma(String messaggio) {
        return MostraPannelloAvvisoConferma(messaggio, true, "Conferma");
    }

    public static boolean MostraPannelloAvvisoEliminaAccount(String messaggio) {
        return MostraPannelloAvvisoConferma(messaggio, true, "Elimina Account");
    }

    private static boolean MostraPannelloAvvisoConferma(String messaggio, boolean confermaVisibile, String testoConferma) {
        try {
            FXMLLoader loader = loader("PannelloAvviso.fxml");
            Parent root = loader.load();
            Stage dialog = modal(root, "PannelloAvviso");
            PannelloAvvisoController controller = loader.getController();
            controller.setStage(dialog);
            controller.MostraPannelloAvviso(messaggio);
            controller.setConfermaVisibile(confermaVisibile);
            controller.setTestoConferma(testoConferma);
            dialog.showAndWait();
            return controller.isConfermato();
        } catch (IOException e) {
            throw new IllegalStateException("PannelloAvviso non caricabile", e);
        }
    }

    public static void CreaPannelloLink(String linkCartella) {
        try {
            FXMLLoader loader = loader("PannelloLink.fxml");
            Parent root = loader.load();
            Stage dialog = modal(root, "PannelloLink");
            PannelloLinkController controller = loader.getController();
            controller.setStage(dialog);
            controller.CreaPannelloLink();
            controller.MostraLinkCondivisione(linkCartella);
            dialog.showAndWait();
        } catch (IOException e) {
            throw new IllegalStateException("PannelloLink non caricabile", e);
        }
    }

    public static void MostraPannelloStatistiche(Object datiStatistici) {
        try {
            FXMLLoader loader = loader("PannelloStatistiche.fxml");
            Parent root = loader.load();
            Stage dialog = modal(root, "PannelloStatistiche");
            PannelloStatisticheController controller = loader.getController();
            controller.setStage(dialog);
            controller.MostraPannelloStatistiche(datiStatistici);
            dialog.showAndWait();
        } catch (IOException e) {
            throw new IllegalStateException("PannelloStatistiche non caricabile", e);
        }
    }

    public static void MostraBannerNotifica(String testoNotifica) {
        try {
            FXMLLoader loader = loader("BannerNotifica.fxml");
            Parent root = loader.load();
            Stage dialog = nonModal(root, "BannerNotifica");
            BannerNotificaController controller = loader.getController();
            controller.setStage(dialog);
            controller.MostraBannerNotifica(testoNotifica);
            dialog.show();
        } catch (IOException e) {
            throw new IllegalStateException("BannerNotifica non caricabile", e);
        }
    }

    private static void caricaScena(String fxml, String titolo) {
        try {
            FXMLLoader loader = loader(fxml);
            Parent root = loader.load();
            Scene scene = new Scene(root, 920, 640);
            URL css = Navigazione.class.getResource("/it/afam/css/style.css");
            if (css != null) {
                scene.getStylesheets().add(css.toExternalForm());
            }
            if (stage.getScene() != null && schermataCorrente != null && !schermataCorrente.equals(fxml)) {
                schermataPrecedente = schermataCorrente;
            }
            schermataCorrente = fxml;
            stage.setTitle(titolo);
            stage.setScene(scene);
            stage.show();
            stage.setFullScreen(true);
        } catch (IOException e) {
            throw new IllegalStateException("FXML non caricabile: " + fxml, e);
        }
    }

    private static FXMLLoader loader(String fxml) {
        URL resource = Navigazione.class.getResource("/it/afam/fxml/" + fxml);
        if (resource == null) {
            throw new IllegalStateException("Risorsa FXML mancante: " + fxml);
        }
        return new FXMLLoader(resource);
    }

    private static String titoloDaFxml(String fxml) {
        return fxml.endsWith(".fxml") ? fxml.substring(0, fxml.length() - 5) : fxml;
    }

    private static Stage modal(Parent root, String title) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        dialog.setAlwaysOnTop(true);
        dialog.setResizable(false);
        dialog.setOnShown(event -> {
            dialog.toFront();
            dialog.requestFocus();
        });
        Scene scene = new Scene(root);
        URL css = Navigazione.class.getResource("/it/afam/css/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }
        dialog.setScene(scene);
        return dialog;
    }

    private static Stage nonModal(Parent root, String title) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.setTitle(title);
        dialog.setAlwaysOnTop(true);
        dialog.setResizable(false);
        dialog.setOnShown(event -> {
            dialog.toFront();
            dialog.requestFocus();
        });
        Scene scene = new Scene(root);
        URL css = Navigazione.class.getResource("/it/afam/css/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }
        dialog.setScene(scene);
        return dialog;
    }
}
