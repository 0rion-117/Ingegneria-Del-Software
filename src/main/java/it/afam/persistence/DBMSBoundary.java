package it.afam.persistence;

import it.afam.app.Sessione;
import it.afam.exception.DBMSException;
import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.model.Notifica;
import it.afam.model.Studente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DBMSBoundary {
    private final QueryDBMS queryDBMS = new QueryDBMS();

    public String RichiediVerificaDatiRegistrazione(String codiceFiscale, String email, String username) throws DBMSException {
        try {
            return queryDBMS.querySelectDatiRegistrazione(codiceFiscale, email, username);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediInserimentoStudente(String nome, String cognome, String email, String passwordCifrata,
                                               LocalDate dataNascita, String codiceFiscale, String username) throws DBMSException {
        try {
            return queryDBMS.queryInsertStudente(nome, cognome, email, passwordCifrata, dataNascita, codiceFiscale, username);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaEmail(String email) throws DBMSException {
        try {
            return queryDBMS.querySelectEmail(email);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaPassword(String email, String passwordCifrata) throws DBMSException {
        try {
            return queryDBMS.querySelectPassword(email, passwordCifrata);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaCodiceFiscale(String codiceFiscale) throws DBMSException {
        try {
            return queryDBMS.querySelectStudentePerCodiceFiscale(codiceFiscale);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediAggiornamentoPassword(int idStudente, String passwordCifrata) throws DBMSException {
        try {
            return queryDBMS.queryUpdatePassword(idStudente, passwordCifrata);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaAccountEmail(String email) throws DBMSException {
        try {
            return queryDBMS.querySelectAccountPerEmail(email);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediSalvataggioTokenReset(String email, String tokenReset) throws DBMSException {
        try {
            return queryDBMS.queryInsertTokenReset(email, tokenReset);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaPassword(int idStudente, String password) throws DBMSException {
        try {
            return queryDBMS.querySelectPasswordStudente(idStudente, password);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediEliminazioneStudente(int idStudente) throws DBMSException {
        try {
            return queryDBMS.queryDeleteStudenteEDatiCollegati(idStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediSalvataggioContenuto(String file, String titolo, String descrizione, String visibilita) throws DBMSException {
        try {
            return queryDBMS.queryInsertContenuto(Sessione.getIdStudente(), file, titolo, descrizione, visibilita);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Contenuto RichiediContenuto(int idContenuto) throws DBMSException {
        try {
            return queryDBMS.querySelectContenuto(idContenuto);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediAggiornamentoContenuto(int idContenuto, String file, String titolo, String descrizione,
                                                  String visibilita) throws DBMSException {
        try {
            return queryDBMS.queryUpdateContenuto(idContenuto, file, titolo, descrizione, visibilita);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediEliminazioneContenuto(int idContenuto) throws DBMSException {
        try {
            return queryDBMS.queryDeleteContenuto(idContenuto);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Contenuto> RichiediContenutiPortfolio(int idStudente) throws DBMSException {
        try {
            return queryDBMS.querySelectContenutiPortfolio(idStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaNomeCartella(String nomeCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectCartellaPerNome(Sessione.getIdStudente(), nomeCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediCreazioneCartella(String nomeCartella, List<Integer> listaIdContenuti) throws DBMSException {
        try {
            return queryDBMS.queryInsertCartella(Sessione.getIdStudente(), nomeCartella, listaIdContenuti);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Contenuto> RichiediContenutiPortfolioEAssociati(int idStudente, int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectContenutiPortfolioECartella(idStudente, idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediSalvataggioModificheCartella(int idCartella, List<Integer> listaIdContenuti) throws DBMSException {
        try {
            return queryDBMS.queryUpdateCartellaContenuti(idCartella, listaIdContenuti);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediEliminazioneCartella(int idCartella) throws DBMSException {
        try {
            return queryDBMS.queryDeleteCartella(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Contenuto> RichiediContenutiCartella(int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectContenutiCartella(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediAggiornamentoLinkCartella(int idCartella, String linkCartella) throws DBMSException {
        try {
            return queryDBMS.queryUpdateLinkCartella(idCartella, linkCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public int RichiediVisualizzazioniCartella(int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectNumeroVisualizzazioni(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediAggiornamentoVisualizzazioni(int idCartella, int numeroVisualizzazioniAggiornato) throws DBMSException {
        try {
            return queryDBMS.queryUpdateNumeroVisualizzazioni(idCartella, numeroVisualizzazioniAggiornato);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediInserimentoNotifica(int idStudente, int idCartella, String tipo, String stato,
                                               LocalDateTime dataOraGenerazione) throws DBMSException {
        try {
            return queryDBMS.queryInsertNotifica(idStudente, idCartella, tipo, stato, dataOraGenerazione);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Object RichiediDettagliNotifica(int idStudente) throws DBMSException {
        try {
            return queryDBMS.querySelectNotificaDaLeggere(idStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediAggiornamentoStatoNotifica(int idNotifica, String stato) throws DBMSException {
        try {
            return queryDBMS.queryUpdateStatoNotifica(idNotifica, stato);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Object RichiediDatiStatistici(int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectDatiStatistici(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaLink(String linkDedicato) throws DBMSException {
        try {
            return queryDBMS.querySelectLinkDedicato(linkDedicato);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Contenuto> RichiediContenutiCartellaDaLink(String linkDedicato) throws DBMSException {
        try {
            return queryDBMS.querySelectContenutiCartellaDaLink(linkDedicato);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediVerificaUsername(String usernameStudente) throws DBMSException {
        try {
            return queryDBMS.querySelectStudentePerUsername(usernameStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Contenuto> RichiediProfiloPubblico(String usernameStudente) throws DBMSException {
        try {
            return (List<Contenuto>) queryDBMS.querySelectProfiloPubblico(usernameStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<String> RichiediElencoProvider() throws DBMSException {
        try {
            return queryDBMS.querySelectProvider();
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Studente RichiediStudentePerEmail(String email) throws DBMSException {
        try {
            return queryDBMS.querySelectStudentePerEmail(email);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Studente RichiediStudente(int idStudente) throws DBMSException {
        try {
            return queryDBMS.querySelectStudente(idStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public Studente RichiediStudenteDaCodiceFiscale(String codiceFiscale) throws DBMSException {
        try {
            return queryDBMS.querySelectStudenteDaCodiceFiscale(codiceFiscale);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Cartella> RichiediCartelleStudente(int idStudente) throws DBMSException {
        try {
            return queryDBMS.querySelectCartelleStudente(idStudente);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public List<Integer> RichiediIdContenutiCartella(int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectIdContenutiCartella(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public int RichiediIdCartellaDaLink(String linkDedicato) throws DBMSException {
        try {
            return queryDBMS.querySelectIdCartellaDaLink(linkDedicato);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public int RichiediIdStudenteCartella(int idCartella) throws DBMSException {
        try {
            return queryDBMS.querySelectIdStudenteCartella(idCartella);
        } catch (SQLException e) {
            throw errore(e);
        }
    }

    public boolean RichiediRiconnessioneDBMS() {
        try {
            return queryDBMS.queryPingDBMS();
        } catch (SQLException e) {
            return false;
        }
    }

    private DBMSException errore(SQLException e) {
        return new DBMSException("Errore DBMS", e);
    }
}
