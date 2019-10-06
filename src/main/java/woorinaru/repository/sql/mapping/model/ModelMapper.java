package woorinaru.repository.sql.mapping.model;

public interface ModelMapper<M, E> {
    E mapToEntity(M model);
    M mapToModel(E entity);
}
