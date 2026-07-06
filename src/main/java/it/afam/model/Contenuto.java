package it.afam.model;

public class Contenuto {
    private int idContenuto;
    private String file;
    private String titolo;
    private String descrizione;
    private String visibilita;

    public Contenuto() {
    }

    public Contenuto(int idContenuto, String file, String titolo, String descrizione, String visibilita) {
        this.idContenuto = idContenuto;
        this.file = file;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.visibilita = visibilita;
    }

    public Contenuto CreaContenuto(String file, String titolo, String descrizione, String visibilita) {
        return new Contenuto(0, file, titolo, descrizione, visibilita);
    }

    public void AggiornaContenuto(int idContenuto, String file, String titolo, String descrizione, String visibilita) {
        this.idContenuto = idContenuto;
        this.file = file;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.visibilita = visibilita;
    }

    public void EliminaContenuto(int idContenuto) {
        if (this.idContenuto == idContenuto) {
            this.file = null;
            this.titolo = null;
            this.descrizione = null;
            this.visibilita = null;
        }
    }

    public int getIdContenuto() {
        return idContenuto;
    }

    public void setIdContenuto(int idContenuto) {
        this.idContenuto = idContenuto;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(String visibilita) {
        this.visibilita = visibilita;
    }

    @Override
    public String toString() {
        return idContenuto + " - " + titolo + " (" + visibilita + ")";
    }
}
