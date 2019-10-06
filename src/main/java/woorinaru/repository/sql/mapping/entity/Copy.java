package woorinaru.repository.sql.mapping.entity;

public interface Copy<O> {
    void copy(O src, O dest);
}
