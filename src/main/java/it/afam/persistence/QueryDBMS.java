package it.afam.persistence;

import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.model.Notifica;
import it.afam.model.Studente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryDBMS {
    public String querySelectDatiRegistrazione(String codiceFiscale, String email, String username) throws SQLException {
        if (exists("SELECT 1 FROM Studente WHERE codiceFiscale = ?", codiceFiscale)) {
            return "CODICE_FISCALE";
        }
        if (exists("SELECT 1 FROM Studente WHERE email = ?", email)) {
            return "EMAIL";
        }
        if (exists("SELECT 1 FROM Studente WHERE username = ?", username)) {
            return "USERNAME";
        }
        return "NESSUNO";
    }

    public boolean queryInsertStudente(String nome, String cognome, String email, String passwordCifrata,
                                       LocalDate dataNascita, String codiceFiscale, String username) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO Studente(nome, cognome, email, password, dataNascita, codiceFiscale, username)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """)) {
                statement.setString(1, nome);
                statement.setString(2, cognome);
                statement.setString(3, email);
                statement.setString(4, passwordCifrata);
                statement.setString(5, dataNascita.toString());
                statement.setString(6, codiceFiscale);
                statement.setString(7, username);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean querySelectEmail(String email) throws SQLException {
        return exists("SELECT 1 FROM Studente WHERE email = ?", email);
    }

    public boolean querySelectPassword(String email, String passwordCifrata) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM Studente WHERE email = ? AND password = ?")) {
            statement.setString(1, email);
            statement.setString(2, passwordCifrata);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean querySelectStudentePerCodiceFiscale(String codiceFiscale) throws SQLException {
        return exists("SELECT 1 FROM Studente WHERE codiceFiscale = ?", codiceFiscale);
    }

    public boolean queryUpdatePassword(int idStudente, String passwordCifrata) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Studente SET password = ? WHERE idStudente = ?")) {
                statement.setString(1, passwordCifrata);
                statement.setInt(2, idStudente);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean querySelectAccountPerEmail(String email) throws SQLException {
        return querySelectEmail(email);
    }

    public boolean queryInsertTokenReset(String email, String tokenReset) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Studente SET tokenReset = ? WHERE email = ?")) {
                statement.setString(1, tokenReset);
                statement.setString(2, email);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean querySelectPasswordStudente(int idStudente, String password) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM Studente WHERE idStudente = ? AND password = ?")) {
            statement.setInt(1, idStudente);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean queryDeleteStudenteEDatiCollegati(int idStudente) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Studente WHERE idStudente = ?")) {
                statement.setInt(1, idStudente);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean queryInsertContenuto(int idStudente, String file, String titolo, String descrizione,
                                        String visibilita) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO Contenuto(idStudente, file, titolo, descrizione, visibilita)
                    VALUES (?, ?, ?, ?, ?)
                    """)) {
                statement.setInt(1, idStudente);
                statement.setString(2, file);
                statement.setString(3, titolo);
                statement.setString(4, descrizione);
                statement.setString(5, visibilita);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public Contenuto querySelectContenuto(int idContenuto) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT idContenuto, file, titolo, descrizione, visibilita FROM Contenuto WHERE idContenuto = ?")) {
            statement.setInt(1, idContenuto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return leggiContenuto(resultSet);
                }
                return null;
            }
        }
    }

    public boolean queryUpdateContenuto(int idContenuto, String file, String titolo, String descrizione,
                                        String visibilita) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE Contenuto
                    SET file = ?, titolo = ?, descrizione = ?, visibilita = ?
                    WHERE idContenuto = ?
                    """)) {
                statement.setString(1, file);
                statement.setString(2, titolo);
                statement.setString(3, descrizione);
                statement.setString(4, visibilita);
                statement.setInt(5, idContenuto);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean queryDeleteContenuto(int idContenuto) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Contenuto WHERE idContenuto = ?")) {
                statement.setInt(1, idContenuto);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public List<Contenuto> querySelectContenutiPortfolio(int idStudente) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idContenuto, file, titolo, descrizione, visibilita
                     FROM Contenuto
                     WHERE idStudente = ?
                     ORDER BY idContenuto
                     """)) {
            statement.setInt(1, idStudente);
            return leggiContenuti(statement);
        }
    }

    public boolean querySelectCartellaPerNome(int idStudente, String nomeCartella) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM Cartella WHERE idStudente = ? AND nomeCartella = ?")) {
            statement.setInt(1, idStudente);
            statement.setString(2, nomeCartella);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean queryInsertCartella(int idStudente, String nomeCartella, List<Integer> listaIdContenuti) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO Cartella(idStudente, nomeCartella, numeroVisualizzazioni)
                    VALUES (?, ?, 0)
                    """, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, idStudente);
                statement.setString(2, nomeCartella);
                statement.executeUpdate();
                int idCartella;
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next()) {
                        connection.rollback();
                        return false;
                    }
                    idCartella = keys.getInt(1);
                }
                salvaAssociazioni(connection, idCartella, listaIdContenuti);
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public List<Contenuto> querySelectContenutiPortfolioECartella(int idStudente, int idCartella) throws SQLException {
        return querySelectContenutiPortfolio(idStudente);
    }

    public boolean queryUpdateCartellaContenuti(int idCartella, List<Integer> listaIdContenuti) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement delete = connection.prepareStatement(
                    "DELETE FROM Cartella_Contenuto WHERE idCartella = ?")) {
                delete.setInt(1, idCartella);
                delete.executeUpdate();
                salvaAssociazioni(connection, idCartella, listaIdContenuti);
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean queryDeleteCartella(int idCartella) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Cartella WHERE idCartella = ?")) {
                statement.setInt(1, idCartella);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public List<Contenuto> querySelectContenutiCartella(int idCartella) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT c.idContenuto, c.file, c.titolo, c.descrizione, c.visibilita
                     FROM Contenuto c
                     JOIN Cartella_Contenuto cc ON cc.idContenuto = c.idContenuto
                     WHERE cc.idCartella = ?
                     ORDER BY c.idContenuto
                     """)) {
            statement.setInt(1, idCartella);
            return leggiContenuti(statement);
        }
    }

    public boolean queryUpdateLinkCartella(int idCartella, String linkCartella) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Cartella SET linkCartella = ? WHERE idCartella = ?")) {
                statement.setString(1, linkCartella);
                statement.setInt(2, idCartella);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public int querySelectNumeroVisualizzazioni(int idCartella) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT numeroVisualizzazioni FROM Cartella WHERE idCartella = ?")) {
            statement.setInt(1, idCartella);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("numeroVisualizzazioni") : 0;
            }
        }
    }

    public boolean queryUpdateNumeroVisualizzazioni(int idCartella, int numeroVisualizzazioniAggiornato) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Cartella SET numeroVisualizzazioni = ? WHERE idCartella = ?")) {
                statement.setInt(1, numeroVisualizzazioniAggiornato);
                statement.setInt(2, idCartella);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean queryInsertNotifica(int idStudente, int idCartella, String tipo, String stato,
                                       LocalDateTime dataOraGenerazione) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO Notifica(idStudente, idCartella, tipo, stato, dataOraGenerazione)
                    VALUES (?, ?, ?, ?, ?)
                    """)) {
                statement.setInt(1, idStudente);
                statement.setInt(2, idCartella);
                statement.setString(3, tipo);
                statement.setString(4, stato);
                statement.setString(5, dataOraGenerazione.toString());
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public Notifica querySelectNotificaDaLeggere(int idStudente) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idNotifica, idStudente, idCartella, tipo, stato, dataOraGenerazione
                     FROM Notifica
                     WHERE idStudente = ? AND stato = 'Da leggere'
                     ORDER BY dataOraGenerazione
                     LIMIT 1
                     """)) {
            statement.setInt(1, idStudente);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Notifica(
                            resultSet.getInt("idNotifica"),
                            resultSet.getInt("idStudente"),
                            resultSet.getInt("idCartella"),
                            resultSet.getString("tipo"),
                            resultSet.getString("stato"),
                            LocalDateTime.parse(resultSet.getString("dataOraGenerazione")));
                }
                return null;
            }
        }
    }

    public boolean queryUpdateStatoNotifica(int idNotifica, String stato) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Notifica SET stato = ? WHERE idNotifica = ?")) {
                statement.setString(1, stato);
                statement.setInt(2, idNotifica);
                boolean esito = statement.executeUpdate() > 0;
                connection.commit();
                return esito;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public Object querySelectDatiStatistici(int idCartella) throws SQLException {
        Map<String, Object> datiStatistici = new LinkedHashMap<>();
        datiStatistici.put("idCartella", idCartella);
        datiStatistici.put("numeroVisualizzazioni", querySelectNumeroVisualizzazioni(idCartella));
        return datiStatistici;
    }

    public boolean querySelectLinkDedicato(String linkDedicato) throws SQLException {
        return exists("SELECT 1 FROM Cartella WHERE linkCartella = ?", linkDedicato);
    }

    public List<Contenuto> querySelectContenutiCartellaDaLink(String linkDedicato) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT c.idContenuto, c.file, c.titolo, c.descrizione, c.visibilita
                     FROM Contenuto c
                     JOIN Cartella_Contenuto cc ON cc.idContenuto = c.idContenuto
                     JOIN Cartella ca ON ca.idCartella = cc.idCartella
                     WHERE ca.linkCartella = ? AND c.visibilita = 'Pubblico'
                     ORDER BY c.idContenuto
                     """)) {
            statement.setString(1, linkDedicato);
            return leggiContenuti(statement);
        }
    }

    public boolean querySelectStudentePerUsername(String usernameStudente) throws SQLException {
        return exists("SELECT 1 FROM Studente WHERE username = ?", usernameStudente);
    }

    public Object querySelectProfiloPubblico(String usernameStudente) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT c.idContenuto, c.file, c.titolo, c.descrizione, c.visibilita
                     FROM Contenuto c
                     JOIN Studente s ON s.idStudente = c.idStudente
                     WHERE s.username = ? AND c.visibilita = 'Pubblico'
                     ORDER BY c.idContenuto
                     """)) {
            statement.setString(1, usernameStudente);
            return leggiContenuti(statement);
        }
    }

    public List<String> querySelectProvider() throws SQLException {
        List<String> provider = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT nomeProvider FROM Provider ORDER BY nomeProvider");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                provider.add(resultSet.getString("nomeProvider"));
            }
        }
        return provider;
    }

    public Studente querySelectStudentePerEmail(String email) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idStudente, nome, cognome, email, password, dataNascita, codiceFiscale, username
                     FROM Studente WHERE email = ?
                     """)) {
            statement.setString(1, email);
            return leggiStudente(statement);
        }
    }

    public Studente querySelectStudente(int idStudente) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idStudente, nome, cognome, email, password, dataNascita, codiceFiscale, username
                     FROM Studente WHERE idStudente = ?
                     """)) {
            statement.setInt(1, idStudente);
            return leggiStudente(statement);
        }
    }

    public Studente querySelectStudenteDaCodiceFiscale(String codiceFiscale) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idStudente, nome, cognome, email, password, dataNascita, codiceFiscale, username
                     FROM Studente WHERE codiceFiscale = ?
                     """)) {
            statement.setString(1, codiceFiscale);
            return leggiStudente(statement);
        }
    }

    public List<Cartella> querySelectCartelleStudente(int idStudente) throws SQLException {
        List<Cartella> cartelle = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT idCartella, nomeCartella, linkCartella, numeroVisualizzazioni
                     FROM Cartella
                     WHERE idStudente = ?
                     ORDER BY idCartella
                     """)) {
            statement.setInt(1, idStudente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idCartella = resultSet.getInt("idCartella");
                    cartelle.add(new Cartella(
                            idCartella,
                            resultSet.getString("nomeCartella"),
                            querySelectIdContenutiCartella(idCartella),
                            resultSet.getString("linkCartella"),
                            resultSet.getInt("numeroVisualizzazioni")));
                }
            }
        }
        return cartelle;
    }

    public List<Integer> querySelectIdContenutiCartella(int idCartella) throws SQLException {
        List<Integer> idContenuti = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT idContenuto FROM Cartella_Contenuto WHERE idCartella = ? ORDER BY idContenuto")) {
            statement.setInt(1, idCartella);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    idContenuti.add(resultSet.getInt("idContenuto"));
                }
            }
        }
        return idContenuti;
    }

    public int querySelectIdCartellaDaLink(String linkDedicato) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT idCartella FROM Cartella WHERE linkCartella = ?")) {
            statement.setString(1, linkDedicato);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("idCartella") : 0;
            }
        }
    }

    public int querySelectIdStudenteCartella(int idCartella) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT idStudente FROM Cartella WHERE idCartella = ?")) {
            statement.setInt(1, idCartella);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("idStudente") : 0;
            }
        }
    }

    public boolean queryPingDBMS() throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        }
    }

    private boolean exists(String sql, String value) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void salvaAssociazioni(Connection connection, int idCartella, List<Integer> listaIdContenuti) throws SQLException {
        if (listaIdContenuti == null) {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR IGNORE INTO Cartella_Contenuto(idCartella, idContenuto) VALUES (?, ?)")) {
            for (Integer idContenuto : listaIdContenuti) {
                if (idContenuto != null) {
                    statement.setInt(1, idCartella);
                    statement.setInt(2, idContenuto);
                    statement.addBatch();
                }
            }
            statement.executeBatch();
        }
    }

    private List<Contenuto> leggiContenuti(PreparedStatement statement) throws SQLException {
        List<Contenuto> contenuti = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                contenuti.add(leggiContenuto(resultSet));
            }
        }
        return contenuti;
    }

    private Contenuto leggiContenuto(ResultSet resultSet) throws SQLException {
        return new Contenuto(
                resultSet.getInt("idContenuto"),
                resultSet.getString("file"),
                resultSet.getString("titolo"),
                resultSet.getString("descrizione"),
                resultSet.getString("visibilita"));
    }

    private Studente leggiStudente(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            String data = resultSet.getString("dataNascita");
            return new Studente(
                    resultSet.getInt("idStudente"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    LocalDate.parse(data),
                    resultSet.getString("codiceFiscale"),
                    resultSet.getString("username"));
        }
    }
}
