package woorinaru.repository.sql.mapping.model;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.resource.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceMapperTest {

    @Test
    public void testResourceModelToEntity() {
        // GIVEN
        woorinaru.core.model.management.administration.Resource resourceModel = new woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(1);
        resourceModel.setDescription("test description");
        resourceModel.setResource("test resource".getBytes());

        // WHEN
        Resource resourceEntity = ResourceMapper.MAPPER.mapToEntity(resourceModel);

        // THEN
        assertThat(resourceEntity.getId()).isEqualTo(1);
        assertThat(resourceEntity.getDescription()).isEqualTo("test description");
        assertThat(resourceEntity.getResource()).isEqualTo("test resource".getBytes());
    }

    @Test
    public void testEntityToResourceModel() {
        // GIVEN
        Resource resourceEntity = new Resource();
        resourceEntity.setId(1);
        resourceEntity.setDescription("test description");
        resourceEntity.setResource("test resource".getBytes());

        // WHEN
        woorinaru.core.model.management.administration.Resource resourceModel = ResourceMapper.MAPPER.mapToModel(resourceEntity);

        // THEN
        assertThat(resourceModel.getId()).isEqualTo(1);
        assertThat(resourceModel.getDescription()).isEqualTo("test description");
        assertThat(resourceModel.getResource()).isEqualTo("test resource".getBytes());
    }

}
