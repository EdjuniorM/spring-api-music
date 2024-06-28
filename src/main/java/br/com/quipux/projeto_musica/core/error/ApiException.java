package br.com.quipux.projeto_musica.core.error;

public class ApiException extends RuntimeException {
    private final String message;
    private final int status;

    public ApiException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
