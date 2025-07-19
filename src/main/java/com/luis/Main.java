package com.luis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection con = Database.getInstance().getConnection();
        Statement statement = con.createStatement();

        String sql = FileUtils.loadTextFile("src/main/resources/schema.sql");
        statement.executeUpdate(sql);
        
        ClientDAO clientDAO = new ClientDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> createClient(scanner, clientDAO);
                    case "2" -> listClients(clientDAO);
                    case "3" -> findClient(scanner, clientDAO);
                    case "4" -> updateClient(scanner, clientDAO);
                    case "5" -> deleteClient(scanner, clientDAO);
                    case "0" -> {
                        running = false;
                        System.out.println("Saindo...");
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== MENU CLIENTES ===");
        System.out.println("1. Cadastrar novo cliente");
        System.out.println("2. Listar todos os clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Atualizar cliente");
        System.out.println("5. Excluir cliente");
        System.out.println("0. Sair");
    }

    private static void createClient(Scanner scanner, ClientDAO dao) throws SQLException {
        InputHelper input = new InputHelper(scanner);
        Client client = new Client();

        client.setName(input.readRequiredString("Nome: "));
        client.setEmail(input.readOptionalString("Email (opcional): "));
        client.setPhone(input.readOptionalString("Telefone (opcional): "));
        client.setCpf(input.readValidCpf("CPF: "));
        client.setBirthDate(input.readBirthDate("Data de nascimento (yyyy-MM-dd): "));
        client.setMonthlyIncome(input.readMonthlyIncome("Renda mensal (opcional): "));
        client.setRegistrationDate(LocalDate.now());

        dao.insert(client);
        System.out.println("Cliente cadastrado com sucesso!");
    }


    private static void listClients(ClientDAO dao) throws SQLException {
        var clients = dao.findAll();
        if (clients.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Client client : clients) {
            client.printDetails();
        }
    }

    private static void findClient(Scanner scanner, ClientDAO dao) throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = dao.findById(id);

        if (client == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        
        client.printDetails();
    }

    private static void updateClient(Scanner scanner, ClientDAO dao) throws SQLException {
        InputHelper input = new InputHelper(scanner);
        
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = dao.findById(id);

        if (client == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Atualize os dados. Deixe em branco para manter.");

        System.out.print("Nome atual (" + client.getName() + ")");
        String name = scanner.nextLine();
        if (!name.isBlank()) client.setName(name);

        System.out.print("Email atual (" + client.getEmail() + "), '-' para limpar: ");
        String email = scanner.nextLine();
        if (email.equals("-")) {
            client.setEmail(null);
        } else if (!email.isBlank()) {
            client.setEmail(email.trim());
        }

        System.out.print("Telefone atual (" + client.getPhone() + "), '-' para limpar: ");
        String phone = scanner.nextLine();
        if (phone.equals("-")) {
            client.setPhone(null);
        } else if (!phone.isBlank()) {
            client.setPhone(phone.trim());
        }

        client.setCpf(input.readOptionalValidCpf(client.getCpf()));

        while (true) {
            System.out.print("Data de nascimento atual (" + client.getBirthDate() + ") [yyyy-MM-dd]: ");
            String birth = scanner.nextLine();
            if (birth.isBlank()) break;
            try {
                LocalDate date = LocalDate.parse(birth);
                client.setBirthDate(date);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato yyyy-MM-dd.");
            }
        }

        System.out.print("Renda mensal atual (" + client.getMonthlyIncome() + "), '-' para limpar: ");
        String income = scanner.nextLine();
        if (income.equals("-")) {
            client.setMonthlyIncome(null);
        } else if (!income.isBlank()) {
            try {
                double value = Double.parseDouble(income);
                if (value < 0) {
                    System.out.println("A renda deve ser maior ou igual a 0. Valor ignorado.");
                } else {
                    client.setMonthlyIncome(value);
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido para renda. Valor ignorado.");
            }
        }

        dao.update(client);
        System.out.println("Cliente atualizado com sucesso!");
    }

    private static void deleteClient(Scanner scanner, ClientDAO dao) throws SQLException {
        System.out.print("Digite o ID do cliente a excluir: ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = dao.findById(id);
        
        if (client == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        
        dao.delete(id);
        System.out.println("Cliente excluído com sucesso.");
    }
}
