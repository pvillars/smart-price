package cl.anpetrus.smartprice.data;

/**
 * Created by Petrus on 16-12-2017.
 */

public class StringProcessor {

    public static String getResumeStr(String s, Integer maxLength) {
        s = s != null ? s : "";
        if (s.length() > maxLength) {
            s = s.substring(0, maxLength - 4) + "...";
        }
        return s;
    }
}
