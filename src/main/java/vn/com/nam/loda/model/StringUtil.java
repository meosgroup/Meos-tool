package vn.com.nam.loda.model;

public class StringUtil {
    public static String getIdFromUrl(String s){
        String array[] = s.split("-");
        return array[array.length-1];
    }

}
