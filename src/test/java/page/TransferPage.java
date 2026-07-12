package page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
    }

    public DashboardPage makeTransfer(String amount, String fromCardNumber) {
        amountInput.setValue(amount);
        fromInput.setValue(fromCardNumber);
        transferButton.click();
        return new DashboardPage();
    }
}
