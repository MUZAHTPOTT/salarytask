/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.converters;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

/**
 * Конвертер для преобразования строки в LocalDate
 */
@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    
    /**
     * Переопределение метода convert для преобразования строки в LocalDate
     * @param dateString строка, представляющая дату
     * @return объект LocalDate, представляющий дату
     * @throws IllegalArgumentException если строка имеет неверный формат даты
     */
    @Override
    public LocalDate convert(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты", e);
        }
    }
}
