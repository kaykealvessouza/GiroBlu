package Excecoes;

/**
 * Exceção-base para as exceções personalizadas do sistema GiroBlu.
 */

public class GiroBluException extends Exception {

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public GiroBluException(String mensagem) {
        super(mensagem);
    }
}
