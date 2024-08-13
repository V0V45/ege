# Подсчет баллов по результатам экзамена

## Основные файлы программы:
### src/main/java/com/dz/ege
- **ApplicationContext.java**: Java-конфигурация Spring в явном виде
- **EgeApplicationContext.java**: основной поток программы
- **ResultsProcessor.java**: класс, который производит преобразования над текстовыми файлами с ответами и результатами ученика
### src/main/resources
- **keys.txt**: файл правильных ответов
- **point.properties**: файл свойств Spring; содержит в себе переменные, которые отвечают, за какие задания сколько баллов считать
- **test_example.txt**: файл ответов ученика
### src/test/java/com/dz/ege
- **ResultsProcessorTests.java**: unit-тесты класса ResultsProceesor
### src/test/resources
- **keys_unittest.txt**: файл правильных ответов, используемый в юнит-тестах
- **result_unittest.txt**: файл ответов ученика, используемый в юнит-тестах