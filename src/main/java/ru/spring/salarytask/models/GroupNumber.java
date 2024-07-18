/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.models;


/**
 * Перечисление, определяющее должности сотрудников и связанные с ними коэффициенты надбавок
 */
public enum GroupNumber {
    
    employee (0.03, 0.3, 0.0),
    manager (0.05, 0.4, 0.005),
    salesman (0.01, 0.35, 0.003);
    
    private Double experienceBonusCoefficient;
    
    private Double maxExperienceBonusCoefficient;
    
    private Double subordinateBonusCoefficient;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    private GroupNumber(Double experienceBonusCoefficient, Double maxExperienceBonusCoefficient, Double subordinateBonusCoefficient) {
        this.experienceBonusCoefficient = experienceBonusCoefficient;
        this.maxExperienceBonusCoefficient = maxExperienceBonusCoefficient;
        this.subordinateBonusCoefficient = subordinateBonusCoefficient;
    }    
    
    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Возвращает коэффициент бонуса за стаж работы для данной группы
     */
    public Double getExperienceBonusCoefficient() {
        return experienceBonusCoefficient;
    }
    
    /**
     * Возвращает максимальный коэффициент бонуса за стаж работы для данной группы
     */
    public Double getMaxExperienceBonusCoefficient() {
        return maxExperienceBonusCoefficient;
    }
    
    /**
     * Возвращает коэффициент бонуса за подчиненных для данной группы
     */
    public Double getSubordinateBonusCoefficient() {
        return subordinateBonusCoefficient;
    }
    
}