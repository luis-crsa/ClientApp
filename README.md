# ClientApp – Java Console CRUD com SQLite
Esta é uma aplicação de console com funcionalidades básicas de CRUD (Create, Read, Update, Delete) de clientes.

<img width="420" height="256" alt="image" src="https://github.com/user-attachments/assets/3f0c3289-7b6f-41ba-aeb2-dc4135a9ccee" />

## 🛠️ Tecnologias utilizadas
- Java
- SQLite
- Maven

## ✨ Funcionalidades
- Cadastrar clientes
- Listar todos os clientes
- Buscar cliente por ID
- Atualizar informações de um cliente
- Deletar um cliente
- Validações para CPF, email, telefone, datas e renda

## 🧾 Campos da entidade Client
| Campo             | Tipo         | Obrigatório | Observações                                 |
|-------------------|--------------|-------------|---------------------------------------------|
| `id`              | Integer      | Sim         | Gerado automaticamente                      |
| `name`            | String       | Sim         |                                             |
| `email`           | String       | Não         | Validação básica de formato                 |
| `phone`           | String       | Não         | Aceita formatos com ou sem parênteses       |
| `cpf`             | String       | Sim         | Validação completa de CPF                   |
| `birthDate`       | LocalDate    | Sim         | Não pode estar no futuro                    |
| `monthlyIncome`   | Double       | Não         | Deve ser >= 0, se informado                 |
| `registrationDate`| LocalDate    | Sim         | Definido automaticamente no cadastro        |

## ▶️ Como executar
Pré-requisitos: Java 21 e Maven
1. Clone o repositório
```bash
git clone https://github.com/luis-crsa/ClientApp.git
cd ClientApp
```

2. Compile e execute
```bash
mvn package
java -jar target/ClientApp-1.0-SNAPSHOT.jar
```

## 💻 Como utilizar a aplicação
Após executar a aplicação, você verá um menu com opções numeradas. Digite o número correspondente à ação que deseja realizar e siga as instruções no terminal.

### 1️⃣ Cadastrar novo cliente
Escolha a opção 1. A aplicação exibirá os campos a serem preenchidos.
Exemplo de preenchimento:
```bash
Nome: João da Silva  
Email: joao@gmail.com  
Telefone: (84) 98515-0820
CPF: 428.279.842-45
Data de nascimento (AAAA-MM-DD): 1990-05-20  
Renda mensal: 3500.00
```
> Campos obrigatórios: nome, CPF, data de nascimento.

### 2️⃣ Listar todos os clientes
Escolha a opção 2.
A aplicação exibirá uma lista com todos os clientes cadastrados, incluindo todos os campos formatados de forma clara.

### 3️⃣ Buscar cliente por ID
Escolha a opção 3 e insira o ID do cliente.
Se o cliente existir, seus dados serão exibidos. Caso contrário, será exibida uma mensagem de erro.

### 4️⃣ Atualizar cliente
Escolha a opção 4 e informe o ID do cliente a ser atualizado.
Se o cliente existir, você poderá atualizar todos os campos, exceto o ID. Caso contrário, será exibida uma mensagem de erro.

### 5️⃣ Deletar cliente
Escolha a opção 5 e informe o ID do cliente que deseja excluir.
Se o cliente existir, a aplicação confirmará a exclusão. Caso contrário, será exibida uma mensagem de erro.

### 0️⃣ Sair
Escolha a opção 0.
Encerra a aplicação.

## ✅ Testes
Este projeto possui testes unitários para as operações de CRUD da entidade Client. Os testes são executados em um banco de dados SQLite em memória.
Para rodar os testes:
```bash
vmn test
```
Os testes cobrem:
- Inserção de clientes
- Busca por ID
- Listagem de todos os clientes
- Atualização de dados
- Exclusão de registros

# 👨‍💻Autor
Luís Cláudio Rodrigues Sarmento
