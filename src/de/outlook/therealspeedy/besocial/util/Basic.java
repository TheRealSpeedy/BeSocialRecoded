package de.outlook.therealspeedy.besocial.util;

public class Basic {

    public static boolean stringArrayContainsString(String[] array, String string) {
        for (String s : array) {
            if (s.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public static String makeDatabaseString(String[] sourceArray) {
        StringBuilder returnString = new StringBuilder();
        for (String s : sourceArray) {
            if (s.equals("")) {
                continue;
            }
            if (returnString.toString().equals("")) {
                returnString.append(s);
            } else {
                returnString.append("&").append(s);
            }
        }
        return returnString.toString();
    }

    public static String addToDatabaseString(String databaseString, String addString) {
        databaseString = databaseString + "&" + addString;
        return databaseString;
    }

    public static String[] removeFromArray(String[] array, String string) {
        if (!stringArrayContainsString(array, string)) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(string)) {
                array[i] = "";
            }
        }
        return array;
    }

}
