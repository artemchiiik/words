package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        int count = 0;
        if ((words != null) && (sample != null)){
            for (String word : words) {
                if (word.strip().equalsIgnoreCase(sample.strip())) {
                    count++;
                }
            }
        }
        return count;
    }

    public static String[] splitWords(String text) {
        String reg = "[,!?:;.\\s]+";
        if ((text == null) || (text.isEmpty()) || (Pattern.matches(reg, text))){
            return null;
        }
        else {
            String[] textTemp = text.split(reg);
            if (textTemp[0].isEmpty()){
                String[] textToReturn = new String[textTemp.length - 1];
                for (int i = 0; i < textTemp.length - 1; i++){
                    textToReturn[i] = textTemp[i + 1];
                }
                return textToReturn;
            }
            else {
                return textTemp;
            }
        }
    }

    public static String convertPath(String path, boolean toWin) {
        if ((path == null) || path.isEmpty()){
            return null;
        }

        String regUnOne = "^([~/\\.]|(~)|(\\.\\.)|dir){0,1}[\\w\\./\\s]+";
        String regUnTwo = "~{1}";

        String regWinOne = "(([A-Z]:)|(\\.)|dir)(\\\\[\\w\\s\\.]+)+(\\\\)?";
        String regWinTwo = "([A-Z]:)\\\\([\\w\\s\\.])+";
        String regWinThree = "((\\.)|(\\.\\.))(\\\\[\\w\\s\\.]+)+(\\\\)?";
        String regWinFour = "([A-Z]:)\\\\";

        if (Pattern.matches(regWinOne, path) || Pattern.matches(regWinTwo, path ) || Pattern.matches(regWinThree, path ) || Pattern.matches(regWinFour, path )){
            if (toWin){
                System.out.println("1" + path);
                return path;
            }
            else {
                System.out.println("2" + path.replace("C:\\User", "~").replace("C:", "/").replace("\\", "/"));
                return path.replace("C:\\User", "~").replace("C:", "/").replace("\\", "/").replace("//", "/");
            }
        }
        else {
            if (Pattern.matches(regUnOne, path) || Pattern.matches(regUnTwo, path)) {
                if (toWin) {
                    if (path.startsWith("/")){
                        return path.replaceFirst("/", "C:\\\\").replace("/", "\\");
                    }
                    System.out.println("3" + (path.replace("~", "C:\\User")).replace("/", "\\"));
                    return (path.replace("~", "C:\\User")).replace("/", "\\");
                } else {
                    System.out.println("4" + path);
                    return path;
                }
            }
            else {
                System.out.println("5" + path);
                return null;}
        }
    }

    public static String joinWords(String[] words) {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        if ((words == null) || words.length == 0){
            return null;
        }
        else {
            for (int i = 0; i < words.length; i++){
                if (!words[i].isEmpty()){
                    stringJoiner.add(words[i]);
                }
            }
            if (stringJoiner.toString().equals("[]")){
                return null;
            }
        }
        return stringJoiner.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}