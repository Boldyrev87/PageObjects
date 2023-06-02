import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class TransferPage {
    private SelenideElement amountTransfer = $("[data-test-id='amount'] .input__control");
    private SelenideElement numberCard = $("[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");
    String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    public TransferPage() {

    }

    public DashboardPage transferValue(DataHelper.CardInfo info, int value) {
        amountTransfer.sendKeys(deleteString);
        amountTransfer.setValue(String.valueOf(value));
        numberCard.sendKeys(deleteString);
        numberCard.setValue(info.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void errorTransfer(DataHelper.CardInfo info, int value) {
        transferValue(info, value);
        errorMessage.shouldHave(exactText("На карте № " + info.getNumber() + " недостаточно средств")).shouldBe(visible);
    }
}
