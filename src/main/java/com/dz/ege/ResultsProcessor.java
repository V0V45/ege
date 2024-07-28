package com.dz.ege;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Класс ResultsProcessor обрабатывает результаты, которые даны в текстовом файле */
public class ResultsProcessor {
    // Поля
    private HashSet<Integer> onePointTasks; // множество целых чисел, которые отвечают номера 1-балльных заданий
    private HashSet<Integer> twoPointTasks; // множество целых чисел, которые отвечают номера 2-балльных заданий
    private HashSet<Integer> threePointTasks; // множество целых чисел, которые отвечают номера 3-балльных заданий

    // Геттеры и сеттеры
    // Сеттеры принимают на вход String, а на выход дают множество HashSet. Это нужно для того, чтобы файл point.properties
    // правильно преобразовывался
    public HashSet<Integer> getOnePointTasks() {
        return this.onePointTasks;
    }

    public void setOnePointTasks(String onePointTasks) {
        this.onePointTasks = convertTasksToSet(onePointTasks);
    }

    public HashSet<Integer> getTwoPointTasks() {
        return this.twoPointTasks;
    }

    public void setTwoPointTasks(String twoPointTasks) {
        this.twoPointTasks = convertTasksToSet(twoPointTasks);
    }

    public HashSet<Integer> getThreePointTasks() {
        return this.threePointTasks;
    }

    public void setThreePointTasks(String threePointTasks) {
        this.threePointTasks = convertTasksToSet(threePointTasks);
    }
    

    // Методы
    // Преобразование текстового файла в коллекцию ключ-значение
    // На вход принимает путь к текстовому файлу с ответами ученика или с корректными ответами,
    // на выход дает коллекцию HashMap типа "номер задания: буквенный ответ"
    private HashMap<Integer, Character> txtFileToMap(String pathToAnswersFile) { // аргумент - путь к текстовому файлу
        HashMap<Integer, Character> outputHashMap = new HashMap<Integer, Character>(); // создаем пустую коллекцию
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToAnswersFile)); // создаем читателя файлов
            String currentLine = br.readLine(); // построчно читаем файл. Так как строка всего одна, то цикл не нужен
            br.close(); // закрываем читателя файлов
            String[] words = currentLine.split(", "); // разбиваем строку из файла на массив. В массиве останутся
            // значения типа ["1 - Б", "2 - Б", ...]
            for (String word : words) { // для каждого значения в вышеописанном массиве
                String[] numberAnswerPair = word.split(" - "); // разбиваем массив на подмассив, в котором останутся
                // ровно два значения типа [1, Б]
                outputHashMap.put(Integer.parseInt(numberAnswerPair[0]), numberAnswerPair[1].charAt(0));
                // кладем в коллекцию ключ из первого элемента подмассива, и значение из второго элемента подмассива
            }
        } catch (Exception e) {
            e.printStackTrace(); // в случае возникновения input-output ошибки выдаем подробную информацию
        }
        return outputHashMap; // возвращаем итоговую коллекцию
    }

    // Выдача только правильных результатов
    // На вход принимает две HashMap: с ответами ученика, и с правильными ответами
    // На выход выдает HashMap только с правильными ответами ученика
    private HashMap<Integer, Character> compareResultsToCorrectAnswers(HashMap<Integer, Character> results,
            HashMap<Integer, Character> correctAnswers) {
        // для начала сравниваем, совпадает ли количество заданий у ученика и в ответах
        if (results.keySet().equals(correctAnswers.keySet())) {
            // создаем пустую HashMap только с правильными ответами
            HashMap<Integer, Character> onlyCorrectAnswers = new HashMap<Integer, Character>();
            // запускаем цикл от первого задания до последнего
            for (int i = 1; i <= results.keySet().size(); i++) {
                // если значение по одному и тому же заданию совпадают у ученика и в ответах
                if (results.get(i).equals(correctAnswers.get(i))) {
                    // то пополняем нашу коллекцию с только правильными ответами
                    onlyCorrectAnswers.put(i, results.get(i));
                } else {
                    continue;
                }
            }
            return onlyCorrectAnswers;
        } else {
            throw new IllegalArgumentException("Количество заданий не совпадает с количеством заданий в файле с ответами!");
        }
    }

    // Преобразование строки заданий по весу в баллах из файла point.properties в массив целых чисел
    private HashSet<Integer> convertTasksToSet(String tasksString) {
        // Для начала убеждаемся, что point.properties заполнен правильно в соответствии с регулярным выражением
        // Формат должен быть типа "1,3,5"
        Pattern pattern = Pattern.compile("\\d+(,\\d+)*");
        Matcher matcher = pattern.matcher(tasksString);
        if (matcher.matches()) {
            // Разбиваем строчку на массив строчек, чтобы оставить только номера заданий
            String[] tasksSplitted = tasksString.split(",");
            // Создаем пустое множество, которое будет содержать номера заданий
            HashSet<Integer> tasks = new HashSet<Integer>();
            // Перебираем все задания в массиве строчек и добавляем их в множество
            for (String taskString : tasksSplitted) {
                tasks.add(Integer.parseInt(taskString));
            }
            return tasks;
        } else {
            throw new IllegalArgumentException("В файле properties неверно указаны задания!");
        }
    }

    // Получение коллекции ключ-значение только с правильными ответами ученика
    // Содержит в себе вышеописанные методы, на вход принимает путь к файлу с ответами ученика и путь к файлу с ключами
    // На выход выдает коллекцию ключ-значение только с правильными ответами ученика
    public HashMap<Integer, Character> getOnlyRightAnswersHashMap(String pathToTestResults, String pathToCorrectAnswers) {
        HashMap<Integer, Character> testResults = txtFileToMap(pathToTestResults);
        HashMap<Integer, Character> correctAnswers = txtFileToMap(pathToCorrectAnswers);
        HashMap<Integer, Character> rightAnswers = compareResultsToCorrectAnswers(testResults, correctAnswers);
        return rightAnswers;
    }

    // Подсчет баллов
    // На вход принимает коллекцию только с правильными ответами ученика
    public int countPoints(HashMap<Integer, Character> rightAnswers) {
        // Изначально у ученика 0 баллов
        int result = 0;
        // Перебираем все номера правильно-решенных заданий в коллекции с правильными ответами
        for (Integer taskNumber : rightAnswers.keySet()) {
            // Перебираем все задания, за которые дается один балл
            for (Integer onePointTask : this.onePointTasks) {
                // Если есть совпадение с текущим номером задания
                if (taskNumber == onePointTask) {
                    // добавляем один балл
                    result += 1;
                } else {
                    continue;
                }
            }
            // Перебираем все задания, за которые дается два балла
            for (Integer twoPointTask : this.twoPointTasks) {
                // Если есть совпадение с текущим номером задания
                if (taskNumber == twoPointTask) {
                    // добавляем два балла
                    result += 2;
                } else {
                    continue;
                }
            }
            // Перебираем все задания, за которые дается три балла
            for (Integer threePointTask : this.threePointTasks) {
                // Если есть совпадение с текущим номером задания
                if (taskNumber == threePointTask) {
                    // добавляем три балла
                    result += 3;
                } else {
                    continue;
                }
            }
        }
        return result;
    }

    // Вывод HashMap в консоль
    public void printHashMap(HashMap<Integer, Character> results) {
        results.forEach((key, value) -> System.out.println(key + ": " + value));
    }


}
