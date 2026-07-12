package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }

    public int getCardBalance(String id) {
        SelenideElement card = cards.findBy(attribute("data-test-id", id));
        String text = card.text();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(String id) {
        cards.findBy(attribute("data-test-id", id)).$("button[data-test-id='action-deposit']").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);

        value = value.replaceAll("[\\s\\u00A0]", "");

        return Integer.parseInt(value);
    }
}