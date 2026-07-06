package it.afam.app;

import it.afam.exception.DBMSException;
import it.afam.persistence.Database;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws DBMSException {
        Database.inizializza();
        Navigazione.inizializza(stage);
        Navigazione.MostraModuloLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
