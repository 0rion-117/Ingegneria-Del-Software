# Sistema Identità Digitale Studenti AFAM

Applicazione desktop JavaFX per il progetto **Sistema Identità Digitale Studenti AFAM**.

L'applicazione parte dalla schermata `ModuloLogin` e crea automaticamente il database SQLite `afam.db` al primo avvio.

## Nota sul perimetro dell'autenticazione SPID

L'autenticazione tramite SPID è modellata nei documenti di analisi e progettazione, inclusi RAD, sequence diagram e class diagram, ma non è richiesta come integrazione reale nell'implementazione JavaFX.

Il pulsante **Accedi con SPID** è quindi mantenuto come flusso dimostrativo e simulato.

L'applicazione non comunica con un Identity Provider SPID esterno e non esegue una reale verifica delle credenziali presso un provider. Il recupero del codice fiscale e degli altri dati necessari viene simulato utilizzando le informazioni già presenti nel database locale.

Di conseguenza, eventuali operazioni modellate nei diagrammi, come `VerificaCredenzialiProvider(...)`, rappresentano il comportamento teorico del sistema completo e non corrispondono necessariamente a metodi implementati nel codice JavaFX.

Questa scelta è intenzionale e non deve essere considerata una funzionalità mancante.

## Requisiti

Per compilare e avviare il software servono:

* Windows 10 o Windows 11
* Java JDK 17 o superiore
* Apache Maven
* Connessione Internet al primo avvio di Maven, necessaria per scaricare le dipendenze JavaFX e SQLite

## Installazione rapida con winget

Aprire PowerShell come utente normale ed eseguire:

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
winget install Apache.Maven
```

Al termine dell'installazione, chiudere e riaprire PowerShell, quindi verificare:

```powershell
java -version
mvn -version
```

La versione di Java deve essere 17 o superiore.

## Installazione manuale

Se `winget` non è disponibile:

1. Installare Java JDK 17 da Adoptium Temurin.
2. Installare Apache Maven dal sito ufficiale di Maven.
3. Configurare la variabile `JAVA_HOME`.
4. Aggiungere Java e Maven al `PATH` di Windows.
5. Chiudere e riaprire PowerShell.
6. Verificare l'installazione con:

```powershell
java -version
mvn -version
```

## Installazione di Maven senza permessi di amministratore

Se Maven non viene installato correttamente con `winget`, oppure il comando `mvn` continua a non essere riconosciuto, è possibile installarlo nell'area utente tramite PowerShell.

Aprire PowerShell ed eseguire:

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

Write-Host "Installazione completata. Verifica della versione:" -ForegroundColor Green
mvn -version
```

Questa procedura installa Maven nella cartella:

```text
%LOCALAPPDATA%\Apache\apache-maven-3.9.9
```

Non sono richiesti permessi di amministratore.

Dopo l'installazione, chiudere e riaprire PowerShell per rendere permanente il nuovo valore del `PATH`.

## Avvio del software

Da PowerShell, entrare nella cartella del progetto:

```powershell
cd "C:\Users\miche\Desktop\CODICE SEIDITA"
```

Compilare il progetto:

```powershell
mvn clean compile
```

Avviare l'applicazione JavaFX:

```powershell
mvn javafx:run
```

## Primo utilizzo

Non sono presenti credenziali iniziali obbligatorie.

Per usare il software:

1. Avviare l'applicazione.
2. Dalla schermata `ModuloLogin`, selezionare `Registrati`.
3. Creare un nuovo account Studente.
4. Tornare alla schermata di login.
5. Accedere con le credenziali appena create.

Il codice per l'autenticazione a due fattori viene stampato nella console di PowerShell, perché il progetto non utilizza un servizio e-mail reale.

Anche il link per il reset della password viene stampato nella console.

## Database

Il database viene creato automaticamente nella cartella principale del progetto:

```text
afam.db
```

Il file `afam.db` contiene:

* account degli studenti;
* contenuti salvati;
* cartelle;
* link dedicati;
* notifiche;
* altri metadati utilizzati dal software.

Non cancellare il database se si vogliono conservare i dati inseriti.

Per ripartire da zero durante i test:

1. Chiudere l'applicazione.
2. Eliminare il file `afam.db`.
3. Avviare nuovamente il software.

Al successivo avvio, il database verrà ricreato automaticamente. Gli account e i metadati precedenti non saranno più disponibili.

## File caricati

Quando uno studente carica una foto, un PDF o un altro file supportato, il programma copia il file nella seguente cartella del progetto:

```text
files\<username>\
```

Nel database viene salvato il percorso della copia interna, per esempio:

```text
files\mariorossi\nomefile.pdf
```

Il file originale selezionato dal computer non viene spostato né cancellato.

Quando un contenuto viene eliminato dal software, viene eliminata solamente la copia interna gestita dal progetto.

## Problemi comuni

### `java` non riconosciuto

Java non è installato oppure non è presente nel `PATH`.

Installarlo con:

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
```

Dopo l'installazione, chiudere e riaprire PowerShell.

### `mvn` non riconosciuto

Maven non è installato oppure non è presente nel `PATH`.

Installarlo con:

```powershell
winget install Apache.Maven
```

Se il problema persiste, seguire la sezione [Installazione di Maven senza permessi di amministratore](#installazione-di-maven-senza-permessi-di-amministratore).

Dopo l'installazione, chiudere e riaprire PowerShell.

### Maven scarica molte dipendenze

È normale al primo avvio.

Maven deve scaricare JavaFX, il driver JDBC di SQLite e i plugin necessari alla compilazione e all'esecuzione del progetto.

### Errore sul database

Verificare che l'applicazione disponga dei permessi di scrittura nella cartella:

```text
C:\Users\miche\Desktop\CODICE SEIDITA
```

Se il database risulta corrotto durante i test:

1. Chiudere l'applicazione.
2. Eliminare il file `afam.db`.
3. Riavviare il software.
