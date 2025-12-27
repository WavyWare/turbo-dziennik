package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;
import pl.zsgornik.util.SelectionHelper;

public abstract class Screen {
    protected final MenuManager menuManager;
    protected final DziennikLekcyjny dziennik;
    protected final SelectionHelper selectionHelper;

    public Screen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        this.menuManager = menuManager;
        this.dziennik = dziennik;
        this.selectionHelper = new SelectionHelper(dziennik, menuManager.getScanner());
    }

    public abstract void display();
    public abstract void handleInput(String input);
}
