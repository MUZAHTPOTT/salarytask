package ru.spring.salarytask;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Класс для инициализации сервлета в приложении Spring Boot
 */
public class ServletInitializer extends SpringBootServletInitializer {
        
        /**
         * Переопределение метода, который настраивает приложение Spring Boot
         * @param application билдер Spring приложения
         */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SalarytaskApplication.class);
	}

}
