package id.web.alterationstudio.sewainaja.alterfest.Helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febrian on 12/19/16.
 */
public class Helpers {
    /**
     * Java Helper
     * @param email
     * @return
     */

    public static boolean isValidEmail(String email)
    {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }
}
