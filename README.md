# Exercícios Java — Janela de Login (Swing)

Repositório de estudos em Java. Contém, por enquanto, um projeto de tela de login feito com **Java Swing** no NetBeans, para praticar interface gráfica e eventos de botão.

## JaneladeLogin

Janela com campos de usuário e senha e os botões **Entrar** e **Limpar**. O método `logar` compara o que foi digitado com um usuário e uma senha fixos no código.

> Projeto de estudo: as credenciais de demonstração ficam no código-fonte e a senha não é criptografada. Um sistema real deve guardar senhas com hash em um banco de dados.

### Tecnologias

- Java 17
- Swing (interface gráfica)
- Maven

### Como executar

Requisitos: JDK 17 e Maven.

```bash
git clone https://github.com/Gabrielmachado01gg/java.git
cd java/JaneladeLogin
mvn compile exec:java -Dexec.mainClass=com.mycompany.janeladelogin.Login
```

Também é possível abrir a pasta `JaneladeLogin` no NetBeans e executar o projeto.

## Autor

Gabriel Wiedenhoft — [LinkedIn](https://www.linkedin.com/in/gabriel-wiedenhoft-machado)
