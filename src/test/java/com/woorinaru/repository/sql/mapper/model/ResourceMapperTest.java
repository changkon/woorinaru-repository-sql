package com.woorinaru.repository.sql.mapper.model;

import org.junit.jupiter.api.Test;
import com.woorinaru.repository.sql.entity.resource.Resource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceMapperTest {

    @Test
    public void testResourceModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.management.administration.Resource resourceModel = new com.woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(1);
        resourceModel.setDescription("test description");
        resourceModel.setLocation("test location");
        LocalDateTime createDateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        LocalDateTime updateDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        resourceModel.setCreateDateTime(createDateTime);
        resourceModel.setUpdateDateTime(updateDateTime);

        // WHEN
        Resource resourceEntity = ResourceMapper.MAPPER.mapToEntity(resourceModel);

        // THEN
        assertThat(resourceEntity.getId()).isEqualTo(1);
        assertThat(resourceEntity.getDescription()).isEqualTo("test description");
        assertThat(resourceEntity.getLocation()).isEqualTo("test location");
        assertThat(resourceEntity.getCreateDateTime()).isEqualTo(createDateTime);
        assertThat(resourceEntity.getUpdateDateTime()).isEqualTo(updateDateTime);
    }

    @Test
    public void testEntityToResourceModel() {
        // GIVEN
        Resource resourceEntity = new Resource();
        resourceEntity.setId(1);
        resourceEntity.setDescription("test description");
        resourceEntity.setLocation("test location");
        LocalDateTime createDateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        LocalDateTime updateDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        resourceEntity.setCreateDateTime(createDateTime);
        resourceEntity.setUpdateDateTime(updateDateTime);

        // WHEN
        com.woorinaru.core.model.management.administration.Resource resourceModel = ResourceMapper.MAPPER.mapToModel(resourceEntity);

        // THEN
        assertThat(resourceModel.getId()).isEqualTo(1);
        assertThat(resourceModel.getDescription()).isEqualTo("test description");
        assertThat(resourceModel.getLocation()).isEqualTo("test location");
        assertThat(resourceModel.getCreateDateTime()).isEqualTo(createDateTime);
        assertThat(resourceModel.getUpdateDateTime()).isEqualTo(updateDateTime);
    }

}
