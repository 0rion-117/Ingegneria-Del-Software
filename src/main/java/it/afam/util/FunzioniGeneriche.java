package it.afam.util;

import it.afam.app.Navigazione;
import it.afam.exception.FileNonSupportatoException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public final class FunzioniGeneriche {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Path DIRECTORY_FILE = Paths.get("files");
    private static final Set<String> ESTENSIONI_SUPPORTATE = Set.of(
            "jpg", "jpeg", "png",
            "mp3", "wav", "flac", "aac",
            "mp4", "mov", "avi",
            "pdf", "docx",
            "musicxml", "mscz"
    );

    private FunzioniGeneriche() {
    }

    public enum TipoPannello {
        ERRORE,
        AVVISO
    }

    public static boolean validaEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    public static boolean validaPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean validaCodiceFiscale(String cf) {
        if (cf == null) {
            return false;
        }
        return cf.toUpperCase(Locale.ROOT).matches("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z0-9]{4}[A-Z]");
    }

    public static boolean validaFile(File f) throws FileNonSupportatoException {
        if (f == null) {
            throw new FileNonSupportatoException("Errore File");
        }
        if (!f.isFile()) {
            throw new FileNonSupportatoException("Errore File");
        }
        String estensione = estensione(f.getName());
        if (!ESTENSIONI_SUPPORTATE.contains(estensione)) {
            throw new FileNonSupportatoException("Errore File");
        }
        return true;
    }

    public static boolean validaFile(String percorsoFile) throws FileNonSupportatoException {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new FileNonSupportatoException("Errore File");
        }
        return validaFile(new File(percorsoFile));
    }

    public static String salvaFileContenuto(String percorsoFile, String usernameStudente)
            throws IOException, FileNonSupportatoException {
        validaFile(percorsoFile);
        Path origine = Paths.get(percorsoFile).normalize();
        if (fileGiaInArchivio(origine)) {
            Path archivio = DIRECTORY_FILE.toAbsolutePath().normalize();
            return normalizzaSeparatore(archivio.relativize(percorsoAssoluto(origine)).toString(), true);
        }

        String username = normalizzaNomeFile(usernameStudente);
        Path directoryStudente = DIRECTORY_FILE.resolve(username);
        Files.createDirectories(directoryStudente);

        String nomeOriginale = origine.getFileName().toString();
        String estensione = estensione(nomeOriginale);
        String nomeBase = nomeSenzaEstensione(nomeOriginale);
        String nomeDestinazione = nomeDisponibile(directoryStudente, nomeBase, estensione);
        Path destinazione = directoryStudente.resolve(nomeDestinazione);

        Files.copy(origine, destinazione);
        return normalizzaSeparatore(DIRECTORY_FILE.resolve(username).resolve(nomeDestinazione).toString(), false);
    }

    public static boolean eliminaFileContenutoGestito(String percorsoFile) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            return false;
        }
        try {
            Path percorso = Paths.get(percorsoFile).normalize();
            if (!fileGiaInArchivio(percorso)) {
                return false;
            }
            return Files.deleteIfExists(percorsoAssoluto(percorso));
        } catch (IOException | InvalidPathException e) {
            return false;
        }
    }

    public static boolean fileContenutoGestito(String percorsoFile) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            return false;
        }
        try {
            return fileGiaInArchivio(Paths.get(percorsoFile).normalize());
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public static String criptaPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 non disponibile", e);
        }
    }

    public static String CriptaPassword(String password) {
        return criptaPassword(password);
    }

    public static String generaCodiceNumerico(int lunghezza) {
        StringBuilder codice = new StringBuilder();
        for (int i = 0; i < lunghezza; i++) {
            codice.append(RANDOM.nextInt(10));
        }
        return codice.toString();
    }

    public static String generaToken() {
        return UUID.randomUUID().toString();
    }

    public static void mostraMessaggio(String messaggio, TipoPannello tipo) {
        if (tipo == TipoPannello.ERRORE) {
            Navigazione.MostraPannelloErrore(messaggio);
        } else {
            Navigazione.MostraPannelloAvviso(messaggio);
        }
    }

    public static String estensione(String nomeFile) {
        if (nomeFile == null) {
            return "";
        }
        int punto = nomeFile.lastIndexOf('.');
        if (punto < 0 || punto == nomeFile.length() - 1) {
            return "";
        }
        return nomeFile.substring(punto + 1).toLowerCase(Locale.ROOT);
    }

    private static boolean fileGiaInArchivio(Path percorso) {
        Path assoluto = percorsoAssoluto(percorso);
        Path archivio = DIRECTORY_FILE.toAbsolutePath().normalize();
        return assoluto.startsWith(archivio) && Files.isRegularFile(assoluto);
    }

    private static Path percorsoAssoluto(Path percorso) {
        return percorso.isAbsolute() ? percorso.normalize() : percorso.toAbsolutePath().normalize();
    }

    private static String nomeDisponibile(Path directory, String nomeBase, String estensione) {
        String nome = nomeBase + "." + estensione;
        int indice = 1;
        while (Files.exists(directory.resolve(nome))) {
            nome = nomeBase + "_" + indice + "." + estensione;
            indice++;
        }
        return nome;
    }

    private static String nomeSenzaEstensione(String nomeFile) {
        int punto = nomeFile.lastIndexOf('.');
        String nome = punto > 0 ? nomeFile.substring(0, punto) : nomeFile;
        return normalizzaNomeFile(nome);
    }

    private static String normalizzaNomeFile(String valore) {
        if (valore == null || valore.isBlank()) {
            return "studente";
        }
        String normalizzato = valore.trim().replaceAll("[^A-Za-z0-9._-]", "_");
        return normalizzato.isBlank() ? "studente" : normalizzato;
    }

    private static String normalizzaSeparatore(String percorso, boolean aggiungiDirectoryFile) {
        String normalizzato = percorso.replace(File.separatorChar, '/');
        return aggiungiDirectoryFile ? "files/" + normalizzato : normalizzato;
    }
}
