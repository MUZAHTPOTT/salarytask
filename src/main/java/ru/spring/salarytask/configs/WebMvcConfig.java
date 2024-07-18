/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.spring.salarytask.converters.StringToLocalDateConverter;

/**
 * Конфигурация веб-слоя MVC
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    private final StringToLocalDateConverter stringToLocalDateConverter;
    
    /**
     * Конструктор класса WebMvcConfig
     * @param stringToLocalDateConverter конвертер для преобразования строки в LocalDate
     */
    public WebMvcConfig(StringToLocalDateConverter stringToLocalDateConverter) {
        this.stringToLocalDateConverter = stringToLocalDateConverter;
    }
    
    /**
     * Добавляет конвертер в реестр форматтеров для обработки веб-запросов
     * @param registry реестр форматтеров
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToLocalDateConverter);
    }
    
}
