package sk.michacu.zmenaren;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyConversionTest {
    @Test
    public void testMonetaryConversionsIMF() {
        final MonetaryAmount amt = Money.of(2000, "EUR");
        CurrencyConversion conv= MonetaryConversions.getConversion("CHF", "IMF");
        MonetaryAmount converted = amt.with(conv);
        assertNotNull(converted);
    }
}
