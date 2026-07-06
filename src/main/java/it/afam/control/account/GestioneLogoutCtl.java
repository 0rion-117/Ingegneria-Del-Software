package it.afam.control.account;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;

public class GestioneLogoutCtl {
    public void AvviaLogout() {
        TerminaSessioneStudente();
        Navigazione.MostraModuloLogin();
    }

    public void TerminaSessioneStudente() {
        Sessione.TerminaSessioneStudente();
    }
}
