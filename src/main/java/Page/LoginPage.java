package Page;

import ValueObject.UserBot;
import com.microsoft.playwright.Page;

public class LoginPage {
    private static final String TITLE = "Социальная сеть Одноклассники. Общение с друзьями в ОК. Ваше место встречи с одноклассниками";
    private static final String ENTER_LOGIN = "Введите логин";
    private static final String ENTER_PASSWORD = "Введите пароль";
    private static final String WRONG_ENTER = "Неправильно указан логин и/или пароль";
    private static final String LOGIN = "//*[@class='it h-mod']";
    private static final String PASSWORD = "//*[@class='it ']";
    private static final String SUBMIT = "//*[@value='Войти в Одноклассники']";
    private static final String LOGIN_ERROR = "//*[@class='input-e login_error']";
    private static final String PASSWORD_ERROR = "//*[@class='input-e login_error']";
    private static final String SUMBIT_ERROR = "//*[@class='input-e login_error']";
    private static final String ROOT = "//*[@class='h-mod anon-main-design21_root-wrapper']";

    private Page page;

    public LoginPage(Page page)
    {
        this.page = page;
    }

    public LoginPage enterLogin(String login)
    {
        page.locator(LOGIN).fill(login);
        return this;
    }

    public LoginPage enterPassword(String password)
    {
        page.locator(PASSWORD).fill(password);
        return this;
    }

    public HomePage clickSubmit()
    {
        page.locator(SUBMIT).click();
        return new HomePage(page);
    }

    public HomePage logIn(UserBot bot)
    {
        enterLogin(bot.getLogin());
        enterPassword(bot.getPassword());
        return clickSubmit();
    }

    public Page getPage()
    {
        return this.page;
    }
}
