package test;

import data.DataHelper;
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

    // Получаем информацию о картах через DataHelper
    DataHelper.CardInfo firstCard = DataHelper.getFirstCardInfo();
    DataHelper.CardInfo secondCard = DataHelper.getSecondCardInfo();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        // Используем методы loginPage и verificationPage, передавая данные из хелпера
        VerificationPage verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());

        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int startBalanceFirst = dashboardPage.getCardBalance(firstCard.getId());
        int startBalanceSecond = dashboardPage.getCardBalance(secondCard.getId());

        int transferAmount = 200;

        TransferPage transferPage = dashboardPage.selectCardToTransfer(firstCard.getId());
        dashboardPage = transferPage.makeTransfer(String.valueOf(transferAmount), secondCard.getCardNumber());

        int endBalanceFirst = dashboardPage.getCardBalance(firstCard.getId());
        int endBalanceSecond = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(startBalanceFirst + transferAmount, endBalanceFirst);
        assertEquals(startBalanceSecond - transferAmount, endBalanceSecond);
    }
}