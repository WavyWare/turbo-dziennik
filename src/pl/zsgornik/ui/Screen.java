package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;
import pl.zsgornik.util.SelectionHelper;

import java.io.IOException;

public abstract class Screen {
    protected final MenuManager menuManager;
    protected final DziennikLekcyjny dziennik;
    protected final SelectionHelper selectionHelper;

    public Screen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        this.menuManager = menuManager;
        this.dziennik = dziennik;
        this.selectionHelper = new SelectionHelper(dziennik, menuManager.getConsole());
    }

    public abstract void display() throws IOException;
    public abstract void handleInput(String input) throws IOException;
}
