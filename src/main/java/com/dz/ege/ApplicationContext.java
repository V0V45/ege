package com.dz.ege;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/* Java-конфигурация бинов Spring */
@Configuration
@PropertySource("classpath:point.properties") // указываем путь к файлу, где будут хранится переменные
public class ApplicationContext {
    // Значения
    @Value("${tasks.onepoint}") String onePoint; // создаем переменную заданий, за которые дают 1 балл, вытаскивая ее значение из файла properties
    @Value("${tasks.twopoints}") String twoPoints; // создаем переменную заданий, за которые дают 2 балла, вытаскивая ее значение из файла properties
    @Value("${tasks.threepoints}") String threePoints; // создаем переменную заданий, за которые дают 3 балла, вытаскивая ее значение из файла properties

    // Бины
    @Bean
    public ResultsProcessor resultsProcessor() {
        ResultsProcessor processor = new ResultsProcessor();
        processor.setOnePointTasks(onePoint);
        processor.setTwoPointTasks(twoPoints);
        processor.setThreePointTasks(threePoints);
        return processor;
    }
    
}
