package sk.michacu.zmenaren;

import sk.michacu.zmenaren.databaza.PgOperations;
import sk.michacu.zmenaren.model.MenaObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    public boolean isNotNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        return !pattern.matcher(strNum).matches();
    }
}
