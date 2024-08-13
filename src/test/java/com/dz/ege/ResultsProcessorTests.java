package com.dz.ege;

import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit-тесты
class ResultsProcessorTests {
	private ResultsProcessor processor;
	private String PATH_TO_RESULTS;
	private String PATH_TO_CORRECT_ANSWERS;

	// Задание констант перед тестами
	@BeforeEach
	void init() {
        processor = new ResultsProcessor(); // создаем объект класса ResultsProcessor
        processor.setOnePointTasks("1,2,3,4"); // для заданий оценкой в 1 балл добавляем первые 4
        processor.setTwoPointTasks("5,6,7,8"); // в два балла - следующие 4
        processor.setThreePointTasks("9,10"); // в три балла - самые последние 2
		PATH_TO_RESULTS = "./src/test/resources/keys_unittest.txt"; // это путь к тестовому файлу с результатами
		PATH_TO_CORRECT_ANSWERS = "./src/test/resources/result_unittest.txt"; // это путь к тестовому файлу с ключами
	}
	
	/* ТЕСТ МЕТОДА GET ONLY RIGHT ANSWERS HASH MAP */
	// Для тестов используются отдельные файлы с ключами и с результатами.
	// Тест проверяет, правильные ли ответы получаются. Правильные ответы: 2, 4, 5, 7, 8
	@Test
	void testGetOnlyRightAnswersHashMap_shouldReturnOnlyRightAnswers() {
		HashMap<Integer, Character> onlyRightAnswers = processor.getOnlyRightAnswersHashMap(PATH_TO_RESULTS, PATH_TO_CORRECT_ANSWERS);
		boolean areEqual = false;
		HashSet<Integer> keys = new HashSet<>();
		keys.add(2);
		keys.add(4);
		keys.add(5);
		keys.add(7);
		keys.add(8);
		if (keys.equals(onlyRightAnswers.keySet())) {
			areEqual = true;
		} else {
			areEqual = false;
		}
		Assertions.assertTrue(areEqual);
	}

	/* ТЕСТ МЕТОДА COUNT POINTS */
	// Тест проверяет, правильно ли считаются баллы. В соответствии с правильными ответами, должно быть 8 баллов
	@Test
	void testCountPoints_shouldCountEightPoints() {
		HashMap<Integer, Character> onlyRightAnswers = processor.getOnlyRightAnswersHashMap(PATH_TO_RESULTS, PATH_TO_CORRECT_ANSWERS);
		Assertions.assertEquals(8, processor.countPoints(onlyRightAnswers));
	}
}



