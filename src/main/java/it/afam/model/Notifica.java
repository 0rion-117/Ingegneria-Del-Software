package it.afam.model;

import java.time.LocalDateTime;

public class Notifica {
    private int idNotifica;
    private int idStudente;
    private int idCartella;
    private String tipo;
    private String stato;
    private LocalDateTime dataOraGenerazione;

    public Notifica() {
    }

    public Notifica(int idNotifica, int idStudente, int idCartella, String tipo,
                    String stato, LocalDateTime dataOraGenerazione) {
        this.idNotifica = idNotifica;
        this.idStudente = idStudente;
        this.idCartella = idCartella;
        this.tipo = tipo;
        this.stato = stato;
        this.dataOraGenerazione = dataOraGenerazione;
    }

    public Notifica CreaNotifica(int idStudente, int idCartella, String tipo,
                                 String stato, LocalDateTime dataOraGenerazione) {
        return new Notifica(0, idStudente, idCartella, tipo, stato, dataOraGenerazione);
    }

    public String GetDettagliNotifica(Object dettagliNotifica) {
        if (dettagliNotifica instanceof Notifica notifica) {
            return "Visualizzazione Cartella " + notifica.getIdCartella()
                    + " - " + notifica.getDataOraGenerazione();
        }
        return "";
    }

    public void SetStatoNotifica(String stato) {
        this.stato = stato;
    }

    public int getIdNotifica() {
        return idNotifica;
    }

    public void setIdNotifica(int idNotifica) {
        this.idNotifica = idNotifica;
    }

    public int getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(int idStudente) {
        this.idStudente = idStudente;
    }

    public int getIdCartella() {
        return idCartella;
    }

    public void setIdCartella(int idCartella) {
        this.idCartella = idCartella;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDateTime getDataOraGenerazione() {
        return dataOraGenerazione;
    }

    public void setDataOraGenerazione(LocalDateTime dataOraGenerazione) {
        this.dataOraGenerazione = dataOraGenerazione;
    }
}
