# Turbo dziennik
Aplikacja do zarządzania dziennikiem szkolnym dla nauczycieli

## Wymagania
- Java 21 lub nowsza
- System operacyjny: Windows, Linux, macos

## Instalacja
1. Pobierz repo z github
```cmd
git clone https://github.com/WavyWare/turbo-dziennik.git 
```
2. Wejdź do folderu
```cmd
cd turbo-dziennik
```
3. Skompiluj i uruchom projekt

Dla wszystkich systemów (Zalecane):

Wejdź w intellij i uruchom plik Main.java w folderze `src/pl/zsgornik`

Dla Windows Powershell:
```cmd
javac -d out (Get-ChildItem -Recurse src/pl/zsgornik -Filter *.java).FullName
java out/Main
```
Dla Linux:
```bash
javac -d out $(find src/pl/zsgornik -name "*.java")
java out/Main
```

## Podstawowe funkcje
1. Tworzenie, usuwanie i modyfikacja:
   1. Klasami
   2. Lekcjami
   3. Obecnościami
   4. Ocenami
   5. Przedmiotami
   6. Uczniami
2. Raporty
   1. Raport nieobecności
   2. Raport średnich
   3. Raport zachowania

## Użytkowanie
1. Po uruchomieniu zaloguj się bądź zarejestruj.
   1. Przykładowy użytkownik, który jest w systemie to Jan Kowalski z loginem `jkowalski123` i hasłem `haslo123`
2. Jeżeli logowanie przeszło pomyślnie, wybierz dane, które chcesz zmieniać. Przyjazny interfejs ułatwia kontrole.

### Przykład korzystania
1. Nauczyciel Jan Kowalski loguje się, by sprawdzić obecność na lekcji i przeprowadzić pytanie na lekcji
2. Nauczyciel loguje się swoim loginem i hasłem
3. Następnie przechodzi do kategorii "1. Lekcje" by dodać swoją lekcję
4. Rejestruje on obecności opcją 3. Zarejestruj obecności i wybiera tę lekcję, którą dodał
5. Po kolei oznacza każdego ucznia i jego typ obecności
6. Wychodzi opcją 0. Powrót i przechodzi do kategorii 2. Oceny i potem do Dodaj ocenę
7. Nauczyciel wybiera opcje dowolnego ucznia i wstawia ocene i komentarz

## Ekrany

- Ekran startowy
  - Ekran, który daje opcje rejestracji lub zalogowania się
- Ekran Logowania
  - Ekran, który przyjmuje login i hasło a nastepnie porównuje z tymi zapisanymi i loguje do systemu
- Ekran Rejestracji
  - Ekran, który pozwala na utworzenie nowego konta nauczyciela i zalogowanie się nim
- Główny ekran
  - Centrum zarządzania, dostepny tylko po zalogowaniu. Z jego poziomu można wybierać inne ekrany.
- Ekran lekcji
  - Pozwala on:
    - Wypisać wszystkie lekcje w systemie
    - Dodać nową lekcję (wybór przedmiotu i klasy)
    - Zarejestrować obecności dla całej klasy na wybranej lekcji
    - Zmienić status obecności dla wybranego ucznia
- Ekran ocen
  - Pozwala on:
    - Wyświetlić listę wszystkich ocen w systemie
    - Dodać nową ocenę (dla ostatniej lekcji prowadzonej przez nauczyciela)
    - Usunąć ocenę
    - Zmienić wartość oceny
    - Zmienić komentarz do oceny
- Ekran obecności
  - Pozwala on:
    - Wyświetlić obecności dla wybranej lekcji
    - Zarejestrować obecność pojedynczego ucznia na lekcji
    - Automatycznie dodaje uwagę o nieusprawiedliwionych nieobecnościach (gdy uczeń ma 3 lub więcej)
- Ekran klas
  - Pozwala on:
    - Wyświetlić listę wszystkich klas z informacjami o wychowawcy i liczbie uczniów
    - Dodać nową klasę
    - Zmienić nazwę klasy
    - Ustawić przewodniczącego klasy
    - Ustawić wychowawcę klasy
- Ekran przedmiotów
  - Pozwala on:
    - Wyświetlić listę wszystkich przedmiotów z przypisanymi nauczycielami
    - Dodać nowy przedmiot (wybór typu przedmiotu)
    - Usunąć przedmiot
    - Zmienić typ przedmiotu
    - Dodać siebie do przedmiotu (przypisać się jako nauczyciel)
    - Usunąć siebie z przedmiotu
- Ekran uczniów
  - Pozwala on:
    - Wyświetlić listę wszystkich uczniów według klas
    - Dodać ucznia do klasy
    - Usunąć ucznia z klasy
    - Wyświetlić raport frekwencji ucznia
    - Wyświetlić raport średnich ocen ucznia (z podziałem na przedmioty)
    - Wyświetlić uwagi ucznia
    - Dodać uwagę dla ucznia (pozytywną lub negatywną)
    - Edytować opis uwagi
    - Wyświetlić raport zachowania (liczba pochwał i uwag)

## Bezpieczeństwo
Zostało użyte hash'owanie haseł, autoryzacja użytkowników i wymagania haseł

## Struktura projektu
Projekt zorganizowany jest w następujący sposób:
- `src/pl/zsgornik/` - główny pakiet aplikacji
- `src/pl/zsgornik/model/` - klasy modelowe (Klasa, Uczen, Nauczyciel, Lekcja, Ocena, Obecnosc, Przedmiot, Uwaga)
- `src/pl/zsgornik/enums/` - typy wyliczeniowe (StatusObecnosci, TypOceny, TypPrzedmiotu)
- `src/pl/zsgornik/service/` - warstwa serwisowa (DziennikLekcyjny)
- `src/pl/zsgornik/ui/` - interfejs użytkownika (ekrany aplikacji)
- `src/pl/zsgornik/util/` - klasy pomocnicze (SelectionHelper, Tuple, Util)

## Testowanie
Aplikacja została przetestowana pod kątem:
- Poprawności kompilacji
- Działania podstawowych funkcji
- Obsługi danych wejściowych użytkownika
- Zarządzania stanem aplikacji

## Troubleshooting
Problem: Błąd kompilacji "cannot find symbol `getFirst()`"
Rozwiązanie: Używaj Java 21 lub nowszej. Metoda `getFirst()` została dodana w Java 21.

Problem: Błąd "Could not find or load main class"
Rozwiązanie: Upewnij się, że kompilujesz i uruchamiasz z odpowiedniej ścieżki:
```bash
javac -d out $(find src/pl/zsgornik -name "*.java")
java -cp out pl.zsgornik.Main
```
## Autor
Jan Florek, najzdolniejszy uczeń klasy 3bT

## Aneks dot. sztucznej inteligencji
AI było użyte w celach pomocniczych podczas:
1. Sugestii wyglądu interfejsu użytkownika
2. Stworzenie tablic z imionami, nazwiskami i nazwami klas w `Main.java`
3. Uzyskania wskazówek dotyczących wdrożenia bardziej zaawansowanych technologii, których nauczyłem się samodzielnie, poza zakresem zajęć szkolnych. Dotyczyło to tylko:
   - `Stream` by skrócić i uprościć kod
   - `Stack` dla zarządzania ekranami
4. Zaproponowania struktury projektu, czyli który obiekt klasy powinna należeć, do którego obiektu i gdzie powinno być wszystko przechowywane (w moim przypadku `DziennikLekcyjny.java`).

Działanie całego kodu w projekcie rozumiem.

### Podpowiedzi IntelliJ
> Niektóre podpowiedzi od IntelliJ mogą być niesłusznie pomylone z działaniem AI, dlatego niektóre zmiany w moim projekcie, np. zmiana klasy na rekord i inne, mogły zależeć od IDE