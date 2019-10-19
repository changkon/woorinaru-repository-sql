package woorinaru.repository.sql.mapping.contract;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModelContractBuilder {

    private Map<Field, MapContract> contractArgs;
    private Class<?> modelClass;

    public ModelContractBuilder(Class<?> modelClass) {
        contractArgs = new HashMap<>();
        this.modelClass = modelClass;
    }

    public ModelContractBuilder all() {
        FieldUtils.getAllFieldsList(modelClass).stream()
            .forEach(field -> contractArgs.put(field, MapContract.MAP));
        return this;
    }

    public ModelContractBuilder none() {
        FieldUtils.getAllFieldsList(modelClass).stream()
            .forEach(field -> contractArgs.put(field, MapContract.NOT_MAP));
        return this;
    }

    public ModelContractBuilder includeFieldNames(String... fields) {
        Set<String> includedFieldNames = Set.of(fields);
        List<Field> allFields = FieldUtils.getAllFieldsList(modelClass);

        allFields.stream()
            .filter(field -> includedFieldNames.contains(field.getName()))
            .forEach(field -> contractArgs.put(field, MapContract.MAP));

        return this;
    }

    public ModelContractBuilder excludeFieldNames(String... fields) {
        Set<String> excludedFieldNames = Set.of(fields);
        List<Field> allFields = FieldUtils.getAllFieldsList(modelClass);

        allFields.stream()
            .filter(field -> excludedFieldNames.contains(field.getName()))
            .forEach(field -> contractArgs.put(field, MapContract.NOT_MAP));

        return this;
    }

    public ModelContractBuilder includePartialFieldNames(String... fields) {
        Set<String> partialFieldNames = Set.of(fields);
        List<Field> allFields = FieldUtils.getAllFieldsList(modelClass);

        allFields.stream()
            .filter(field -> partialFieldNames.contains(field.getName()))
            .forEach(field -> contractArgs.put(field, MapContract.PARTIAL_MAP));

        return this;
    }

    public ModelContract build() {
        return () -> (contractArgs);
    }

}
