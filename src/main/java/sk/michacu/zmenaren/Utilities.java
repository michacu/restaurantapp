package sk.michacu.zmenaren;

import sk.michacu.zmenaren.databaza.PgOperations;
import sk.michacu.zmenaren.model.MenaObject;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.*;
import java.util.regex.Pattern;

public class Utilities {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private final PgOperations pgOperations;

    public Utilities() {
        this.pgOperations = new PgOperations();
    }

    public MenaObject getMena(Long id) {
        return pgOperations.findById(id);
    }

    public List<MenaObject> getCurrencyList() {
        return pgOperations.findAll();
    }

    public void fillInitList() {
        List<MenaObject> menaObjectList = new ArrayList<>();
        Date date = new Date();
        menaObjectList.add(new MenaObject(1L, "EUR", "€", "mena platna v europskej unii", true, date, date));
        menaObjectList.add(new MenaObject(2L,"CZK", "Kč", "mena platna pre cesku republiku", true, date, date));
        menaObjectList.add(new MenaObject(3L, "PLN", "zł", "mena platna v polsku", true, date, date));
        menaObjectList.add(new MenaObject(4L, "RUB", "₽", "mena platna v rusku", true, date, date));
        menaObjectList.add(new MenaObject(5L, "HUF", "ft", "mena platna pre madarsko", true, date, date));
        menaObjectList.add(new MenaObject(6L, "GBP", "£", "mena platna vo velkej britanii", true, date, date));
        menaObjectList.add(new MenaObject(7L, "USD", "$", "mena platna v amerike", true, date, date));
        pgOperations.fillInitData(menaObjectList);
    }

    public void addMenaObject(String currnameValueFrom, String iconValueForm, String descriptionValueForm, boolean activeValueForm) {
        Date date = new Date();
        pgOperations.addMena(new MenaObject(1L, currnameValueFrom, iconValueForm, descriptionValueForm, activeValueForm, date, date));
    }

    public void removeMenaObject(String currnameValueFrom) {
        pgOperations.deleteMena(currnameValueFrom);
    }

    public void removeAllData() {
        pgOperations.deleteAll();
    }


    public boolean isNotNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        return !pattern.matcher(strNum).matches();
    }

    public MonetaryAmount getMonetaryAmount(String fromCurrency, String currencySelectorTo, String currencySelectorFrom) {
        MonetaryAmount resultNumber = Monetary.getDefaultAmountFactory().setCurrency(currencySelectorFrom)
                .setNumber(Long.parseLong(fromCurrency)).create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(currencySelectorTo);
        return resultNumber.with(conversion);
    }

    public void updateMena(String currName,String icon, String description, boolean activeValue) {
        pgOperations.updateMenaInfo(currName,icon,activeValue,description);
    }

    public List<Currency> getAllCurrencies() {
        List<Currency> toret = new ArrayList<>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {
            try {
                Currency currency = Currency.getInstance( loc );

                if ( currency != null ) {
                    toret.add( currency );
                }
            } catch(Exception exc)
            {
                // Locale not found
            }
        }

        return toret;
    }

    public StringBuilder fillErrorList(String menaSelectorForm, String iconValueForm, String descriptionValueForm, StringBuilder error, List<MenaObject> actualList, String menaRadio) {
        if (menaSelectorForm.isEmpty()) {
            error.append("\n").append("Mena Selector cant be empty please choose value");
        }
        actualList.forEach(menaObject -> {
            List<MenaObject> filteredList = new ArrayList<>();
            if (menaObject.getCurrName().equals(menaSelectorForm)) {
                filteredList.add(menaObject);
            }
            if (menaRadio.equals("add") && !filteredList.isEmpty()) {
                error.append("Mena allready exist in zoznam please choose diferent value");
            }
            if (menaRadio.equals("remove") && filteredList.isEmpty()) {
                error.append("Mena does not exist cant delete");
            }
        });
        if (iconValueForm.isEmpty()) {
            error.append("\n").append("Icon Value cant be empty please enter icon symbol");
        }
        if (descriptionValueForm.isEmpty()) {
            error.append("\n").append("Description cant be empty please fill description");
        }
        return error;
    }
}
