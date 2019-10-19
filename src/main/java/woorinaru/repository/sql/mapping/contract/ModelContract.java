package woorinaru.repository.sql.mapping.contract;

import java.lang.reflect.Field;
import java.util.Map;

@FunctionalInterface
public interface ModelContract {
    Map<Field, MapContract> getContract();

    default boolean equals(ModelContract other) {
        Map<Field, MapContract> contract = getContract();
        Map<Field, MapContract> otherContract = other.getContract();
        return contract.size() == otherContract.size() && contract.entrySet().stream().allMatch(e -> otherContract.containsKey(e.getKey()) && e.getValue().equals(otherContract.get(e.getKey())));
    }
}
