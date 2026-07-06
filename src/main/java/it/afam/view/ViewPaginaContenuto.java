package it.afam.view;

import it.afam.app.Navigazione;
import it.afam.app.Sessione;
import it.afam.model.Contenuto;
import it.afam.util.FunzioniGeneriche;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewPaginaContenuto {
    @FXML private Label titoloLabel;
    @FXML private Label fileLabel;
    @FXML private Label visibilitaLabel;
    @FXML private TextArea descrizioneArea;
    @FXML private StackPane contenutoPane;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        MostraPaginaContenuto(Sessione.getContenutoCorrente());
    }

    public void MostraPaginaContenuto(Contenuto contenuto) {
        if (contenuto == null) {
            return;
        }
        titoloLabel.setText(contenuto.getTitolo());
        fileLabel.setText(contenuto.getFile());
        visibilitaLabel.setText(contenuto.getVisibilita());
        descrizioneArea.setText(contenuto.getDescrizione());
        MostraContenutoMultimediale(contenuto.getFile());
    }

    public void ClickIndietro() {
        FermaRiproduzione();
        Navigazione.MostraSchermataPrecedente();
    }

    private void MostraContenutoMultimediale(String percorsoFile) {
        FermaRiproduzione();
        contenutoPane.getChildren().clear();

        File file = new File(percorsoFile == null ? "" : percorsoFile);
        if (!file.isFile()) {
            contenutoPane.getChildren().setAll(new Label("File non trovato"));
            return;
        }

        String estensione = FunzioniGeneriche.estensione(file.getName());
        switch (estensione) {
            case "jpg", "jpeg", "png" -> MostraImmagine(file);
            case "pdf" -> MostraPdf(file);
            case "mp4", "mov", "avi" -> MostraVideo(file);
            case "mp3", "wav", "flac", "aac" -> MostraAudio(file);
            default -> MostraAperturaEsterna(file, "Apri file");
        }
    }

    private void MostraImmagine(File file) {
        ImageView imageView = nuovaImageView(new Image(file.toURI().toString()));
        contenutoPane.getChildren().setAll(imageView);
    }

    private void MostraPdf(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            if (document.getNumberOfPages() == 0) {
                MostraAperturaEsterna(file, "Apri PDF");
                return;
            }
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage immaginePagina = renderer.renderImageWithDPI(0, 120);
            ImageView imageView = nuovaImageView(convertiImmagine(immaginePagina));
            Button apriButton = new Button("Apri PDF");
            apriButton.setOnAction(event -> ApriFile(file));
            VBox box = new VBox(8, imageView, apriButton);
            box.setAlignment(Pos.CENTER);
            contenutoPane.getChildren().setAll(box);
        } catch (IOException e) {
            MostraAperturaEsterna(file, "Apri PDF");
        }
    }

    private void MostraVideo(File file) {
        try {
            mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        } catch (RuntimeException e) {
            MostraAperturaEsterna(file, "Apri video");
            return;
        }
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.fitWidthProperty().bind(contenutoPane.widthProperty().subtract(24));
        mediaView.fitHeightProperty().bind(contenutoPane.heightProperty().subtract(70));

        VBox box = new VBox(8, mediaView, controlliMedia());
        box.setAlignment(Pos.CENTER);
        contenutoPane.getChildren().setAll(box);
        mediaPlayer.setOnError(() -> ErroreRiproduzione(file, "Apri video"));
    }

    private void MostraAudio(File file) {
        try {
            mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        } catch (RuntimeException e) {
            MostraAperturaEsterna(file, "Apri audio");
            return;
        }
        Label nomeFile = new Label(file.getName());
        VBox box = new VBox(14, nomeFile, controlliMedia());
        box.setAlignment(Pos.CENTER);
        contenutoPane.getChildren().setAll(box);
        mediaPlayer.setOnError(() -> ErroreRiproduzione(file, "Apri audio"));
    }

    private HBox controlliMedia() {
        Button playButton = new Button("Play");
        Button pausaButton = new Button("Pausa");
        Button stopButton = new Button("Stop");
        playButton.setOnAction(event -> mediaPlayer.play());
        pausaButton.setOnAction(event -> mediaPlayer.pause());
        stopButton.setOnAction(event -> mediaPlayer.stop());
        HBox controlli = new HBox(8, playButton, pausaButton, stopButton);
        controlli.setAlignment(Pos.CENTER);
        return controlli;
    }

    private void MostraAperturaEsterna(File file, String testoPulsante) {
        Button apriButton = new Button(testoPulsante);
        apriButton.setOnAction(event -> ApriFile(file));
        VBox box = new VBox(10, new Label(file.getName()), apriButton);
        box.setAlignment(Pos.CENTER);
        contenutoPane.getChildren().setAll(box);
    }

    private ImageView nuovaImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(contenutoPane.widthProperty().subtract(24));
        imageView.fitHeightProperty().bind(contenutoPane.heightProperty().subtract(24));
        return imageView;
    }

    private Image convertiImmagine(BufferedImage bufferedImage) {
        WritableImage image = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        PixelWriter writer = image.getPixelWriter();
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                writer.setArgb(x, y, bufferedImage.getRGB(x, y));
            }
        }
        return image;
    }

    private void ApriFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                Navigazione.MostraPannelloErrore("Errore nella visualizzazione del Contenuto");
            }
        } catch (IOException e) {
            Navigazione.MostraPannelloErrore("Errore nella visualizzazione del Contenuto");
        }
    }

    private void ErroreRiproduzione(File file, String testoPulsante) {
        FermaRiproduzione();
        MostraAperturaEsterna(file, testoPulsante);
    }

    private void FermaRiproduzione() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}
