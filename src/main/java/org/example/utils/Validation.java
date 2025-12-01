package org.example.utils;
import org.example.models.Position;

public class Validation {
    public static boolean isStringValid(String s) {
        return s != null && !s.trim().isEmpty(); //jesli nie jest nullem i nie jest puste zwraca true
    }

    public static boolean isEmailValid(String email) {
        if(email == null){
            return false;
        }
        String regexPattern = "^(.+)@(\\S+)$";
        return email.matches(regexPattern); //jesli email ma @ zwraca true
    }

    public static boolean isPositionValid(Position position) {
        if (position != null) {
            for (Position pos : Position.values()) {
                if (pos.name().equals(position.name())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSalaryValid(double salary) {
        return salary >= 0; //jesli salary jest >= 0 zwraca true
    }
}
