package club.cybet.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Misc {

    public static List<Integer> convertToIntArray(List<String> stringArray) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                System.err.println("Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    public static List<Integer> convertToIntArray(String stringArray, String delimiter){
        return convertToIntArray(Arrays.asList(stringArray.split(delimiter)));
    }

    public static List<Long> convertToLongArray(List<String> stringArray) {
        ArrayList<Long> result = new ArrayList<Long>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Long.parseLong(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                System.err.println("Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    public static List<Long> convertToLongArray(String stringArray, String delimiter){
        return convertToLongArray(Arrays.asList(stringArray.split(delimiter)));
    }

    public static List<String> convertFromCamelCaseToHeaderCase(List<String> words){
        List<String> converted = new ArrayList<>();

        for (String camelValue : words){
            StringBuilder word = new StringBuilder();
            for (String w : camelValue.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
                word.append(w).append(" ");
            }
            String convertedText = word.toString().trim();
            convertedText = Character.toUpperCase(convertedText.charAt(0)) + convertedText.substring(1);
            converted.add(convertedText);
        }

        return converted;
    }


}
