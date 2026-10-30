package com.gabrielwiedenhoft.login;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Cadastro e verificação de usuários. As senhas nunca são guardadas: só um hash PBKDF2
 * com sal aleatório, comparado em tempo constante para dificultar ataques de temporização.
 */
public class ServicoAutenticacao {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACOES = 120_000;
    private static final int TAMANHO_SAL = 16;
    private static final int TAMANHO_HASH_BITS = 256;

    private record Credencial(byte[] sal, byte[] hash) { }

    private final Map<String, Credencial> usuarios = new HashMap<>();
    private final SecureRandom aleatorio = new SecureRandom();

    /** Cadastra um usuário. O nome é tratado sem diferenciar maiúsculas de minúsculas. */
    public void cadastrar(String usuario, char[] senha) {
        String chave = normalizar(usuario);
        if (chave.isEmpty()) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }
        if (senha == null || senha.length == 0) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (usuarios.containsKey(chave)) {
            throw new IllegalArgumentException("Usuário já cadastrado: " + usuario);
        }
        byte[] sal = new byte[TAMANHO_SAL];
        aleatorio.nextBytes(sal);
        usuarios.put(chave, new Credencial(sal, calcularHash(senha, sal)));
    }

    /** Retorna {@code true} somente se o usuário existe e a senha confere. */
    public boolean autenticar(String usuario, char[] senha) {
        if (senha == null) {
            return false;
        }
        Credencial credencial = usuarios.get(normalizar(usuario));
        if (credencial == null) {
            // Gasta o mesmo tempo de um usuário válido, para não revelar quais usuários existem
            calcularHash(senha, new byte[TAMANHO_SAL]);
            return false;
        }
        byte[] calculado = calcularHash(senha, credencial.sal());
        return MessageDigest.isEqual(calculado, credencial.hash());
    }

    private static String normalizar(String usuario) {
        return usuario == null ? "" : usuario.trim().toLowerCase(Locale.ROOT);
    }

    private static byte[] calcularHash(char[] senha, byte[] sal) {
        PBEKeySpec spec = new PBEKeySpec(senha, sal, ITERACOES, TAMANHO_HASH_BITS);
        try {
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException erro) {
            throw new IllegalStateException("Algoritmo de hash indisponível.", erro);
        } finally {
            spec.clearPassword();
        }
    }

    /** Apaga o conteúdo de um array de senha da memória. */
    public static void limpar(char[] senha) {
        if (senha != null) {
            Arrays.fill(senha, '\0');
        }
    }
}
