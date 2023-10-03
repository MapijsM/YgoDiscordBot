package events.commands;

public class CapitalizeString {
    public static String getCapitalizedString(String stringToCapitalize) {
        String[] words = stringToCapitalize.split(" ");
        StringBuilder capitalizedString = new StringBuilder();
        for (String word : words) {
            String first = word.substring(0, 1);
            String afterFirst = word.substring(1);
            capitalizedString.append(first.toUpperCase()).append(afterFirst).append(" ");
        }
        return capitalizedString.toString();
    }
}
