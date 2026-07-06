package it.afam.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class ViewPannelloLink {
    @FXML private TextField linkField;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void CreaPannelloLink() {
    }

    public void MostraLinkCondivisione(String linkCartella) {
        linkField.setText(linkCartella);
    }

    public void CopiaLink() {
        ClipboardContent content = new ClipboardContent();
        content.putString(linkField.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }

    public void ClickChiudi() {
        ChiudiPannello();
    }

    public void ChiudiPannello() {
        if (stage != null) {
            stage.close();
        }
    }
}
