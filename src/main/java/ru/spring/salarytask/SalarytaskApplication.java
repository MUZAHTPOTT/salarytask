package ru.spring.salarytask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для запуска Spring Boot
 */
@SpringBootApplication
public class SalarytaskApplication {
        
        /**
         * Метод main, который запускает Spring приложение
         * @param args аргументы командной строки
         */
	public static void main(String[] args) {
		SpringApplication.run(SalarytaskApplication.class, args);
	}

}
