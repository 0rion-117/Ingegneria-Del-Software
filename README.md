# Sistema Identita Digitale Studenti AFAM

Applicazione desktop JavaFX per il progetto "Sistema Identita Digitale Studenti AFAM".

L'applicazione parte dalla schermata `ModuloLogin` e crea automaticamente il database SQLite `afam.db` al primo avvio.

## Requisiti

Per compilare e avviare il software servono:

- Windows 10 o Windows 11
- Java JDK 17 o superiore
- Apache Maven
- Connessione internet al primo avvio Maven, per scaricare le dipendenze JavaFX e SQLite

## Installazione rapida con winget

Aprire PowerShell come utente normale e lanciare:

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
winget install Apache.Maven
```

Al termine dell'installazione chiudere e riaprire PowerShell, poi verificare:

```powershell
java -version
mvn -version
```

La versione di Java deve essere 17 o superiore.

## Installazione manuale

Se `winget` non e' disponibile:

1. Installare Java JDK 17 da Adoptium Temurin.
2. Installare Apache Maven dal sito ufficiale di Maven.
3. Aggiungere `JAVA_HOME` e Maven al `PATH` di Windows.
4. Chiudere e riaprire PowerShell.
5. Verificare con:

```powershell
java -version
mvn -version
```

## Installazione Maven senza permessi admin

Se Maven non viene installato correttamente con `winget`, oppure `mvn` continua a non essere riconosciuto, e' possibile installarlo nell'area utente con PowerShell.

Aprire PowerShell e lanciare:

```powershell
$installDir = "$env:LOCALAPPDATA\Apache"
New-Item -ItemType Directory -Force -Path $installDir | Out-Null

Write-Host "Download di Apache Maven in corso..." -ForegroundColor Cyan
Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip" -OutFile "$env:TEMP\maven.zip"

Write-Host "Estrazione dei file in corso..." -ForegroundColor Cyan
Expand-Archive -Path "$env:TEMP\maven.zip" -DestinationPath $installDir -Force
Remove-Item -Path "$env:TEMP\maven.zip" -Force

$binPath = "$installDir\apache-maven-3.9.9\bin"

Write-Host "Configurazione del sistema..." -ForegroundColor Cyan
$userPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($userPath -notlike "*$binPath*") {
    [Environment]::SetEnvironmentVariable("Path", "$userPath;$binPath", "User")
}

$env:Path += ";$binPath"

Write-Host "Installazione completata! Verifica versione:" -ForegroundColor Green
mvn -version
```

Questa procedura installa Maven in:

```text
%LOCALAPPDATA%\Apache\apache-maven-3.9.9
```

Non richiede permessi di amministratore. Dopo l'installazione, chiudere e riaprire PowerShell per rendere stabile il nuovo `PATH`.

## Avvio del software

Da PowerShell entrare nella cartella del progetto:

```powershell
cd "C:\Users\miche\Desktop\CODICE SEIDITA\mio-codice"
```

Compilare:

```powershell
mvn clean compile
```

Avviare l'app JavaFX:

```powershell
mvn javafx:run
```

## Primo utilizzo

Non ci sono credenziali iniziali obbligatorie.

Per usare il software:

1. Avviare l'app.
2. Dalla schermata `ModuloLogin`, scegliere `Registrati`.
3. Creare un nuovo Studente.
4. Tornare al login e accedere.

Il codice 2FA viene stampato nella console di PowerShell, perche' il progetto non specifica un servizio e-mail reale.

Anche il link di reset password viene stampato nella console.

## Database

Il database viene creato automaticamente nella cartella del progetto:

```text
afam.db
```

`afam.db` contiene gli account, i contenuti salvati, le cartelle, i link, le notifiche e gli altri metadati del software. Non cancellarlo se si vogliono conservare i dati inseriti dagli studenti.

Se si vuole ripartire da zero durante i test, chiudere l'applicazione e cancellare `afam.db`. Al successivo avvio verra' ricreato automaticamente, ma gli account e i metadati precedenti non saranno piu' disponibili.

## File caricati

Quando uno studente carica una foto, un PDF o un altro file supportato, il programma copia il file dentro la cartella del progetto:

```text
files\<username>\
```

Nel database viene salvato il percorso della copia interna, per esempio:

```text
files\mariorossi\nomefile.pdf
```

Il file originale scelto dal PC non viene spostato ne' cancellato. Se un contenuto viene eliminato dal software, viene eliminata solo la copia interna gestita dal progetto.

## Problemi comuni

### `java` non riconosciuto

Java non e' installato oppure non e' nel `PATH`.

Soluzione:

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
```

Poi chiudere e riaprire PowerShell.

### `mvn` non riconosciuto

Maven non e' installato oppure non e' nel `PATH`.

Soluzione rapida con `winget`:

```powershell
winget install Apache.Maven
```

Se non funziona, usare la sezione `Installazione Maven senza permessi admin`.

Poi chiudere e riaprire PowerShell.

### Maven scarica molte dipendenze

E' normale al primo avvio. Maven scarica JavaFX, SQLite JDBC e i plugin necessari.

### Errore sul database

Verificare che l'app abbia permesso di scrivere nella cartella:

```text
C:\Users\miche\Desktop\CODICE SEIDITA\mio-codice
```

Se il database e' corrotto durante i test, chiudere l'app e cancellare `afam.db`.
