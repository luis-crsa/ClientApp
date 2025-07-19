package com.luis;

import java.time.LocalDate;

public class Client {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private LocalDate birthDate;
    private Double monthlyIncome;
    private LocalDate registrationDate;

    public Client() {
    }

    public Client(Integer id, String name, String email, String phone, String cpf, LocalDate birthDate, Double monthlyIncome, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.monthlyIncome = monthlyIncome;
        this.registrationDate = registrationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void printDetails() {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + name);
        System.out.println("E-mail: " + email);
        System.out.println("Telefone: " + phone);
        System.out.println("CPF: " + cpf);
        System.out.println("Data de Nascimento: " + birthDate);
        System.out.println("Renda Mensal: R$ " + String.format("%.2f", monthlyIncome));
        System.out.println("Data de Cadastro: " + registrationDate);
        System.out.println("─────────────────────────────────────────────\n");
    }
}
