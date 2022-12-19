package Page;

import com.microsoft.playwright.Page;

public class GroupPage {
    private Page page;

    public GroupPage(Page page)
    {
        this.page = page;
    }

    public Page getPage()
    {
        return this.page;
    }

}
