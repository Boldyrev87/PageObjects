package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var user = DataHelper.getAuthInfo();
        var code = DataHelper.getVerificationCode();
        var verificationPage = loginPage.validLogin(user);
        dashboardPage = verificationPage.validVerification(code);
    }

    @Test
    @DisplayName("Transfer from second to first card")
    void shouldTransferMoneyFromSecondToFirstCard() {
        int sum = 9999;
        int begBalance1 = dashboardPage.getCardBalance(0);
        int begBalance2 = dashboardPage.getCardBalance(1);
        var refillCardBalancePage = dashboardPage.refillCard(0);
        var cardNum = DataHelper.getSecondCard();
        var dashboardPage2 = refillCardBalancePage.transferValue(cardNum, sum);
        int endBalance1 = dashboardPage2.getCardBalance(0);
        int endBalance2 = dashboardPage2.getCardBalance(1);
        assertEquals(begBalance1 + sum, endBalance1);
        assertEquals(begBalance2 - sum, endBalance2);
    }

    @Test
    @DisplayName("Transfer from first to second card")
    void shouldTransferMoneyFromFirstToSecondCard() {
        int sum = 9999;
        int begBalance1 = dashboardPage.getCardBalance(0);
        int begBalance2 = dashboardPage.getCardBalance(1);
        var refillCardBalancePage = dashboardPage.refillCard(1);
        var cardNum = DataHelper.getFirstCard();
        var dashboardPage2 = refillCardBalancePage.transferValue(cardNum, sum);
        int endBalance1 = dashboardPage2.getCardBalance(0);
        int endBalance2 = dashboardPage2.getCardBalance(1);
        assertEquals(begBalance1 - sum, endBalance1);
        assertEquals(begBalance2 + sum, endBalance2);
    }

    @Test
    @DisplayName("No transfer from second to first card")
    void shouldNotTransferMoneyFromSecondToFirstCard() {
        int begBalance1 = dashboardPage.getCardBalance(1);
        int sum = begBalance1 + 9999;
        var refillCardBalancePage = dashboardPage.refillCard(0);
        var cardNum = DataHelper.getSecondCard();
        refillCardBalancePage.errorTransfer(cardNum, sum);
    }
}











