package Page.Element;

import Page.GroupPage;
import Page.UserGroupsPage.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.ArrayList;

public class CreateGroupBar {

    private static final String PAGE = "//*[@data-l='t,PAGE']";
    private static final String INTEREST = "//*[@data-l='t,INTEREST']";
    private static final String HAPPENING = "//*[@data-l='t,HAPPENING']";

    private static final String FIELD_NAME = "//*[@id='field_name']";
    private static final String THEMES_SCROLL = "//*[@autocomplete='new-password']";
    private static final String THEME_SPORT = "//*[@data-id='Category_SWIMMING_POOL']";
    private static final String ACCESS_OPEN = "//*[label='Открытый']";
    private static final String ACCESS_CLOSE = "//*[label='Закрытый']";
    private static final String ACCESS_SECRET = "//*[label='Секретный']";
    private static final String CREATE = "//*[@class='button-pro']";

    private Page page;
    private ArrayList<Locator> groupTypes;
    private ArrayList<Locator> accessTypes;
    private Locator fieldName;
    private Locator filedTheme;
    private Locator create;

    public CreateGroupBar(Page page)
    {
        this.page = page;
        groupTypes = new ArrayList<>();
        groupTypes.add(page.locator(PAGE));
        groupTypes.add(page.locator(INTEREST));
        groupTypes.add(page.locator(HAPPENING));
    }

    public CreateGroupBar chooseGroupType(GroupTypes type)
    {
        getType(type).click();

        fieldName = page.locator(FIELD_NAME);
        filedTheme = page.locator(THEMES_SCROLL);
        accessTypes = new ArrayList<>();
        accessTypes.add(page.locator(ACCESS_OPEN));
        accessTypes.add(page.locator(ACCESS_CLOSE));
        accessTypes.add(page.locator(ACCESS_SECRET));
        create = page.locator(CREATE);
        return this;
    }

    public CreateGroupBar setGroupName(String name)
    {
        page.locator(FIELD_NAME).fill(name);
        return this;
    }

    public CreateGroupBar setGroupTheme(String name)
    {
        page.locator(THEMES_SCROLL).click();
        page.locator(THEME_SPORT).hover();
        page.locator(THEME_SPORT).click();
        return this;
    }

    public GroupPage create()
    {
        create.click();
        return new GroupPage(page);
    }

    public CreateGroupBar chooseAccessType(AccessTypes type)
    {
        switch (type){
            case OPEN:
                accessTypes.get(0).click();
                break;
            case CLOSE:
                accessTypes.get(1).click();
                break;
            case SECRET:
                accessTypes.get(2).click();
                break;
            default:
                accessTypes.get(0).click();
                break;
        }
        return this;
    }

    private Locator getType(GroupTypes type)
    {
        switch (type) {
            case PAGE:
                return groupTypes.get(0);
            case INTEREST:
                return groupTypes.get(1);
            case HAPPENING:
                return groupTypes.get(2);
            default:
                return groupTypes.get(0);
        }
    }





}
