package pl.zsgornik.ui;

import java.io.IOException;
import java.util.List;
import pl.zsgornik.model.Klasa;
import pl.zsgornik.model.Nauczyciel;
import pl.zsgornik.model.Uczen;
import pl.zsgornik.service.DziennikLekcyjny;

public class KlasyScreen extends Screen {
    public KlasyScreen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        super(menuManager, dziennik);
    }

    @Override
    public void display() {
        System.out.println("\n=== KLASY ===");
        System.out.println("\n1. Lista klas");
        System.out.println("2. Dodaj klasę");
        System.out.println("3. Zmień nazwę klasy");
        System.out.println("4. Ustaw przewodniczącego klasy");
        System.out.println("5. Ustaw wychowawce");
        System.out.println("0. Powrót");
        System.out.print("\nWybierz opcję: ");
    }

    @Override
    public void handleInput(String input) throws IOException {
        switch (input) {
            case "1":
                displayClasses();
                break;
            case "2":
                System.out.print("\nPodaj nazwę klasy: ");
                String className = menuManager.getConsole().readLine().trim();
                if (className.isEmpty()) {
                    System.out.println("Nazwa klasy nie może być pusta");
                    break;
                }
                if (dziennik.getGroups().stream().anyMatch(x -> x.getClassName().equals(className))) {
                    System.out.println("Klasa o tej nazwie już istnieje");
                    break;
                } 
                dziennik.pushGroup(new Klasa(className));
                System.out.println("Dodano klasę: " + className);
                break;
            case "3":
                Klasa changeNameGroup = selectionHelper.selectClass();
                if (changeNameGroup == null) {
                    break;
                }
                System.out.print("Podaj nową nazwę klasy: ");
                String newClassName = menuManager.getConsole().readLine().trim();
                if (newClassName.isEmpty()) {
                    System.out.println("Nowa nazwa klasy nie może być pusta");
                    break;
                }
                if (dziennik.getGroups().stream().anyMatch(x -> x.getClassName().equals(newClassName))) {
                    System.out.println("Klasa o tej nazwie już istnieje");
                    break;
                }
                changeNameGroup.setClassName(newClassName);
                System.out.println("\nPomyślnie zmieniono nazwę\n.");
                break;
            case "4":
                Klasa leaderGroup = selectionHelper.selectClass();
                if (leaderGroup == null) {
                    break;
                }
                Uczen newLeader = selectionHelper.chooseStudent(leaderGroup);
                if (newLeader != null) {
                    leaderGroup.setClassLeader(newLeader);
                }
                break;
            case "5":
                Klasa supervisorGroup = selectionHelper.selectClass();
                if (supervisorGroup == null) {
                    break;
                }
                Nauczyciel supervisor = selectionHelper.chooseTeacher();
                if (supervisor != null) {
                    supervisorGroup.setSupervisor(supervisor);
                    System.out.println("\nPomyślnie ustawiono wychowawce\n");
                }
                break;
            case "0":
                menuManager.popScreen();
                break;
            default:
                System.out.println("Nieprawidłowa opcja");
                break;
        }
    }

    private void displayClasses() {
        System.out.println("\nturbo dziennik - LISTA KLAS");
        List<Klasa> classes = dziennik.getGroups();
        if (classes.isEmpty()) {
            System.out.println("Brak klas w systemie");
        } else {
            for (int i = 0; i < classes.size(); i++) {
                Klasa klasa = classes.get(i);
                System.out.println((i + 1) + ". " + klasa);
            }
        }
    }

}

