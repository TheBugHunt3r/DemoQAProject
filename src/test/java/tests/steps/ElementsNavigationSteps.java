package tests.steps;

import ui.manager.PageManager;
import ui.pages.ElementsPage;

public class ElementsNavigationSteps {

    private final PageManager pageManager;

    public ElementsNavigationSteps (PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public ElementsPage goToElements() {
        return pageManager.getMainPage()
                .open()
                .isPageOpened()
                .moveToElements();
    }
}
