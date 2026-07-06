package it.afam.persistence;

import it.afam.exception.DBMSException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
    private static final String URL = "jdbc:sqlite:afam.db";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    public static void inizializza() throws DBMSException {
        try {
            Class.forName("org.sqlite.JDBC");
            creaTabelle();
            popolaProvider();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBMSException("Errore DBMS", e);
        }
    }

    private static void creaTabelle() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Studente(
                      idStudente INTEGER PRIMARY KEY AUTOINCREMENT,
                      nome TEXT NOT NULL,
                      cognome TEXT NOT NULL,
                      email TEXT UNIQUE NOT NULL,
                      password TEXT NOT NULL,
                      dataNascita TEXT NOT NULL,
                      codiceFiscale TEXT UNIQUE NOT NULL,
                      username TEXT UNIQUE NOT NULL,
                      tokenReset TEXT
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Contenuto(
                      idContenuto INTEGER PRIMARY KEY AUTOINCREMENT,
                      idStudente INTEGER NOT NULL,
                      file TEXT NOT NULL,
                      titolo TEXT NOT NULL,
                      descrizione TEXT,
                      visibilita TEXT NOT NULL,
                      FOREIGN KEY(idStudente) REFERENCES Studente(idStudente) ON DELETE CASCADE
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Cartella(
                      idCartella INTEGER PRIMARY KEY AUTOINCREMENT,
                      idStudente INTEGER NOT NULL,
                      nomeCartella TEXT NOT NULL,
                      linkCartella TEXT UNIQUE,
                      numeroVisualizzazioni INTEGER NOT NULL DEFAULT 0,
                      FOREIGN KEY(idStudente) REFERENCES Studente(idStudente) ON DELETE CASCADE,
                      UNIQUE(idStudente, nomeCartella)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Cartella_Contenuto(
                      idCartella INTEGER NOT NULL,
                      idContenuto INTEGER NOT NULL,
                      PRIMARY KEY(idCartella, idContenuto),
                      FOREIGN KEY(idCartella) REFERENCES Cartella(idCartella) ON DELETE CASCADE,
                      FOREIGN KEY(idContenuto) REFERENCES Contenuto(idContenuto) ON DELETE CASCADE
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Notifica(
                      idNotifica INTEGER PRIMARY KEY AUTOINCREMENT,
                      idStudente INTEGER NOT NULL,
                      idCartella INTEGER NOT NULL,
                      tipo TEXT NOT NULL,
                      stato TEXT NOT NULL,
                      dataOraGenerazione TEXT NOT NULL,
                      FOREIGN KEY(idStudente) REFERENCES Studente(idStudente) ON DELETE CASCADE,
                      FOREIGN KEY(idCartella) REFERENCES Cartella(idCartella) ON DELETE CASCADE
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Provider(
                      nomeProvider TEXT PRIMARY KEY NOT NULL
                    )
                    """);
        }
    }

    private static void popolaProvider() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT OR IGNORE INTO Provider(nomeProvider) VALUES ('Fornitore di Identita Digitale')");
        }
    }
}
