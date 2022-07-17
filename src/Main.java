import java.util.Scanner;

public class Main {

    static boolean isRomanNumerals = false;

    public static char isArithmeticExpression(String input) {
        char [] symbols = {'+', '-', '*', '/'};

        for (char element: symbols) {
            if(input.contains(String.valueOf(element))) {
                if(element != input.charAt(0) && element != input.charAt(input.length() - 1)) {
                    return element;
                }
                else {
                    throw new RuntimeException("Это не математическое выражение.");
                }
            }
        }

        return ' ';
    }

    public static int[] toArabicNumerals(String [] stringOperands) {
        int [] operands = new int[stringOperands.length];

        for (int i = 0; i< stringOperands.length; i++) {
            stringOperands[i] = stringOperands[i].toUpperCase();
            int number = 0;
            for (int j = stringOperands[i].length() - 1; j>=0; j--) {
                switch (stringOperands[i].charAt(j)) {
                    case 'I' -> number++;
                    case 'V' -> {
                        number += 5;
                        if (j != 0) {
                            if (stringOperands[i].charAt(j - 1) == 'I') {
                                number--;
                                j--;
                            }
                        }
                    }
                    case 'X' -> {
                        number += 10;
                        if (j != 0) {
                            if (stringOperands[i].charAt(j - 1) == 'I') {
                                number--;
                                j--;
                            }
                        }
                    }
                    default -> throw new RuntimeException("Таких римских цифр в базе нет.");
                }
            }
            operands[i] = number;
        }

        return operands;
    }

    public static String toRomanNumeral (int number) {
        String romanNumber = "";

        if(number >= 100) {
            int numbersOfHundreds = number/100;
            number -= 100 * numbersOfHundreds;
            for (int i = 0; i < numbersOfHundreds; i++) {
                romanNumber += "C";
            }
        }

        if(number >=90) {
            romanNumber += "XC";
            number -= 90;
        } else if(number >= 50) {
            romanNumber += "L";
            number -= 50;
        } else if(number >= 40) {
            romanNumber += "XL";
            number -= 40;
        } else if(number >= 10) {
            int numbersOfTens = number/10;
            number -= 10 * numbersOfTens;
            for(int i = 0; i < numbersOfTens; i++) {
                romanNumber += "X";
            }
        }

        if (number == 9) {
            romanNumber += "IX";
        } else if (number >= 5) {
            romanNumber += "V";
            number -= 5;

            for (int i = 0; i < number; i++) {
                romanNumber += "I";
            }
        } else if (number == 4) {
            romanNumber += "IV";
        } else {
            for (int i = 0; i < number; i++) {
                romanNumber += "I";
            }
        }

        return romanNumber;
    }

    public static int[] getOperands(String input, char symbol) {
        input = input.replace(symbol, ' ');
        String [] stringOperands = input.split(" ");
        int notNumberCount = 0;

        int [] operands = new int[stringOperands.length];

        for (int i = 0; i<operands.length; i++) {
            try {
                operands[i] = Integer.parseInt(stringOperands[i]);
            }
            catch (NumberFormatException ex) {
                notNumberCount++;
            }
        }

        if (notNumberCount != 0) {
            if(notNumberCount != operands.length) {
                throw new RuntimeException("Оба числа должны бить либо римскими, либо арабскими.");
            } else {
                isRomanNumerals = true;
                operands = toArabicNumerals(stringOperands);
            }
        }

        for (int number: operands) {
            if(number < 1 || number > 10) {
                throw new RuntimeException("Числа от 1 до 10!");
            }
        }

        return operands;
    }

    public static String calc(String input) {
        int result = 0;
        input = input.replaceAll(" ", "");

        char symbol = isArithmeticExpression(input);
        int [] operands = getOperands(input, symbol);

        switch (symbol) {
            case '+' -> result = operands[0] + operands[1];
            case '-' -> result = operands[0] - operands[1];
            case '*' -> result = operands[0] * operands[1];
            case '/' -> result = operands[0] / operands[1];
        }

        if(isRomanNumerals) {
            if(result < 1) {
                throw new RuntimeException("Результат работы Римскими цифрами не может быть ноль и ниже.");
            }
            return toRomanNumeral(result);
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Правила: Числа от 1 до 10, формат выражения \"a +*/- b\"");
        System.out.print("Введите математическое выражение: ");
        String userInput = consoleInput.nextLine();

        String result = calc(userInput);
        System.out.println("Результат: " + result);
    }
}
