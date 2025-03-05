<h1 align="center">Projecte Trivial - 1R DAM 2024/2025 - EloiCortiella</h1>
Aquest projecte tracta d'una aplicació de joc de preguntes Trivial basat en Java. 

El projecte utilitza Swing per a la interfície gràfica d'usuari i Maven per a la gestió de dependències i repositoris.

## Funcionalitats que té l'aplicació

- Gestionar els jugadors d'una partida (Afegir d'una taula, eliminar d'una taula i Borrar un jugador del joc)
- Iniciar i jugar una partida de preguntes amb els jugadors afegits
- Fer el seguiment de les puntuacions dels jugadors
- Guardar i carregar configuracions de joc, preguntes i usuaris usats en partides anteriors
- Mostrar rànquings de jugadors durant la partida ordenats per puntuació

## Requisits

- Java 11 o superior
- Maven 3.6.0 o superior

## Dependències i plugins del projecte

- `Mp0485UsefulCode`: Llibreria de codi útil per a Java que conté classes i mètodes reutilitzables.
- `jackson-databind`: Llibreria de Jackson per a la conversió d'objectes Java a JSON i viceversa.
- `jackson-core`: Llibreria de Jackson per a la manipulació de JSON.
- `javadoc`: Plugin de Maven per a la generació de la documentació Javadoc.

## Instal·lació

1. Clona el repositori:
    ```sh
    git clone https://github.com/Eloi-Cortiella/trivial.git
    cd trivial
    ```

## Ús

1. Executa l'aplicació:
    ```sh
    mvn exec:java -Dexec.mainClass="org.trivial.gui.FinestraInicial"
    ```
   També pots executar l'aplicació des de l'IDE que s'utilitze. Aqui s'ha usat IntelliJ IDEA Ultimate Edition.


2. Segueix les instruccions en pantalla per afegir jugadors i començar el joc.

## Estructura del Projecte

- `src/main/java/org/trivial/gui/`: Conté les classes de la GUI.
- `src/main/resources/`: Conté fitxers de recursos com fitxers de configuració i dades de preguntes.

## Classes Principals (Finestres)

### `FinestraInicial`

La classe `FinestraInicial` és la finestra d'inici de l'aplicació. Aquesta finestra permet als usuaris seleccionar el mode de joc (un sol jugador o multijugador) i configurar el nombre de jugadors.

---

### Mètodes de la classe `FinestraInicial`

### Constructor `FinestraInicial()`

Inicialitza la finestra d'inici del joc. S'encarrega de configurar les propietats de la finestra, com el títol, la mida, la posició, etc. També inicialitza els components de la interfície gràfica i estableix els listeners per a gestionar les accions de l'usuari.

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Inici".
   - Contingut: `pantallaInicial` (un panell que conté tots els components).
   - Operació de tancament: `EXIT_ON_CLOSE`.
   - La finestra no és redimensionable.
   - La finestra es mostra al centre de la pantalla.

2. **Inicialització dels components**:
   - `labelMulti` i `spinnerJugadors` estan inicialment ocults.
   - S'estableixen els listeners per als botons i components interactius.

---

### Mètode `actionPerformed()` del botó `botoIniciarPartida`

S'executa quan l'usuari prem el botó "Iniciar Partida". Depenent de la selecció de l'usuari (un sol jugador o multijugador), s'inicia una partida amb el nombre de jugadors corresponent.

### Accions principals:
1. **Mode un sol jugador**:
   - Si l'usuari selecciona "Un sol jugador", es configura `numJugadors` a 1.
   - Es mostra un missatge confirmant l'inici de la partida.
   - Es tanca la finestra actual i s'obre la finestra `FinestraJugadors`.

2. **Mode multijugador**:
   - Si l'usuari selecciona "Multijugador", es llegeix el valor del `spinnerJugadors`.
   - Si el nombre de jugadors és 0, es mostra un missatge d'error.
   - Si el nombre de jugadors és vàlid, s'inicia la partida amb el nombre de jugadors seleccionat.
   - Es tanca la finestra actual i s'obre la finestra `FinestraJugadors`.

---

### Mètode `actionPerformed()` del botó `botoTutorial`

S'executa quan l'usuari prem el botó "Tutorial". Mostra un diàleg amb les regles bàsiques del joc.

### Accions principals:
1. Es mostra un missatge amb les regles del joc:
   - Selecció del mode de joc (un sol jugador o multijugador).
   - Configuració de la partida (nombre de preguntes, punts necessaris per guanyar, etc.).
   - Condicions per guanyar la partida.

---

### Mètode `stateChanged()` del `spinnerJugadors`

S'executa quan l'usuari canvia el valor del `spinnerJugadors`. Limita el nombre de jugadors a un rang entre 2 i 4.

### Accions principals:
1. Si el valor del `spinner` és superior a 4:
   - Es fixa el valor a 4.
   - Es mostra un missatge d'error indicant que el nombre de jugadors no pot ser superior a 4.

2. Si el valor del `spinner` és inferior a 2:
   - Es fixa el valor a 2.
   - Es mostra un missatge d'error indicant que el nombre de jugadors no pot ser inferior a 2.

---

### Mètodes `actionPerformed()` dels RadioButtons

S'executen quan l'usuari selecciona una opció dels RadioButtons (`unSolJugadorRadioButton` o `multijugador2A4RadioButton`).

### Accions principals:
1. **Selecció de "Multijugador (2 a 4 jugadors)"**:
   - Es mostra el `labelMulti` i el `spinnerJugadors`.
   - Es fixa el valor del `spinner` a 2.
   - Es desactiva l'opció "Un sol jugador".

2. **Selecció de "Un sol jugador"**:
   - S'amaga el `labelMulti` i el `spinnerJugadors`.
   - Es desactiva l'opció "Multijugador (2 a 4 jugadors)".

---

### Mètode `main()`

És el punt d'entrada del programa. Crea una instància de la finestra `FinestraInicial` i la mostra.

### Accions principals:
1. Es crea una instància de `FinestraInicial` dins de l'Event Dispatch Thread (EDT) de Swing.
2. Es configura la mida de la finestra a 400x200 píxels.
3. Si es produeix algun error durant la inicialització, es mostra un missatge d'error i es tanca l'aplicació.

---
### `FinestraJugadors`

La classe `FinestraJugadors` és una finestra que permet configurar la partida del joc de trivial. Aquesta finestra permet afegir jugadors, seleccionar la categoria de preguntes, configurar el nombre de preguntes i la puntuació necessària per guanyar, i iniciar el joc. A continuació, s'explica el funcionament dels mètodes principals:

---

### Constructor `FinestraJugadors()`

Inicialitza la finestra de configuració de la partida. S'encarrega de configurar les propietats de la finestra, com el títol, la mida, la posició, etc. També inicialitza els components de la interfície gràfica i estableix els listeners per a gestionar les accions de l'usuari.

### `Explicació dels mètodes`

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Configuració de la partida".
   - Contingut: `panelJ` (un panell que conté tots els components).
   - Operació de tancament: `DO_NOTHING_ON_CLOSE` (es gestiona manualment el tancament).
   - La finestra no és redimensionable.
   - La finestra es mostra al centre de la pantalla.

2. **Inicialització dels components**:
   - Es carreguen les dades dels usuaris des d'un fitxer d'accés directe (`dafUs`).
   - Es configuren els models de dades per a les taules d'usuaris i jugadors.
   - S'inicialitzen els spinners i el combo box de categories.

3. **Listeners**:
   - S'estableixen els listeners per als botons, taules i spinners.

---

### Mètode `actionPerformed()` del botó `botoAfegirUsuari`

S'executa quan l'usuari prem el botó "Afegir Usuari". Afegeix un nou usuari a la llista d'usuaris si el nom no està en blanc i no està repetit.

### Accions principals:
1. **Comprovació de duplicats**:
   - Es comprova si el nom introduït ja existeix a la llista d'usuaris.
   - Si el nom ja existeix, es mostra un missatge d'error.

2. **Afegir usuari**:
   - Si el nom no està en blanc i no està repetit, s'afegeix l'usuari al fitxer d'accés directe i a la taula d'usuaris.

---

### Mètode `actionPerformed()` del botó `iniciarJoc`

S'executa quan l'usuari prem el botó "Iniciar Joc". Inicia la partida si s'han afegit tots els jugadors necessaris.

### Accions principals:
1. **Comprovació del nombre de jugadors**:
   - Si no s'han afegit tots els jugadors, es mostra un missatge d'error.

2. **Configuració de la partida**:
   - Es guarden les dades de configuració (categoria, nombre de preguntes, puntuació necessària, etc.).
   - Es carreguen les preguntes del banc de preguntes segons la categoria seleccionada.

3. **Iniciar el joc**:
   - Es tanca la finestra actual i s'obre la finestra del joc (`FinestraJoc`).

---

### Mètode `mouseClicked()` de la taula `tableUsuaris`

S'executa quan l'usuari fa doble clic a la taula d'usuaris. Afegeix l'usuari seleccionat a la llista de jugadors.

### Accions principals:
1. **Selecció de l'usuari**:
   - Es comprova si s'ha seleccionat una fila vàlida.
   - Si s'ha seleccionat una fila, s'afegeix l'usuari a la taula de jugadors i s'elimina de la taula d'usuaris.

2. **Comprovació del nombre de jugadors**:
   - Si ja s'han afegit tots els jugadors necessaris, es mostra un missatge d'error.

---

### Mètode `mouseClicked()` de la taula `tableJugadors`

S'executa quan l'usuari fa doble clic a la taula de jugadors. Elimina l'usuari seleccionat de la llista de jugadors i el torna a la llista d'usuaris.

### Accions principals:
1. **Selecció de l'usuari**:
   - Es comprova si s'ha seleccionat una fila vàlida.
   - Si s'ha seleccionat una fila, s'elimina l'usuari de la taula de jugadors i es torna a afegir a la taula d'usuaris.

2. **Actualització de la puntuació**:
   - Es recupera la puntuació total de l'usuari des del fitxer d'accés directe.

---

### Mètode `stateChanged()` del `spinnerPreguntes`

S'executa quan l'usuari canvia el valor del `spinnerPreguntes`. Limita el nombre de preguntes a un rang entre 10 i 20.

### Accions principals:
1. Si el valor del `spinner` és superior a 20:
   - Es fixa el valor a 20.
   - Es mostra un missatge d'error indicant que el nombre de preguntes no pot ser superior a 20.

2. Si el valor del `spinner` és inferior a 10:
   - Es fixa el valor a 10.
   - Es mostra un missatge d'error indicant que el nombre de preguntes no pot ser inferior a 10.

---

### Mètode `stateChanged()` del `spinnerPuntuacio`

S'executa quan l'usuari canvia el valor del `spinnerPuntuacio`. Limita la puntuació necessària per guanyar a un rang entre 15 i 30.

### Accions principals:
1. Si el valor del `spinner` és superior a 30:
   - Es fixa el valor a 30.
   - Es mostra un missatge d'error indicant que la puntuació no pot ser superior a 30.

2. Si el valor del `spinner` és inferior a 15:
   - Es fixa el valor a 15.
   - Es mostra un missatge d'error indicant que la puntuació no pot ser inferior a 15.

---

### Mètode `actionPerformed()` del botó `buttonEliminar`

S'executa quan l'usuari prem el botó "Eliminar". Elimina l'usuari seleccionat de la llista d'usuaris.

### Accions principals:
1. **Confirmació de l'eliminació**:
   - Es demana confirmació a l'usuari abans d'eliminar l'usuari.

2. **Eliminació de l'usuari**:
   - Si l'usuari confirma, s'elimina l'usuari de la taula d'usuaris i del fitxer d'accés directe.

---

### Mètode `main()`

És el punt d'entrada del programa. Crea una instància de la finestra `FinestraJugadors` i la mostra.

### Accions principals:
1. Es crea una instància de `FinestraJugadors` dins de l'Event Dispatch Thread (EDT) de Swing.
2. Es configura la mida de la finestra a 600x400 píxels.
3. Si es produeix algun error durant la inicialització, es mostra un missatge d'error i es tanca l'aplicació.

---

### `FinestraJoc`

### Explicació dels mètodes de la classe `FinestraJoc`

La classe `FinestraJoc` és la finestra principal del joc de trivial. Aquesta finestra mostra les preguntes, gestiona el temps de resposta, actualitza la puntuació dels jugadors i controla el canvi de torns. A continuació, s'explica el funcionament dels mètodes principals:

---

### Constructor `FinestraJoc(FinestraRanking finestraRanking)`

Inicialitza la finestra del joc. S'encarrega de configurar les propietats de la finestra, com el títol, la mida, la posició, etc. També inicialitza els components de la interfície gràfica i estableix els listeners per a gestionar les accions de l'usuari.

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Trivial".
   - Contingut: `pantallaJoc` (un panell que conté tots els components).
   - Operació de tancament: `EXIT_ON_CLOSE`.
   - La finestra no és redimensionable.
   - La finestra es mostra al centre de la pantalla.

2. **Inicialització dels components**:
   - Es llegeix la configuració del fitxer de configuració.
   - Es configuren els components de la interfície gràfica, com els radio buttons, el botó de confirmació, la barra de progrés, etc.
   - Es mostren les dades del primer jugador i la primera pregunta.

3. **Temporitzador**:
   - Es configura un temporitzador per a gestionar el temps de resposta de cada pregunta.
   - Si el temps s'esgota, es penalitza al jugador i es canvia de torn.

---

### Mètode `actionPerformed()` del botó `botoConfirmar`

S'executa quan l'usuari prem el botó "Confirmar". Comprova la resposta seleccionada pel jugador i actualitza la puntuació en funció de si la resposta és correcta o incorrecta.

### Accions principals:
1. **Comprovació de la resposta**:
   - Es determina quina opció ha seleccionat el jugador.
   - Es comprova si la resposta seleccionada és correcta.

2. **Actualització de la puntuació**:
   - Si la resposta és correcta, s'afegeixen punts al jugador.
   - Si la resposta és incorrecta, es penalitza al jugador.

3. **Canvi de torn**:
   - Es crida al mètode `canviarTorn()` per a passar al següent jugador o a la següent pregunta.

---

### Mètode `canviarTorn()`

Gestiona el canvi de torn entre jugadors o el pas a la següent pregunta. També comprova si algun jugador ha arribat a la puntuació guanyadora o si s'han acabat les preguntes.

### Accions principals:
1. **Comprovació de la puntuació guanyadora**:
   - Si algun jugador ha arribat a la puntuació guanyadora, es mostra un missatge i es tanca el joc.

2. **Canvi de torn**:
   - Si hi ha més d'un jugador, es passa al següent jugador.
   - Si només hi ha un jugador, es continua amb la següent pregunta.

3. **Mostrar la següent pregunta**:
   - Es mostra la següent pregunta i es reinicia el temporitzador.

4. **Finalització del joc**:
   - Si s'han acabat les preguntes, es mostra un missatge i es tanca el joc.

---

### Mètode `mostrarPregunta(Pregunta Pregunta, Usuari u)`

S'encarrega de mostrar la pregunta actual i les opcions de resposta a la interfície gràfica.

### Accions principals:
1. **Actualització dels components**:
   - Es mostra el número de la pregunta.
   - Es mostra la puntuació actual del jugador.
   - Es mostra l'enunciat de la pregunta.
   - Es mostren les opcions de resposta als radio buttons.

---

### Mètode `windowClosing()` de la finestra

S'executa quan l'usuari tanca la finestra del joc. S'encarrega de tancar els fitxers oberts i netejar les dades temporals.

### Accions principals:
1. **Tancament dels fitxers**:
   - Es tanquen els fitxers d'accés directe d'usuaris, preguntes i configuració.

2. **Neteja de dades temporals**:
   - Es buiden les preguntes filtrades.

---

### Mètode `main()`

És el punt d'entrada del programa. Crea una instància de la finestra `FinestraJoc` i la mostra.

### Accions principals:
1. Es crea una instància de `FinestraRanking`.
2. Es crea una instància de `FinestraJoc` amb la finestra de ranking.
3. Si es produeix algun error durant la inicialització, es mostra un missatge d'error i es tanca l'aplicació.

---

### `FinestraRanking`

### Explicació dels mètodes de la classe `FinestraRanking`

La classe `FinestraRanking` és una finestra que mostra el ranking dels jugadors durant la partida. Aquesta finestra permet veure la puntuació de cada jugador i actualitzar-la en temps real segons les respostes correctes o incorrectes. A continuació, s'explica el funcionament dels mètodes principals:

---

### Constructor `FinestraRanking()`

Inicialitza la finestra del ranking. S'encarrega de configurar les propietats de la finestra, com el títol, la mida, la posició, etc. També inicialitza els components de la interfície gràfica i carrega les dades dels jugadors.

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Ranking de la Partida".
   - Contingut: `panelJ` (un panell que conté tots els components).
   - Operació de tancament: `DO_NOTHING_ON_CLOSE` (es gestiona manualment el tancament).
   - La finestra és redimensionable.
   - La finestra es mostra al centre de la pantalla.

2. **Inicialització dels components**:
   - Es desactiva l'edició de la taula perquè els usuaris no puguin modificar les dades manualment.
   - Es carreguen les dades dels jugadors des de la taula de resultats de la finestra `FinestraJugadors`.

3. **Omplir la taula**:
   - Es crida al mètode `omplirTaula()` per a mostrar les dades dels jugadors a la taula.

---

### Mètode `omplirTaula()`

S'encarrega de configurar i omplir la taula de ranking amb les dades dels jugadors. També aplica un estil visual per a destacar els tres primers llocs.

### Accions principals:
1. **Configuració del model de la taula**:
   - Es crea un model de taula amb les dades dels jugadors.
   - Es defineixen les columnes de la taula: "Nom" i "Puntuació de la partida".

2. **Aplicació d'estils**:
   - Es defineix un renderitzador personalitzat per a la taula que aplica colors diferents a les tres primeres files:
      - **Primer lloc**: Fons groc.
      - **Segon lloc**: Fons gris clar.
      - **Tercer lloc**: Fons taronja.
   - La resta de files tenen un fons blanc.

3. **Ordenació de les dades**:
   - Es ordenen les dades dels jugadors de major a menor puntuació.

---

### Mètode `actualitzarRanking(Usuari u, boolean acertat)`

S'executa per a actualitzar la puntuació d'un jugador al ranking després de respondre una pregunta. Depenent de si la resposta és correcta o incorrecta, s'afegeixen o es resten punts al jugador.

### Accions principals:
1. **Actualització de la puntuació**:
   - Si la resposta és correcta, s'afegeixen punts al jugador (`puntuacioPregunta`).
   - Si la resposta és incorrecta, es penalitza al jugador restant-li punts (`penalitzacioTemps`).

2. **Actualització de les dades**:
   - Es busca el jugador a la taula de dades i s'actualitza la seva puntuació.

3. **Reomplir la taula**:
   - Es crida al mètode `omplirTaula()` per a reflectir els canvis a la interfície gràfica.

---

### `FinestraFinalMulti`

# Explicació de la classe `FinestraFinalMulti`

La classe `FinestraFinalMulti` és una finestra que es mostra al final d'una partida de trivial en mode multijugador. Aquesta finestra mostra els resultats finals dels jugadors, incloent els tres primers llocs, i permet tancar el joc de manera ordenada. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

La classe `FinestraFinalMulti` té els següents atributs:

1. **`panel1`**:
   - Tipus: `JPanel`.
   - Descripció: És el panell principal que conté tots els components de la finestra.

2. **`titolResultats`**:
   - Tipus: `JLabel`.
   - Descripció: És l'etiqueta que mostra el títol de la finestra ("Resultats de la Partida").

3. **`labelPrimer`, `labelSegon`, `labelTercer`**:
   - Tipus: `JLabel`.
   - Descripció: Són les etiquetes que mostren els noms i puntuacions dels jugadors que han quedat en primer, segon i tercer lloc, respectivament.

4. **`labelPrimerLloc`, `labelSegonLloc`, `labelTercerLloc`**:
   - Tipus: `JLabel`.
   - Descripció: Són les etiquetes que mostren icones o text addicional per a indicar els llocs (opcional).

5. **`botoSortir`**:
   - Tipus: `JButton`.
   - Descripció: És el botó que permet tancar el joc.

---

### Constructor `FinestraFinalMulti()`

Inicialitza la finestra de resultats finals. S'encarrega de configurar les propietats de la finestra, mostrar els resultats dels jugadors i gestionar l'acció de tancament del joc.

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Guanyadors de la Partida".
   - Contingut: `panel1` (el panell principal).
   - Operació de tancament: `DO_NOTHING_ON_CLOSE` (es gestiona manualment el tancament).
   - La finestra es mostra al centre de la pantalla.

2. **Obtenció de les dades dels jugadors**:
   - Es recuperen les dades dels jugadors des de la taula de resultats del ranking (`FinestraRanking.taulaResultats`).

3. **Mostrar els resultats**:
   - Es mostren els noms i puntuacions dels jugadors que han quedat en primer, segon i tercer lloc.
   - Si hi ha menys de tres jugadors, es mostren missatges indicant que no hi ha segon o tercer lloc.

4. **Configuració del botó de sortida**:
   - Es defineix l'acció que s'executa quan l'usuari prem el botó "Sortir".

---

### Mètode `actionPerformed()` del botó `botoSortir`

S'executa quan l'usuari prem el botó "Sortir". S'encarrega de tancar el joc de manera ordenada, actualitzant les puntuacions dels jugadors i netejant les dades temporals.

### Accions principals:
1. **Actualització de les puntuacions dels jugadors**:
   - Es recorre la taula de resultats i s'actualitzen les puntuacions totals dels jugadors al fitxer `usuaris.dat`.

2. **Tancament dels fitxers**:
   - Es tanquen els fitxers d'accés directe d'usuaris, preguntes i configuració.

3. **Neteja de dades temporals**:
   - Es buida el fitxer de preguntes filtrades.

4. **Missatge de sortida**:
   - Es mostra un missatge de comiat ("Fins aviat!") i es tanca el programa.

5. **Gestió d'errors**:
   - Si es produeix un error durant el tancament, es mostra un missatge d'error.

---

### Mètode `main()`

És el punt d'entrada del programa. Crea una instància de la finestra `FinestraFinalMulti` i la mostra.

### Accions principals:
1. Es crea una instància de `FinestraFinalMulti` dins de l'Event Dispatch Thread (EDT) de Swing.
2. Si es produeix algun error durant la inicialització, es mostra un missatge d'error i es tanca l'aplicació.

---

### `FinestraFinalSingle`

# Explicació de la classe `FinestraFinalSingle`

La classe `FinestraFinalSingle` és una finestra que es mostra al final d'una partida de trivial en mode d'un sol jugador. Aquesta finestra mostra el resultat final del jugador, incloent la seva puntuació i un missatge personalitzat segons el seu rendiment. També permet tancar el joc de manera ordenada. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

La classe `FinestraFinalSingle` té els següents atributs:

1. **`panel1`**:
   - Tipus: `JPanel`.
   - Descripció: És el panell principal que conté tots els components de la finestra.

2. **`labelResultatsFinals`**:
   - Tipus: `JLabel`.
   - Descripció: És l'etiqueta que mostra un missatge personalitzat segons el resultat del jugador.

3. **`labelImatgeFinal`**:
   - Tipus: `JLabel`.
   - Descripció: És l'etiqueta que mostra una imatge animada (GIF) segons el resultat del jugador.

4. **`botoSortir`**:
   - Tipus: `JButton`.
   - Descripció: És el botó que permet tancar el joc.

5. **`labelResultatsPunts`**:
   - Tipus: `JLabel`.
   - Descripció: És l'etiqueta que mostra la puntuació final del jugador.

---

### Constructor `FinestraFinalSingle()`

Inicialitza la finestra de resultats finals per a un sol jugador. S'encarrega de configurar les propietats de la finestra, mostrar el resultat del jugador i gestionar l'acció de tancament del joc.

### Accions principals:
1. **Configuració de la finestra**:
   - Títol: "Resultat de la Partida".
   - Contingut: `panel1` (el panell principal).
   - Operació de tancament: `DO_NOTHING_ON_CLOSE` (es gestiona manualment el tancament).
   - La finestra es mostra al centre de la pantalla.

2. **Mostrar el resultat del jugador**:
   - Es comprova la puntuació final del jugador i es mostra un missatge i una imatge animada segons el seu rendiment:
      - **Puntuació 0 o negativa**: Missatge de consolació i imatge de "perdedor".
      - **Puntuació igual o superior a la meitat de la puntuació necessària per guanyar, però inferior a la puntuació guanyadora**: Missatge d'encoratjament i imatge de "acceptable".
      - **Puntuació igual o superior a la puntuació necessària per guanyar**: Missatge de felicitació i imatge de "guanyador".

3. **Configuració del botó de sortida**:
   - Es defineix l'acció que s'executa quan l'usuari prem el botó "Sortir".

---

## Mètode `actionPerformed()` del botó `botoSortir`

S'executa quan l'usuari prem el botó "Sortir". S'encarrega de tancar el joc de manera ordenada, actualitzant la puntuació del jugador i netejant les dades temporals.

### Accions principals:
1. **Actualització de la puntuació del jugador**:
   - Es busca l'usuari al fitxer `usuaris.dat` i s'actualitza la seva puntuació total amb la puntuació obtinguda en la partida.

2. **Tancament dels fitxers**:
   - Es tanquen els fitxers d'accés directe d'usuaris, preguntes i configuració.

3. **Neteja de dades temporals**:
   - Es buida el fitxer de preguntes filtrades.

4. **Missatge de sortida**:
   - Es mostra un missatge de comiat ("Fins aviat!") i es tanca el programa.

5. **Gestió d'errors**:
   - Si es produeix un error durant el tancament, es mostra un missatge d'error.

---

### Mètode `main()`

És el punt d'entrada del programa. Crea una instància de la finestra `FinestraFinalSingle` i la mostra.

### Accions principals:
1. Es crea una instància de `FinestraFinalSingle` dins de l'Event Dispatch Thread (EDT) de Swing.
2. Si es produeix algun error durant la inicialització, es mostra un missatge d'error i es tanca l'aplicació.

---

### Classes Secundàries (Models)

### `BancPreguntes`

### Explicació dels mètodes de la classe `BancPreguntes`

La classe `BancPreguntes` s'encarrega de gestionar les preguntes del joc de trivial. Aquesta classe permet importar preguntes des d'un fitxer JSON, filtrar-les per categoria, guardar-les en un fitxer binari i carregar-les per a ser utilitzades durant el joc. A continuació, s'explica el funcionament dels mètodes principals:

---

### Mètode `guardarBinari(List<Pregunta> preguntesBin)`

Guarda una llista de preguntes en un fitxer binari utilitzant la classe `DirectAccessFile`.

### Accions principals:
1. **Escriptura de preguntes**:
   - Recorre la llista de preguntes i les escriu en el fitxer binari `preguntes.dat`.
   - Si l'escriptura és exitosa, es mostra un missatge de confirmació.

2. **Gestió d'errors**:
   - Si es produeix un error durant l'escriptura, es llança una excepció amb un missatge d'error.

---

### Mètode `carregarJsonFiltrades()`

Carrega les preguntes des d'un fitxer JSON (`preguntesFiltrades.json`) i les barreja per a ser utilitzades durant el joc.

### Accions principals:
1. **Lectura del fitxer JSON**:
   - Es llegeix el fitxer JSON i es deserialitzen les preguntes en una llista d'objectes `Pregunta`.

2. **Barrejar preguntes**:
   - Es barreja la llista de preguntes per a garantir que es mostrin en un ordre aleatori durant el joc.

3. **Gestió d'errors**:
   - Si es produeix un error durant la lectura del fitxer, es llança una excepció.

---

### Mètode `importarJsonToDat()`

Importa les preguntes des d'un fitxer JSON (`preguntes.json`) i les guarda en un fitxer binari (`preguntes.dat`).

### Accions principals:
1. **Comprovació de l'existència del fitxer binari**:
   - Si el fitxer binari ja existeix i conté dades, no es realitza cap acció.

2. **Importació de preguntes**:
   - Es llegeix el fitxer JSON i es deserialitzen les preguntes en una llista d'objectes `Pregunta`.
   - Es crida al mètode `guardarBinari()` per a guardar les preguntes en el fitxer binari.

3. **Gestió d'errors**:
   - Si es produeix un error durant la lectura o l'escriptura, es llança una excepció.

---

### Mètode `exportarDatToJSON(String categoria)`

Exporta les preguntes d'una categoria específica des del fitxer binari (`preguntes.dat`) a un fitxer JSON (`preguntesFiltrades.json`).

### Accions principals:
1. **Filtrar preguntes per categoria**:
   - Es recorre el fitxer binari i es filtren les preguntes que pertanyen a la categoria especificada.

2. **Escriptura del fitxer JSON**:
   - Es guarda la llista de preguntes filtrades en un fitxer JSON.

3. **Gestió d'errors**:
   - Si es produeix un error durant la lectura o l'escriptura, es llança una excepció.

---

### Mètode `buidarPreguntesFiltrades()`

Buida el fitxer JSON de preguntes filtrades (`preguntesFiltrades.json`) i neteja la llista de preguntes filtrades.

### Accions principals:
1. **Eliminació del fitxer JSON**:
   - Es comprova si el fitxer JSON existeix i, en cas afirmatiu, s'elimina.

2. **Neteja de la llista de preguntes filtrades**:
   - Es buida la llista `preguntesFiltrades`.

3. **Gestió d'errors**:
   - Si es produeix un error durant l'eliminació del fitxer, es mostra un missatge d'error.

---

### `Pregunta`

# Explicació de la classe `Pregunta`

La classe `Pregunta` representa una pregunta del joc de trivial. Aquesta classe conté l'enunciat de la pregunta, les opcions de resposta, la resposta correcta i la categoria a la qual pertany la pregunta. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

Té els següents atributs:

1. **`enunciat`**:
   - Tipus: `String`.
   - Descripció: Conté l'enunciat o text de la pregunta.

2. **`opcions`**:
   - Tipus: `List<String>`.
   - Descripció: Conté una llista de les opcions de resposta per a la pregunta.

3. **`respostaCorrecta`**:
   - Tipus: `String`.
   - Descripció: Conté la resposta correcta de la pregunta.

4. **`categoria`**:
   - Tipus: `String`.
   - Descripció: Indica la categoria a la qual pertany la pregunta (per exemple, "Història", "Ciència", etc.).

---

### Constructors

Té dos constructors:

1. **Constructor per defecte**:
   - Crea una instància de `Pregunta` sense inicialitzar cap atribut.
   - Útil per a crear objectes buits que es poden omplir posteriorment amb els mètodes `set`.

2. **Constructor amb paràmetres**:
   - Paràmetres:
      - `enunciat`: L'enunciat de la pregunta.
      - `opcions`: Les opcions de resposta.
      - `respostaCorrecta`: La resposta correcta.
      - `categoria`: La categoria de la pregunta.
   - Descripció: Crea una instància de `Pregunta` inicialitzant tots els atributs amb els valors passats com a paràmetres.

---

### Mètodes Getters i Setters

Disposa de mètodes `getters` i `setters` per a accedir i modificar els atributs de la classe:

1. **`getEnunciat()`**:
   - Retorna l'enunciat de la pregunta.

2. **`setEnunciat(String enunciat)`**:
   - Estableix l'enunciat de la pregunta.

3. **`getOpcions()`**:
   - Retorna la llista d'opcions de resposta.

4. **`setOpcions(List<String> opcions)`**:
   - Estableix la llista d'opcions de resposta.

5. **`getRespostaCorrecta()`**:
   - Retorna la resposta correcta de la pregunta.

6. **`setRespostaCorrecta(String respostaCorrecta)`**:
   - Estableix la resposta correcta de la pregunta.

7. **`getCategoria()`**:
   - Retorna la categoria de la pregunta.

8. **`setCategoria(String categoria)`**:
   - Estableix la categoria de la pregunta.

---

### Implementació de `Serializable`

La classe `Pregunta` implementa la interfície `Serializable`, la qual cosa permet serialitzar objectes d'aquesta classe. Això és útil per a guardar i carregar preguntes en fitxers binaris.

---

### `Usuari`

# Explicació de la classe `Usuari`

La classe `Usuari` representa un usuari del joc de trivial. Aquesta classe conté el nom de l'usuari i la seva puntuació total. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

La classe `Usuari` té els següents atributs:

1. **`nom`**:
   - Tipus: `String`.
   - Descripció: Conté el nom de l'usuari.

2. **`puntuacioTotal`**:
   - Tipus: `int`.
   - Descripció: Conté la puntuació total acumulada per l'usuari durant la partida.

---

### Constructor

Té un constructor amb paràmetres per a inicialitzar els atributs de la classe:

1. **Constructor amb paràmetres**:
   - Paràmetres:
      - `nom`: El nom de l'usuari.
      - `puntuacioTotal`: La puntuació total de l'usuari.
   - Descripció: Crea una instància de `Usuari` inicialitzant tots els atributs amb els valors passats com a paràmetres.

---

### Mètodes Getters i Setters

Disposa de mètodes `getters` i `setters` per a accedir i modificar els atributs de la classe:

1. **`getNom()`**:
   - Retorna el nom de l'usuari.

2. **`setNom(String nom)`**:
   - Estableix el nom de l'usuari.

3. **`getPuntuacioTotal()`**:
   - Retorna la puntuació total de l'usuari.

4. **`setPuntuacioTotal(int puntuacioTotal)`**:
   - Estableix la puntuació total de l'usuari.

---

### Implementació de `Serializable`

La classe `Usuari` també la implementa la interfície `Serializable` com la classe `Pregunta`, la qual cosa permet serialitzar objectes d'aquesta classe. Això és útil per a guardar i carregar usuaris en fitxers binaris, com ara per a mantenir un registre dels jugadors i les seves puntuacions.

---

### `Configuracio`

# Explicació de la classe `Configuracio`

La classe `Configuracio` representa la configuració de la partida del joc de trivial. Aquesta classe conté els paràmetres que defineixen com es desenvolupa el joc, com ara la puntuació necessària per guanyar, el temps màxim per respondre, les penalitzacions, etc. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

Té els següents atributs estàtics:

1. **`puntuacioGuanyar`**:
   - Tipus: `int`.
   - Descripció: Indica la puntuació necessària per guanyar la partida.

2. **`puntuacioPregunta`**:
   - Tipus: `int`.
   - Descripció: Indica els punts que s'obtenen per cada resposta correcta.

3. **`tempsMaximResposta`**:
   - Tipus: `int`.
   - Descripció: Indica el temps màxim (en segons) per respondre una pregunta.

4. **`penalitzacioTemps`**:
   - Tipus: `int`.
   - Descripció: Indica la penalització (en punts) que s'aplica si no es respon a temps.

5. **`numPreguntes`**:
   - Tipus: `int`.
   - Descripció: Indica el nombre total de preguntes que es faran durant la partida.

---

### Constructors

Té dos constructors:

1. **Constructor per defecte**:
   - Crea una instància de `Configuracio` sense inicialitzar cap atribut.
   - Útil per a crear objectes buits que es poden omplir posteriorment amb els mètodes `set`.

2. **Constructor amb paràmetres**:
   - Paràmetres:
      - `puntuacioGuanyar`: La puntuació necessària per guanyar.
      - `puntuacioPregunta`: Els punts per cada resposta correcta.
      - `tempsMaximResposta`: El temps màxim per respondre una pregunta.
      - `penalitzacioTemps`: La penalització per no respondre a temps.
      - `numPreguntes`: El nombre total de preguntes per partida.
   - Descripció: Crea una instància de `Configuracio` inicialitzant tots els atributs amb els valors passats com a paràmetres.

---

### Mètodes Getters i Setters

Disposa de mètodes `getters` i `setters` per a accedir i modificar els atributs de la classe:

1. **`getPuntuacioGuanyar()`**:
   - Retorna la puntuació necessària per guanyar.

2. **`setPuntuacioGuanyar(int puntuacioGuanyar)`**:
   - Estableix la puntuació necessària per guanyar.

3. **`getPuntuacioPregunta()`**:
   - Retorna els punts per cada resposta correcta.

4. **`setPuntuacioPregunta(int puntuacioPregunta)`**:
   - Estableix els punts per cada resposta correcta.

5. **`getTempsMaximResposta()`**:
   - Retorna el temps màxim per respondre una pregunta.

6. **`setTempsMaximResposta(int tempsMaximResposta)`**:
   - Estableix el temps màxim per respondre una pregunta.

7. **`getPenalitzacioTemps()`**:
   - Retorna la penalització per no respondre a temps.

8. **`setPenalitzacioTemps(int penalitzacioTemps)`**:
   - Estableix la penalització per no respondre a temps.

9. **`getNumPreguntes()`**:
   - Retorna el nombre total de preguntes per partida.

10. **`setNumPreguntes(int numPreguntes)`**:
   - Estableix el nombre total de preguntes per partida.

---

### Implementació de `Serializable`

Implementa la interfície `Serializable`, la qual cosa permet serialitzar objectes d'aquesta classe. Això és útil per a guardar i carregar la configuració de la partida en fitxers binaris, permetent mantenir un registre de les configuracions utilitzades.

---

### `GestorConfiguracio`

# Explicació de la classe `GestorConfiguracio`

La classe `GestorConfiguracio` s'encarrega de gestionar la configuració de la partida del joc de trivial. Aquesta classe permet guardar la configuració en un fitxer binari per a poder recuperar-la en futures sessions del joc. A continuació, s'explica el funcionament de la classe:

---

### Atributs de la classe

Té un atribut estàtic:

1. **`dafConfig`**:
   - Tipus: `DirectAccessFile<Configuracio>`.
   - Descripció: És un fitxer d'accés directe que permet guardar i llegir objectes de tipus `Configuracio` en un fitxer binari.

---

### Mètodes

Té un mètode principal:

### Mètode `guardarConfiguracio(Configuracio configuracio)`

Aquest mètode guarda la configuració de la partida en un fitxer binari (`configuracio.dat`).

#### Accions principals:
1. **Inicialització del fitxer d'accés directe**:
   - Es crea una instància de `DirectAccessFile` per a gestionar el fitxer `configuracio.dat`.

2. **Escriptura de la configuració**:
   - Es guarda l'objecte `Configuracio` passat com a paràmetre en el fitxer binari.

3. **Confirmació de l'operació**:
   - Si l'escriptura és exitosa, es mostra un missatge de confirmació.

4. **Gestió d'errors**:
   - Si es produeix un error durant l'escriptura, es captura l'excepció i es mostra un missatge d'error.

---

### Implementació de `DirectAccessFile`

La classe utilitza la classe `DirectAccessFile` per a gestionar el fitxer binari. Aquesta classe permet escriure i llegir objectes de manera eficient en un fitxer, cosa que és útil per a guardar i recuperar la configuració de la partida.

---

### Exemple d'execució

### 1. Finestra inicial

### 2. Finestra Jugadors

### 3. Finestra Joc

### 4. Finestra Ranking

### 5. Finestra Final Multi

### 6. Finestra Final Single
