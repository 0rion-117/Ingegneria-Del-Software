package it.afam.model;

import java.util.ArrayList;
import java.util.List;

public class Cartella {
    private int idCartella;
    private String nomeCartella;
    private List<Integer> listaIdContenuti;
    private String linkCartella;
    private int numeroVisualizzazioni;

    public Cartella() {
        this.listaIdContenuti = new ArrayList<>();
    }

    public Cartella(int idCartella, String nomeCartella, List<Integer> listaIdContenuti,
                    String linkCartella, int numeroVisualizzazioni) {
        this.idCartella = idCartella;
        this.nomeCartella = nomeCartella;
        this.listaIdContenuti = listaIdContenuti == null ? new ArrayList<>() : new ArrayList<>(listaIdContenuti);
        this.linkCartella = linkCartella;
        this.numeroVisualizzazioni = numeroVisualizzazioni;
    }

    public Cartella CreaCartella(String nomeCartella, List<Integer> listaIdContenuti) {
        return new Cartella(0, nomeCartella, listaIdContenuti, null, 0);
    }

    public void AggiornaComposizioneCartella(int idCartella, List<Integer> listaIdContenuti) {
        this.idCartella = idCartella;
        this.listaIdContenuti = new ArrayList<>(listaIdContenuti);
    }

    public void EliminaCartella(int idCartella) {
        if (this.idCartella == idCartella) {
            this.nomeCartella = null;
            this.listaIdContenuti = new ArrayList<>();
            this.linkCartella = null;
            this.numeroVisualizzazioni = 0;
        }
    }

    public void SetLinkCartella(String linkCartella) {
        this.linkCartella = linkCartella;
    }

    public int SetNumeroVisualizzazioni(int numeroVisualizzazioni) {
        this.numeroVisualizzazioni = numeroVisualizzazioni;
        return this.numeroVisualizzazioni;
    }

    public int getIdCartella() {
        return idCartella;
    }

    public void setIdCartella(int idCartella) {
        this.idCartella = idCartella;
    }

    public String getNomeCartella() {
        return nomeCartella;
    }

    public void setNomeCartella(String nomeCartella) {
        this.nomeCartella = nomeCartella;
    }

    public List<Integer> getListaIdContenuti() {
        return new ArrayList<>(listaIdContenuti);
    }

    public void setListaIdContenuti(List<Integer> listaIdContenuti) {
        this.listaIdContenuti = listaIdContenuti == null ? new ArrayList<>() : new ArrayList<>(listaIdContenuti);
    }

    public String getLinkCartella() {
        return linkCartella;
    }

    public void setLinkCartella(String linkCartella) {
        this.linkCartella = linkCartella;
    }

    public int getNumeroVisualizzazioni() {
        return numeroVisualizzazioni;
    }

    public void setNumeroVisualizzazioni(int numeroVisualizzazioni) {
        this.numeroVisualizzazioni = numeroVisualizzazioni;
    }

    @Override
    public String toString() {
        return idCartella + " - " + nomeCartella;
    }
}
