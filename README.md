# 🌿 Ecosystem

O **Ecosystem** é uma aplicação full stack desenvolvida como parte do Trabalho de Conclusão de Curso (TCC), com o objetivo de facilitar o **armazenamento, consulta e visualização de dados acadêmicos e ambientais**, especialmente voltados para projetos de ecologia.

A aplicação foi construída utilizando **Spring Boot** no backend e **Angular** no frontend, com integração a banco de dados PostgreSQL e autenticação via JWT.

---

## 🧑🏽‍💻 Funcionalidades Principais

- 📚 Cadastro e atualização de **fontes bibliográficas**
- 👤 Associação de múltiplos **autores** e **palavras-chave**
- 🔎 Pesquisa avançada com filtros dinâmicos (por título, autor, ano, tipo, mídia)
- 🔐 Autenticação de usuários com controle de acesso (admin/user)
- 🧾 Interface amigável para visualização e edição dos registros

---

## 🛠️ Tecnologias Utilizadas

### 🔧 Backend (Java)
- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Hibernate
- ModelMapper

---

## 🧠 Estrutura de Entidades

- `BibliographicSource` → Fonte bibliográfica principal
- `Author` → Nome, email, ORCID, afiliação
- `Keyword` → Palavras-chave relacionadas
- `User` → Usuários com roles (`ADMIN`, `USER`)
- `Role` → Perfis e permissões do sistema

---

## 🔐 Segurança

- Login com username e password
- Geração de JWT no backend
- Proteção de rotas no Angular (guards por perfil)
- Requisições autenticadas com token JWT

---

## 🧪 Testes

- Testes manuais via Postman
- Integração com Swagger para documentação da API
- Testes unitários com JUnit e Mockito (em progresso)

---

## 📂 Organização do Projeto

### Backend

```bash
src/
├── controllers/
├── services/
├── repositories/
├── dtos/
├── entities/
├── security/
├── enums/
└── specification/
 
# Navegue até o diretório do backend
cd ecosystem-backend

# Compile e execute com Maven
./mvnw spring-boot:run
