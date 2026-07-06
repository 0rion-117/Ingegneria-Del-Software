package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.util.FunzioniGeneriche;

public class GestioneVerifica2FACtl {
    private String email;
    private String codiceTemporaneo;
    private String codiceInserito;

    public void AvviaVerifica2FA(String email) {
        this.email = email;
        codiceTemporaneo = GeneraCodiceNumericoTemporaneo();
        Sessione.setCodiceTemporaneo2FA(codiceTemporaneo);
        InviaCodiceEmail(codiceTemporaneo);
        Navigazione.MostraFormVerifica2FA();
    }

    public String GeneraCodiceNumericoTemporaneo() {
        return FunzioniGeneriche.generaCodiceNumerico(6);
    }

    public void InviaCodiceEmail(String codiceTemporaneo) {
        System.out.println("Codice 2FA per " + email + ": " + codiceTemporaneo);
    }

    public void InviaCodice(String codiceInserito) {
        this.codiceInserito = codiceInserito;
        String codiceAtteso = Sessione.getCodiceTemporaneo2FA();
        if (VerificaCodice(codiceInserito, codiceAtteso)) {
            Navigazione.MostraPannelloAvviso("Avvenuta autenticazione");
            Sessione.confermaVerifica2FA();
            Navigazione.MostraInterfacciaPrincipaleStudente();
        } else {
            Navigazione.MostraPannelloErrore("Codice non corrispondente");
        }
    }

    public boolean VerificaCodice(String codiceInserito, String codiceTemporaneo) {
        return codiceTemporaneo != null && codiceTemporaneo.equals(codiceInserito);
    }
}
