package pl.zsgornik.ui;

import pl.zsgornik.service.DziennikLekcyjny;

public abstract class Screen {
    protected static MenuManager menuManager;
    protected static DziennikLekcyjny dziennik;

    public Screen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        Screen.menuManager = menuManager;
        Screen.dziennik = dziennik;
    }

    public abstract void display();
    public abstract void handleInput(String input);
}
