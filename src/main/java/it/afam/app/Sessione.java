package it.afam.app;

import it.afam.model.Cartella;
import it.afam.model.Contenuto;
import it.afam.model.Notifica;
import it.afam.model.Studente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Sessione {
    private static int idStudente;
    private static String usernameStudente;
    private static String emailStudente;
    private static int idStudenteVerifica2FA;
    private static String usernameVerifica2FA;
    private static String emailVerifica2FA;
    private static String codiceTemporaneo2FA;
    private static Contenuto contenutoCorrente;
    private static Cartella cartellaCorrente;
    private static List<Contenuto> listaContenuti = new ArrayList<>();
    private static List<Cartella> listaCartelle = new ArrayList<>();
    private static List<Integer> listaIdContenutiCartella = new ArrayList<>();
    private static String modalitaFormContenuto;
    private static String modalitaFormCartella;
    private static String providerSelezionato;
    private static List<String> provider = new ArrayList<>();
    private static String profiloPubblicoUsername;
    private static List<Contenuto> profiloPubblico = new ArrayList<>();
    private static List<Contenuto> contenutiCartellaGuest = new ArrayList<>();
    private static String linkDedicato;
    private static Object datiStatistici;
    private static Notifica notificaCorrente;

    private Sessione() {
    }

    public static void avviaStudente(int id, String username, String email) {
        idStudente = id;
        usernameStudente = username;
        emailStudente = email;
    }

    public static void preparaVerifica2FA(Studente studente) {
        if (studente != null) {
            idStudenteVerifica2FA = studente.getIdStudente();
            usernameVerifica2FA = studente.getUsername();
            emailVerifica2FA = studente.getEmail();
        }
    }

    public static void confermaVerifica2FA() {
        avviaStudente(idStudenteVerifica2FA, usernameVerifica2FA, emailVerifica2FA);
        idStudenteVerifica2FA = 0;
        usernameVerifica2FA = null;
        emailVerifica2FA = null;
        codiceTemporaneo2FA = null;
    }

    public static void TerminaSessioneStudente() {
        idStudente = 0;
        usernameStudente = null;
        emailStudente = null;
        contenutoCorrente = null;
        cartellaCorrente = null;
        listaContenuti = new ArrayList<>();
        listaCartelle = new ArrayList<>();
        listaIdContenutiCartella = new ArrayList<>();
        modalitaFormContenuto = null;
        modalitaFormCartella = null;
        providerSelezionato = null;
        provider = new ArrayList<>();
        profiloPubblicoUsername = null;
        profiloPubblico = new ArrayList<>();
        contenutiCartellaGuest = new ArrayList<>();
        linkDedicato = null;
        datiStatistici = null;
        notificaCorrente = null;
    }

    public static int getIdStudente() {
        return idStudente;
    }

    public static String getUsernameStudente() {
        return usernameStudente;
    }

    public static String getEmailStudente() {
        return emailStudente;
    }

    public static String getEmailVerifica2FA() {
        return emailVerifica2FA;
    }

    public static String getCodiceTemporaneo2FA() {
        return codiceTemporaneo2FA;
    }

    public static void setCodiceTemporaneo2FA(String codiceTemporaneo2FA) {
        Sessione.codiceTemporaneo2FA = codiceTemporaneo2FA;
    }

    public static Contenuto getContenutoCorrente() {
        return contenutoCorrente;
    }

    public static void setContenutoCorrente(Contenuto contenutoCorrente) {
        Sessione.contenutoCorrente = contenutoCorrente;
    }

    public static Cartella getCartellaCorrente() {
        return cartellaCorrente;
    }

    public static void setCartellaCorrente(Cartella cartellaCorrente) {
        Sessione.cartellaCorrente = cartellaCorrente;
    }

    public static List<Contenuto> getListaContenuti() {
        return listaContenuti;
    }

    public static void setListaContenuti(List<Contenuto> listaContenuti) {
        Sessione.listaContenuti = listaContenuti == null ? new ArrayList<>() : listaContenuti;
    }

    public static List<Cartella> getListaCartelle() {
        return listaCartelle;
    }

    public static void setListaCartelle(List<Cartella> listaCartelle) {
        Sessione.listaCartelle = listaCartelle == null ? new ArrayList<>() : listaCartelle;
    }

    public static List<Integer> getListaIdContenutiCartella() {
        return listaIdContenutiCartella;
    }

    public static void setListaIdContenutiCartella(List<Integer> listaIdContenutiCartella) {
        Sessione.listaIdContenutiCartella = listaIdContenutiCartella == null ? new ArrayList<>() : listaIdContenutiCartella;
    }

    public static String getModalitaFormContenuto() {
        return modalitaFormContenuto;
    }

    public static void setModalitaFormContenuto(String modalitaFormContenuto) {
        Sessione.modalitaFormContenuto = modalitaFormContenuto;
    }

    public static String getModalitaFormCartella() {
        return modalitaFormCartella;
    }

    public static void setModalitaFormCartella(String modalitaFormCartella) {
        Sessione.modalitaFormCartella = modalitaFormCartella;
    }

    public static String getProviderSelezionato() {
        return providerSelezionato;
    }

    public static void setProviderSelezionato(String providerSelezionato) {
        Sessione.providerSelezionato = providerSelezionato;
    }

    public static List<String> getProvider() {
        return provider;
    }

    public static void setProvider(List<String> provider) {
        Sessione.provider = provider == null ? new ArrayList<>() : provider;
    }

    public static String getProfiloPubblicoUsername() {
        return profiloPubblicoUsername;
    }

    public static void setProfiloPubblicoUsername(String profiloPubblicoUsername) {
        Sessione.profiloPubblicoUsername = profiloPubblicoUsername;
    }

    public static List<Contenuto> getProfiloPubblico() {
        return profiloPubblico;
    }

    public static void setProfiloPubblico(List<Contenuto> profiloPubblico) {
        Sessione.profiloPubblico = profiloPubblico == null ? new ArrayList<>() : profiloPubblico;
    }

    public static List<Contenuto> getContenutiCartellaGuest() {
        return contenutiCartellaGuest;
    }

    public static void setContenutiCartellaGuest(List<Contenuto> contenutiCartellaGuest) {
        Sessione.contenutiCartellaGuest = contenutiCartellaGuest == null ? new ArrayList<>() : contenutiCartellaGuest;
    }

    public static String getLinkDedicato() {
        return linkDedicato;
    }

    public static void setLinkDedicato(String linkDedicato) {
        Sessione.linkDedicato = linkDedicato;
    }

    public static Object getDatiStatistici() {
        return datiStatistici;
    }

    public static void setDatiStatistici(Object datiStatistici) {
        Sessione.datiStatistici = datiStatistici;
    }

    public static Notifica getNotificaCorrente() {
        return notificaCorrente;
    }

    public static void setNotificaCorrente(Notifica notificaCorrente) {
        Sessione.notificaCorrente = notificaCorrente;
    }

    @SuppressWarnings("unchecked")
    public static String formattaDatiStatistici() {
        if (datiStatistici instanceof Map<?, ?> map) {
            return map.toString();
        }
        return datiStatistici == null ? "" : datiStatistici.toString();
    }
}
