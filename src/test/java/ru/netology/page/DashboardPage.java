package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private ElementsCollection cardButtons = $$("button[data-test-id=action-deposit]");
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public void DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int id) {
        val text = cards.get(id).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage refillCard(int id) {
        cardButtons.get(id).click();
        return new TransferPage();
    }
}
