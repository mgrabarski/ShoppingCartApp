package grabarski.shoppingcartapp.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public class DataFormatter {

    public static String formatCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(amount);
    }

    public static String formatDate(long date) {
        return new SimpleDateFormat("MM dd").format(new Date(date));
    }
}
