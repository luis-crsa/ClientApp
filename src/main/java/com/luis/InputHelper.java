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

    public String readEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isBlank() || isValidEmail(input)) return input;
            System.out.println("E-mail inválido. Ex: nome@dominio.com");
        }
    }

    public String readPhone(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isBlank() || isValidPhone(input)) return input;
            System.out.println("Telefone inválido. Digite DDD seguido do número (10 ou 11 dígitos). Ex: 11987654321");
        }
    }

    public String readValidCpf(String prompt) {
        while (true) {
            String cpf = readRequiredString(prompt).replaceAll("\\D", "");

            if (isValidCpf(cpf)) {
                return cpf;
            }

            System.out.println("CPF inválido. Certifique-se de digitar 11 números válidos.");
        }
    }

    public LocalDate readBirthDate(String prompt) {
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

    public Double readMonthlyIncome(String prompt) {
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

    private boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int firstCheckDigit = (sum1 * 10) % 11;
            if (firstCheckDigit == 10) firstCheckDigit = 0;

            sum2 += firstCheckDigit * 2;
            int secondCheckDigit = (sum2 * 10) % 11;
            if (secondCheckDigit == 10) secondCheckDigit = 0;

            return firstCheckDigit == Character.getNumericValue(cpf.charAt(9)) &&
                    secondCheckDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.replaceAll("[\\s()-]", "").matches("^\\d{10,11}$");
    }

    private boolean isValidEmail(String email) {
        return email != null &&
                email.contains("@") &&
                email.indexOf('@') > 0 &&
                email.indexOf('@') < email.length() - 1 &&
                email.substring(email.indexOf('@')).contains(".");
    }
}
