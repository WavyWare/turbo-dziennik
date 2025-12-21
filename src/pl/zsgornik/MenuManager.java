package pl.zsgornik;

import java.util.Stack;
import java.util.Scanner;

public class MenuManager {
    private Stack<Screen> screenStack;
    private Scanner scanner;
    private DziennikLekcyjny dziennik;
    private boolean running;

    public MenuManager(DziennikLekcyjny dziennik) {
        this.dziennik = dziennik;
        this.screenStack = new Stack<>();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void pushScreen(Screen screen) {
        screenStack.push(screen);
    }

    public void popScreen() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    public void replaceScreen(Screen screen) {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
        screenStack.push(screen);
    }

    public void start() {
        while (running && !screenStack.isEmpty()) {
            Screen currentScreen = screenStack.peek();
            currentScreen.display();
            
            if (!screenStack.isEmpty() && screenStack.peek() == currentScreen) {
                String input = scanner.nextLine().trim();
                currentScreen.handleInput(input);
            }
        }
        scanner.close();
    }

    public void stop() {
        running = false;
    }

    public DziennikLekcyjny getDziennik() {
        return dziennik;
    }

    public Scanner getScanner() {
        return scanner;
    }
}

