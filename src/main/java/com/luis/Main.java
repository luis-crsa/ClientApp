package com.luis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
        Client client = new Client();

        System.out.print("Nome: ");
        client.setName(scanner.nextLine());

        System.out.print("Email: ");
        client.setEmail(scanner.nextLine());

        System.out.print("Telefone: ");
        client.setPhone(scanner.nextLine());

        System.out.print("CPF: ");
        client.setCpf(scanner.nextLine());

        System.out.print("Data de nascimento (yyyy-MM-dd): ");
        client.setBirthDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("Renda mensal: ");
        String income = scanner.nextLine();
        client.setMonthlyIncome(income.isBlank() ? null : Double.parseDouble(income));

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
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = dao.findById(id);

        if (client == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Atualize os dados. Deixe em branco para manter.");

        System.out.print("Nome atual (" + client.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) client.setName(name);

        System.out.print("Email atual (" + client.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) client.setEmail(email);

        System.out.print("Telefone atual (" + client.getPhone() + "): ");
        String phone = scanner.nextLine();
        if (!phone.isBlank()) client.setPhone(phone);

        System.out.print("CPF atual (" + client.getCpf() + "): ");
        String cpf = scanner.nextLine();
        if (!cpf.isBlank()) client.setCpf(cpf);

        System.out.print("Data de nascimento atual (" + client.getBirthDate() + ") [yyyy-MM-dd]: ");
        String birth = scanner.nextLine();
        if (!birth.isBlank()) client.setBirthDate(LocalDate.parse(birth));

        System.out.print("Renda mensal atual (" + client.getMonthlyIncome() + "): ");
        String income = scanner.nextLine();
        if (!income.isBlank()) client.setMonthlyIncome(Double.parseDouble(income));

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
