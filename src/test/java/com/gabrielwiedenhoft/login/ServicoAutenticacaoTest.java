package com.gabrielwiedenhoft.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServicoAutenticacaoTest {

    private ServicoAutenticacao servico;

    @BeforeEach
    void preparar() {
        servico = new ServicoAutenticacao();
        servico.cadastrar("Gabriel", "senha-forte".toCharArray());
    }

    @Test
    void aceitaUsuarioESenhaCorretos() {
        assertTrue(servico.autenticar("Gabriel", "senha-forte".toCharArray()));
    }

    @Test
    void usuarioNaoDiferenciaMaiusculasEEspacos() {
        assertTrue(servico.autenticar("  gabriel ", "senha-forte".toCharArray()));
    }

    @Test
    void senhaDiferenciaMaiusculas() {
        assertFalse(servico.autenticar("Gabriel", "SENHA-FORTE".toCharArray()));
    }

    @Test
    void rejeitaSenhaErrada() {
        assertFalse(servico.autenticar("Gabriel", "outra".toCharArray()));
    }

    @Test
    void rejeitaUsuarioInexistente() {
        assertFalse(servico.autenticar("ninguem", "senha-forte".toCharArray()));
    }

    @Test
    void rejeitaEntradasNulasOuVazias() {
        assertFalse(servico.autenticar(null, "senha-forte".toCharArray()));
        assertFalse(servico.autenticar("Gabriel", null));
        assertFalse(servico.autenticar("Gabriel", new char[0]));
    }

    @Test
    void naoPermiteCadastrarUsuarioDuplicado() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("GABRIEL", "x".toCharArray()));
    }

    @Test
    void naoPermiteCadastroSemUsuarioOuSenha() {
        assertThrows(IllegalArgumentException.class, () -> servico.cadastrar(" ", "x".toCharArray()));
        assertThrows(IllegalArgumentException.class, () -> servico.cadastrar("ana", new char[0]));
        assertThrows(IllegalArgumentException.class, () -> servico.cadastrar("ana", null));
    }

    @Test
    void limparApagaOConteudoDaSenha() {
        char[] senha = "segredo".toCharArray();
        ServicoAutenticacao.limpar(senha);
        assertEquals("\0\0\0\0\0\0\0", new String(senha));
    }
}
