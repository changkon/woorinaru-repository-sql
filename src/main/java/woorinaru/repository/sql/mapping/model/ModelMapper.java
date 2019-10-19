package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.mapping.criteria.ModelCriteria;

public interface ModelMapper<M, E> extends ModelCriteria {
    E mapToEntity(M model);
    M mapToModel(E entity);
    Class<E> getEntityClass();
    Class<M> getModelClass();
}
