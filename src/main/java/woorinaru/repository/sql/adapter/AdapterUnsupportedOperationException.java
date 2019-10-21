package woorinaru.repository.sql.adapter;

public class AdapterUnsupportedOperationException extends RuntimeException {
    public AdapterUnsupportedOperationException() {
        super("Unsupported in adapter class");
    }
}
