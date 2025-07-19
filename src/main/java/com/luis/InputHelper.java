package com.luis;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readRequiredString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Esse campo é obrigatório.");
            }
        } while (input.isBlank());
        return input;
    }

    public String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public LocalDate readRequiredDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Esse campo é obrigatório.");
                continue;
            }

            try {
                LocalDate date = LocalDate.parse(input);
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("A data não pode estar no futuro.");
                } else {
                    return date;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Data ou formato inválido. Use yyyy-MM-dd.");
            }
        }
    }

    public Double readOptionalPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("O valor deve ser maior ou igual a 0.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
            }
        }
    }
}
