package Test;

import Page.GroupPage;
import Page.HomePage;
import Page.LoginPage;
import Page.UserGroupsPage;
import ValueObject.UserBot;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static Page.UserGroupsPage.AccessTypes.*;
import static Page.UserGroupsPage.GroupTypes.*;

public class TestGroups extends BaseTest {

    private static final String LOGIN1 = "technoPol8";
    private static final String PASSWORD1 = "technoPolis2022";
    private static UserBot user1 = new UserBot(LOGIN1, PASSWORD1);

    private static final String LOGIN2 = "technoPol22";
    private static final String PASSWORD2 = "technoPolis2022";
    private static UserBot user2 = new UserBot(LOGIN2, PASSWORD2);

    private static final String URL = "https://ok.ru";
    private static String GROUP_URL = "https://ok.ru/group/70000001293924";

    private static final String USERS_GROUPS_EXPECTED_URL = "https://ok.ru/profile/583090489444/groups";
    private static final String CREATE_GROUPS_EXPECTED_URL = "https://ok.ru/groups/create";
    private static final String CREATEGROUPBAR = "//*[@class='modal-new_center']";
    private static final String GROUPTITLE = "//*[@class='group-name_h']";
    private static final String GROUPINFO = "//*[@class='group-name_info']";
    private static final String GROUPACCESS = "//*[@class='iblock-cloud_cnt nowrap']";

    private static final String GROUPNAME = "testgroup";
    private static final String GROUPTHEME = "Бассейн";

    private static final String TEASER_BLOCK = "//*[@id='hook_Block_ClosedGroupTeaserBlock']";
    private static final String CLOSED_MSG = "Это закрытая группа";
    private static final String CLOSED_STATUS = "Закрытая группа";

    public void saveContext()
    {
        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
    }

    public BrowserContext openContext()
    {
        return browser.newContext(
                new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json")));
    }

    @Override
    @BeforeEach
    public void initEach()
    {
        browserContext = openContext();
        page = browserContext.newPage();
        page.navigate(URL);
    }

    @Test
    @DisplayName("open groups list")
    public void testUsersGroupsOpening()
    {
        UserGroupsPage userGroupsPage = new HomePage(page).goToGroups();
        PlaywrightAssertions.assertThat(userGroupsPage.getPage()).hasURL(USERS_GROUPS_EXPECTED_URL);
    }

    @Test
    @DisplayName("create private group")
    public void testPrivateGroupCreating()
    {
        UserGroupsPage userGroupsPage = new HomePage(page).goToGroups();
        userGroupsPage.getPage().getByText("Создать группу").click();

        PlaywrightAssertions.assertThat(userGroupsPage.getPage()).hasURL(CREATE_GROUPS_EXPECTED_URL);
        PlaywrightAssertions.assertThat(userGroupsPage.getPage().locator(CREATEGROUPBAR)).isVisible();

        GroupPage groupPage = userGroupsPage.createGroup(PAGE, CLOSE, GROUPNAME, GROUPTHEME);
        PlaywrightAssertions.assertThat(groupPage.getPage().locator(GROUPTITLE)).isVisible();

        Assertions.assertEquals(GROUPNAME, groupPage.getPage().locator(GROUPTITLE).innerText());
        PlaywrightAssertions.assertThat(groupPage.getPage().locator(GROUPINFO)).isVisible();
        Assertions.assertEquals( GROUPTHEME, groupPage.getPage().locator(GROUPINFO).innerText());

        groupPage.getPage().locator(GROUPACCESS).hover();
        PlaywrightAssertions.assertThat(page.locator(GROUPACCESS)).containsText(CLOSED_STATUS);
        GROUP_URL = userGroupsPage.getPage().url();
    }

    @Test
    @DisplayName("open closed group")
    public void testPrivateGroupAccessibility()
    {
        page = browser.newContext().newPage();
        page.navigate(URL);
        GroupPage groupPage = new LoginPage(page).logIn(user2).goToGroups().navigate(GROUP_URL);

        PlaywrightAssertions.assertThat(groupPage.getPage().locator(GROUPTITLE)).isVisible();
        Assertions.assertEquals(GROUPNAME, groupPage.getPage().locator(GROUPTITLE).innerText());
        PlaywrightAssertions.assertThat(groupPage.getPage().locator(GROUPINFO)).isVisible();
        Assertions.assertEquals(GROUPTHEME, groupPage.getPage().locator(GROUPINFO).innerText());
        PlaywrightAssertions.assertThat(groupPage.getPage().locator(TEASER_BLOCK)).containsText(CLOSED_MSG);
    }
}
