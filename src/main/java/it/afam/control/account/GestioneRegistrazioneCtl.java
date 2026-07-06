package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.control.comune.GestioneErroreComunicazioneDBMSCtl;
import it.afam.exception.DBMSException;
import it.afam.model.Studente;
import it.afam.persistence.DBMSBoundary;
import it.afam.util.FunzioniGeneriche;

import java.time.LocalDate;

public class GestioneRegistrazioneCtl {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String confermaPassword;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private String username;
    private String passwordCifrata;
    private final DBMSBoundary dbmsBoundary = new DBMSBoundary();

    public void AvviaRegistrazione() {
        Navigazione.MostraFormRegistrati();
    }

    public void InviaDatiRegistrazione(String nome, String cognome, String email, String password,
                                       String confermaPassword, LocalDate dataNascita,
                                       String codiceFiscale, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.confermaPassword = confermaPassword;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale == null ? null : codiceFiscale.toUpperCase();
        this.username = username;

        if (!ValidaFormatoEmail(this.email)) {
            Navigazione.MostraPannelloErrore("E-mail non valida");
            return;
        }
        if (!ValidaFormatoPassword(this.password)) {
            Navigazione.MostraPannelloErrore("Password non valida");
            return;
        }
        if (!VerificaCorrispondenzaPassword(this.password, this.confermaPassword)) {
            Navigazione.MostraPannelloErrore("le Password non corrispondono");
            return;
        }
        if (!ValidaFormatoCodiceFiscale(this.codiceFiscale)) {
            Navigazione.MostraPannelloErrore("Codice Fiscale non valido");
            return;
        }
        if (this.nome == null || this.nome.isBlank() || this.cognome == null || this.cognome.isBlank()
                || this.dataNascita == null || this.username == null || this.username.isBlank()) {
            Navigazione.MostraPannelloErrore("Input non valido");
            return;
        }

        try {
            String esitoVerificaDati = dbmsBoundary.RichiediVerificaDatiRegistrazione(this.codiceFiscale, this.email, this.username);
            if (VerificaCodiceFiscalePresente(esitoVerificaDati)) {
                Navigazione.MostraPannelloAvviso("CF già utilizzato");
                return;
            }
            if (VerificaEmailPresente(esitoVerificaDati)) {
                Navigazione.MostraPannelloAvviso("Email già utilizzata");
                return;
            }
            if (VerificaUsernamePresente(esitoVerificaDati)) {
                Navigazione.MostraPannelloAvviso("Username già utilizzato");
                return;
            }
            if (!VerificaDatiGiaPresenti(esitoVerificaDati)) {
                passwordCifrata = CriptaPassword(this.password);
                new Studente().CreaStudente(this.nome, this.cognome, this.email, passwordCifrata,
                        this.dataNascita, this.codiceFiscale, this.username);
                boolean esitoInserimento = dbmsBoundary.RichiediInserimentoStudente(this.nome, this.cognome,
                        this.email, passwordCifrata, this.dataNascita, this.codiceFiscale, this.username);
                if (esitoInserimento) {
                    Navigazione.MostraPannelloAvviso("avvenuta registrazione");
                    Navigazione.MostraModuloLogin();
                } else {
                    Navigazione.MostraPannelloErrore("Errore DBMS");
                }
            }
        } catch (DBMSException e) {
            new GestioneErroreComunicazioneDBMSCtl().RilevaMancataComunicazioneDBMS();
        }
    }

    public void InviaEmail(String email) {
        this.email = email;
    }

    public void InviaPassword(String password) {
        this.password = password;
    }

    public void InviaPasswordConferma(String password, String confermaPassword) {
        this.password = password;
        this.confermaPassword = confermaPassword;
    }

    public void InviaCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public boolean ValidaFormatoEmail(String email) {
        return FunzioniGeneriche.validaEmail(email);
    }

    public boolean ValidaFormatoPassword(String password) {
        return FunzioniGeneriche.validaPassword(password);
    }

    public boolean VerificaCorrispondenzaPassword(String password, String confermaPassword) {
        return password != null && password.equals(confermaPassword);
    }

    public boolean ValidaFormatoCodiceFiscale(String codiceFiscale) {
        return FunzioniGeneriche.validaCodiceFiscale(codiceFiscale);
    }

    public boolean VerificaCodiceFiscalePresente(String esitoVerificaDati) {
        return "CODICE_FISCALE".equals(esitoVerificaDati);
    }

    public boolean VerificaEmailPresente(String esitoVerificaDati) {
        return "EMAIL".equals(esitoVerificaDati);
    }

    public boolean VerificaUsernamePresente(String esitoVerificaDati) {
        return "USERNAME".equals(esitoVerificaDati);
    }

    public boolean VerificaDatiGiaPresenti(String esitoVerificaDati) {
        return !"NESSUNO".equals(esitoVerificaDati);
    }

    public String CriptaPassword(String password) {
        return FunzioniGeneriche.criptaPassword(password);
    }
}
