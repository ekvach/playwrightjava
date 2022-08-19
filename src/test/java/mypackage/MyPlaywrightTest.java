package mypackage;


import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyPlaywrightTest {
    private static Playwright playwright;
    private static Browser browser;

    private BrowserContext context;
    private Page page;

    @BeforeAll
    public static void launchBrowser() {
        playwright = Playwright.create();

        browser = playwright.chromium()
                            .launch();
    }

    @AfterAll
    public static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext();
        context.browser();
        page = context.newPage();
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }

    @Test
    public void verifyGoogleSearchTest() {
        page.navigate("https://google.com/");

        page.locator("[name=q]")
            .fill("Cat");

        page.keyboard()
            .press("Enter");

        assertThat(page.locator("id=result-stats")
                       .innerText())
                .as("asdasd")
                .matches(".*(:?)\\h\\d([\\h,]\\d{3}){3}\\h(results\\h)?\\(\\d\\.\\d*\\h.*\\)\\h");
    }
}
