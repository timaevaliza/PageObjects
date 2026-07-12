package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    DashboardPage dashboardPage;

    String firstCardId = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    String secondCardId = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

    String secondCardNumber = "5559 0000 0000 0002";

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin("vasya", "qwerty123");

        dashboardPage = verificationPage.validVerify("12345");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {

        int startBalanceFirst = dashboardPage.getCardBalance(firstCardId);
        int startBalanceSecond = dashboardPage.getCardBalance(secondCardId);

        int transferAmount = 200;

        TransferPage transferPage = dashboardPage.selectCardToTransfer(firstCardId);

        // 3. Выполняем перевод и возвращаем обновленный Dashboard
        dashboardPage = transferPage.makeTransfer(String.valueOf(transferAmount), secondCardNumber);

        // 4. Фиксируем балансы после проведения операции
        int endBalanceFirst = dashboardPage.getCardBalance(firstCardId);
        int endBalanceSecond = dashboardPage.getCardBalance(secondCardId);

        // 5. Проверяем математическую точность изменений в JUnit 5
        assertEquals(startBalanceFirst + transferAmount, endBalanceFirst);
        assertEquals(startBalanceSecond - transferAmount, endBalanceSecond);
    }
}
