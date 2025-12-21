package pl.zsgornik;

public abstract class Screen {
    protected MenuManager menuManager;
    protected DziennikLekcyjny dziennik;

    public Screen(MenuManager menuManager, DziennikLekcyjny dziennik) {
        this.menuManager = menuManager;
        this.dziennik = dziennik;
    }

    public abstract void display();
    public abstract void handleInput(String input);
}

