package pl.zsgornik.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import pl.zsgornik.service.DziennikLekcyjny;

public class MenuManager {
    private final Stack<Screen> screenStack;
    private final DziennikLekcyjny dziennik;
    private final BufferedReader reader;
    private boolean running;

    public MenuManager(DziennikLekcyjny dziennik) {
        this.dziennik = dziennik;
        this.screenStack = new Stack<>();
        this.running = true;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
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

    public void start() throws IOException {
        while (running && !screenStack.isEmpty()) {
            Screen currentScreen = screenStack.peek();
            currentScreen.display();
            
            if (!screenStack.isEmpty() && screenStack.peek() == currentScreen) {
                String input = getConsole().readLine().trim();
                currentScreen.handleInput(input);
            }
        }
    }

    public void stop() {
        running = false;
    }

    public DziennikLekcyjny getDziennik() {
        return dziennik;
    }

    public BufferedReader getConsole() {
        return reader;
    }

}
