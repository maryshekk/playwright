package Test;
import Page.HomePage;
import Page.LoginPage;
import ValueObject.UserBot;
import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.assertions.*;

public class TestLogin extends BaseTest {
    private static final String LOGIN1 = "technoPol8";
    private static final String PASSWORD1 = "technoPolis2022";
    private static final String USERBAR = "//*[@data-l='t,userPage']";
    private static final String SUMBIT_ERROR = "//*[@class='input-e login_error']";

    private static UserBot user1 = new UserBot(LOGIN1, PASSWORD1);
    private static UserBot user2 = new UserBot(LOGIN1, "wrong");
    private static UserBot user3 = new UserBot("wrong", PASSWORD1);
    private static UserBot user4 = new UserBot("wrong", "wrong");
    private static UserBot user5 = new UserBot("", "");
    private static final String URL = "https://ok.ru/";
    public BrowserContext context;

    @Test
    @DisplayName("all log in checks")
    public void succesfullLoginTest() {
        LoginPage loginPage = new LoginPage(page);

        PlaywrightAssertions.assertThat(loginPage.logIn(user2).getPage().locator(SUMBIT_ERROR)).isVisible();
        PlaywrightAssertions.assertThat(loginPage.logIn(user3).getPage().locator(SUMBIT_ERROR)).isVisible();

        loginPage = new LoginPage(browser.newContext().newPage().navigate(URL).frame().page());

        PlaywrightAssertions.assertThat(loginPage.logIn(user4).getPage().locator(SUMBIT_ERROR)).isVisible();
        PlaywrightAssertions.assertThat(loginPage.logIn(user5).getPage().locator(SUMBIT_ERROR)).isVisible();

        HomePage homePage = loginPage.logIn(user1);
        
        PlaywrightAssertions.assertThat(homePage.getPage().locator(USERBAR)).isVisible();
    }
}
