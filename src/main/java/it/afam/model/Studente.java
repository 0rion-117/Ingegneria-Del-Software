package it.afam.model;

import java.time.LocalDate;

public class Studente {
    private int idStudente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private String username;

    public Studente() {
    }

    public Studente(int idStudente, String nome, String cognome, String email, String password,
                    LocalDate dataNascita, String codiceFiscale, String username) {
        this.idStudente = idStudente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.username = username;
    }

    public Studente CreaStudente(String nome, String cognome, String email, String passwordCifrata,
                                 LocalDate dataNascita, String codiceFiscale, String username) {
        return new Studente(0, nome, cognome, email, passwordCifrata, dataNascita, codiceFiscale, username);
    }

    public void SetPassword(String passwordCifrata) {
        this.password = passwordCifrata;
    }

    public void RimuoviDatiStudente(int idStudente) {
        if (this.idStudente == idStudente) {
            this.nome = null;
            this.cognome = null;
            this.email = null;
            this.password = null;
            this.dataNascita = null;
            this.codiceFiscale = null;
            this.username = null;
        }
    }

    public String GetUsernameStudente() {
        return username;
    }

    public int getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(int idStudente) {
        this.idStudente = idStudente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username + " - " + nome + " " + cognome;
    }
}
