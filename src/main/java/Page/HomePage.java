package Page;

import com.microsoft.playwright.Page;

public class HomePage {

    public static final String GROUPS = "//*[@data-l='t,userAltGroup']";

    private Page page;

    public HomePage(Page page)
    {
        this.page = page;
    }

    public UserGroupsPage goToGroups()
    {
        page.locator(GROUPS).click();
        return new UserGroupsPage(page);
    }

    public Page getPage()
    {
        return this.page;
    }
}
