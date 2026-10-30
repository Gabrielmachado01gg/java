# Janela de Login — Java Swing

[![CI](https://github.com/Gabrielmachado01gg/java/actions/workflows/ci.yml/badge.svg)](https://github.com/Gabrielmachado01gg/java/actions/workflows/ci.yml)
![Java 17](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Licença MIT](https://img.shields.io/badge/licen%C3%A7a-MIT-green)

Tela de login desktop feita com **Java Swing**, com a lógica de autenticação separada da interface e coberta por testes.

## Boas práticas aplicadas

- **Senha em `JPasswordField`:** os caracteres ficam ocultos na tela e a senha é lida como `char[]`, apagado da memória logo após o uso.
- **Hash PBKDF2-HMAC-SHA256** (120 mil iterações) com **sal aleatório** por usuário: nenhuma senha é guardada em texto puro.
- **Comparação em tempo constante** (`MessageDigest.isEqual`) e mesmo custo de tempo para usuário inexistente, evitando vazar quais usuários existem.
- **Separação de responsabilidades:** `Login` cuida só da tela; `ServicoAutenticacao` guarda as regras.
- **Testes JUnit 5** e integração contínua no GitHub Actions.

## Estrutura

```
src/main/java/com/gabrielwiedenhoft/login/
├── Login.java                  # janela (gerada no editor visual do NetBeans)
├── Login.form                  # layout da janela
└── ServicoAutenticacao.java    # cadastro e verificação com hash
src/test/java/...               # testes JUnit 5
```

## Como executar

Requisitos: JDK 17+ e Maven 3.9+.

```bash
git clone https://github.com/Gabrielmachado01gg/java.git
cd java
mvn -B verify
java -jar target/janela-login-1.0.0.jar
```

## Aviso

O usuário de demonstração (`Gabriel` / `12345`) existe apenas para fins didáticos e fica no código-fonte. Em um sistema real, as credenciais viriam de um banco de dados e nunca estariam no código.

## Autor

Gabriel Wiedenhoft — [LinkedIn](https://www.linkedin.com/in/gabriel-wiedenhoft-machado)

Licença [MIT](LICENSE).
