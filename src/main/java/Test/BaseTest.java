package Test;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    static BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
    static Boolean headless = false;

    static BrowserContext browserContext;
    Page page;

    private static final String URL = "https://ok.ru/";

    @BeforeAll
    static void launchBrowser()
    {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(options.setHeadless(headless));
    }

    @AfterAll
    static void closeBrowser()
    {
        playwright.close();
    }

    @BeforeEach
    public void initEach()
    {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate(URL);
    }

    @AfterEach
    public void close()
    {
        page.close();
    }

    public String getURL()
    {
        return URL;
    }
}