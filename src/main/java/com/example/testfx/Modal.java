package com.example.testfx;

public class Modal {
    public static float cacll(long number1, long number2, String operator) {
        switch (operator) {
            case "x":
                return number1 * number2;
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "/":
                if (number2 == 0) return 0;
                return (float) number1 / number2;
        }
        return 0;
    }
}
