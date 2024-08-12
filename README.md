### ToDoList-Spring

#Descrição

O ToDoList-Spring é uma aplicação web de gerenciamento de tarefas (To-Do List) construída utilizando o framework Spring Boot. Esta aplicação permite aos usuários criar, visualizar, atualizar e excluir tarefas, proporcionando uma interface simples e eficiente para o gerenciamento de listas de tarefas.

# Funcionalidades

Adicionar Tarefas: Crie novas tarefas com título e descrição.
Visualizar Tarefas: Liste todas as tarefas criadas.
Atualizar Tarefas: Edite o título e a descrição das tarefas existentes.
Excluir Tarefas: Remova tarefas da lista.
Filtros de Visualização: Filtre tarefas por status (concluída/não concluída).

# Tecnologias Utilizadas

Java 11+
Spring Boot
H2 Database (banco de dados em memória para desenvolvimento e testes)
Spring Data JPA (para persistência de dados)

# Instalação e Execução

Pré-requisitos
Java 11 ou superior
Maven

# Passos para Execução

> Clone o repositório:

```
git clone https://github.com/BOThiago/ToDoList-Spring.git
```

> Navegue até o diretório do projeto:

```
cd ToDoList-Spring
```

> Compile e execute a aplicação:

```
mvn spring-boot:run
```

Acesse a aplicação no navegador:

http://localhost:8080

# Estrutura do Projeto

> src/main/java/com/example/todolist - Contém o código-fonte principal da aplicação.
> src/main/resources/templates - Arquivos de template Thymeleaf.
> src/main/resources/static - Arquivos estáticos (CSS, JS).
> src/main/resources/application.properties - Configurações da aplicação.

# Banco de Dados
A aplicação usa um banco de dados em memória H2 por padrão. Para acessar o console do H2, vá até:

```
http://localhost:8080/h2-console
```

Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.
