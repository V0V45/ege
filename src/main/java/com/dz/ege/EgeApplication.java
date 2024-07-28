package com.dz.ege;

import java.util.HashMap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/* Основной поток приложения */
public class EgeApplication {
	public static void main(String[] args) {
		// создаем ApplicationContext как Java-конфигурация
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
		// достаем бин resultsProcessor из ApplicationContext
		ResultsProcessor processor = context.getBean(ResultsProcessor.class);
		// создаем коллекцию ключ-значение, которая содержит в себе только правильные ответы ученика;
		// в аргументах метода указываем путь к файлам ответов ученика и правильных ответов
		HashMap<Integer, Character> onlyRightAnswers = processor.getOnlyRightAnswersHashMap("./src/main/resources/test_example.txt", "./src/main/resources/keys.txt");
		// выводим правильные ответы в консоль
		processor.printHashMap(onlyRightAnswers);
		// считаем баллы
		int result = processor.countPoints(onlyRightAnswers);
		// выводим количество баллов
		System.out.println("Итого баллов: " + result);
		// закрываем ApplicationContext
		context.close();
	}

}
