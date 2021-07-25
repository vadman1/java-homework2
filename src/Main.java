import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String filename = null;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            filename = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!Path.of(filename).isAbsolute()) {
            Path path = Path.of(filename).toAbsolutePath(); // превращаем путь файла в абсолютный, если он относительный
            filename = String.valueOf(path);
        }

        StringBuilder stringBuilder = new StringBuilder(); // сюда поместим содержимое всего файла

        try(BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line + " "); // в конце пробел, чтобы начало сл строки не слиплось с предыдущей
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Integer> map = new TreeMap<>(); // TreeMap, чтобы сразу была сортировка в алфавитном порядке

        Pattern pattern = Pattern.compile("[A-Za-z]+"); // для отбора слов, состоящих из латинского алфавита
        Matcher matcher = pattern.matcher(stringBuilder.toString());
        while (matcher.find()) {
            String key = matcher.group().toLowerCase();
            if(map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        showStatistics(map);
        System.out.println("=================");
        showFrequentWord(map);
    }

    static void showStatistics(Map<String, Integer> map){
        if(map.isEmpty()) {
            System.out.println("В файле нет слов, состоящих из латинского алфавита");
            return;
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Слово: " + entry.getKey() + " Частота: "
                    + entry.getValue());
        }
    }

    static void showFrequentWord(Map<String, Integer> map) {
        if(map.isEmpty()) {
            System.out.println("В файле нет слов, состоящих из латинского алфавита");
            return;
        }

        Integer max = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue() > max){
                max = entry.getValue();
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue().equals(max)){
                System.out.println("Слово: " + entry.getKey() + " Частота: "
                        + entry.getValue());
            }
        }
    }
}
