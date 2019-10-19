package woorinaru.repository.sql.mapping.criteria;

import woorinaru.repository.sql.mapping.contract.ModelContract;

public interface ModelCriteria {
    boolean isSatisfiedBy(ModelContract contract);
}
