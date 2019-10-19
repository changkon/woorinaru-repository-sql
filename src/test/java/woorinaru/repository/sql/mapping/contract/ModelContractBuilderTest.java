package woorinaru.repository.sql.mapping.contract;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelContractBuilderTest {

    @Test
    public void testAllFieldsMappedModelContract() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .all()
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(4);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("name"))).isEqualTo(MapContract.MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("collection"))).isEqualTo(MapContract.MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("dateTime"))).isEqualTo(MapContract.MAP);
    }

    @Test
    public void testNoFieldsMappedModelContract() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .none()
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(4);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("name"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("collection"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("dateTime"))).isEqualTo(MapContract.NOT_MAP);
    }

    @Test
    public void testIncludeMappedFieldsModelContract() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .includeFieldNames("id", "dateTime")
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(2);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("dateTime"))).isEqualTo(MapContract.MAP);
    }

    @Test
    public void testExcludeMappedFieldsModelContract() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .excludeFieldNames("id", "collection", "dateTime")
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(3);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("collection"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("dateTime"))).isEqualTo(MapContract.NOT_MAP);
    }

    @Test
    public void testPartialMappedFieldsModelContract() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .includePartialFieldNames("id", "name")
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(2);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.PARTIAL_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("name"))).isEqualTo(MapContract.PARTIAL_MAP);
    }

    @Test
    public void testModelContractBuilderBuild() throws NoSuchFieldException {
        // GIVEN
        // WHEN
        ModelContract contract = new ModelContractBuilder(MockClass.class)
            .all()
            .excludeFieldNames("id")
            .includePartialFieldNames("name", "dateTime")
            .build();

        // THEN
        Map<Field, MapContract> contractDetails = contract.getContract();
        assertThat(contractDetails).hasSize(4);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("id"))).isEqualTo(MapContract.NOT_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("name"))).isEqualTo(MapContract.PARTIAL_MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("collection"))).isEqualTo(MapContract.MAP);
        assertThat(contractDetails.get(MockClass.class.getDeclaredField("dateTime"))).isEqualTo(MapContract.PARTIAL_MAP);
    }

    private static class MockClass {
        private int id;
        private String name;
        private Collection<String> collection;
        private LocalDateTime dateTime;
    }

}
