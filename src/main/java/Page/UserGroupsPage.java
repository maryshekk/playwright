package Page;

import Page.Element.CreateGroupBar;
import com.microsoft.playwright.Page;

public class UserGroupsPage {

    private Page page;

    public enum GroupTypes
    {
        PAGE,
        INTEREST,
        HAPPENING
    }

    public enum AccessTypes
    {
        OPEN,
        CLOSE,
        SECRET
    }

    public UserGroupsPage(Page page)
    {
        this.page = page;
    }

    public Page getPage()
    {
        return this.page;
    }

    public GroupPage createGroup(GroupTypes type, AccessTypes access, String name, String theme)
    {
        CreateGroupBar bar = new CreateGroupBar(page);
        return bar.chooseGroupType(type)
                .setGroupName(name)
                .setGroupTheme(theme)
                .chooseAccessType(access)
                .create();
    }

    public GroupPage navigate(String url)
    {
        page.navigate(url);
        return new GroupPage(page);
    }


}
