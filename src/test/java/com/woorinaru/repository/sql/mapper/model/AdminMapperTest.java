package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.entity.user.Admin;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminMapperTest {

    @Test
    public void testAdminModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.user.Admin adminModel = new com.woorinaru.core.model.user.Admin();
        adminModel.setId(1);
        adminModel.setName("Test Admin Name");
        adminModel.setEmail("test email");
        adminModel.setNationality("New Zealand");
        adminModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        adminModel.setFavouriteResources(Collections.emptyList());
        LocalDateTime createDateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        LocalDateTime updateDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        adminModel.setCreateDateTime(createDateTime);
        adminModel.setUpdateDateTime(updateDateTime);

        // WHEN
        AdminMapper mapper = AdminMapper.MAPPER;
        Admin adminEntity = mapper.mapToEntity(adminModel);

        // THEN
        assertThat(adminEntity.getId()).isEqualTo(1);
        assertThat(adminEntity.getName()).isEqualTo("Test Admin Name");
        assertThat(adminEntity.getEmail()).isEqualTo("test email");
        assertThat(adminEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(adminEntity.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(adminEntity.getFavouriteResources()).isNullOrEmpty();
        assertThat(adminEntity.getCreateDateTime()).isEqualTo(createDateTime);
        assertThat(adminEntity.getUpdateDateTime()).isEqualTo(updateDateTime);
    }

    @Test
    public void testAdminEntityToModel() {
        // GIVEN
        Admin adminEntity = new Admin();
        adminEntity.setId(1);
        adminEntity.setName("Test Admin Name");
        adminEntity.setEmail("test email");
        adminEntity.setNationality("New Zealand");
        adminEntity.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        adminEntity.setFavouriteResources(Collections.emptyList());
        LocalDateTime createDateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        LocalDateTime updateDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        adminEntity.setCreateDateTime(createDateTime);
        adminEntity.setUpdateDateTime(updateDateTime);

        // WHEN
        AdminMapper mapper = AdminMapper.MAPPER;
        com.woorinaru.core.model.user.Admin adminModel = mapper.mapToModel(adminEntity);

        // THEN
        assertThat(adminModel.getId()).isEqualTo(1);
        assertThat(adminModel.getName()).isEqualTo("Test Admin Name");
        assertThat(adminModel.getEmail()).isEqualTo("test email");
        assertThat(adminModel.getNationality()).isEqualTo("New Zealand");
        assertThat(adminModel.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(adminModel.getFavouriteResources()).isEmpty();
        assertThat(adminModel.getCreateDateTime()).isEqualTo(createDateTime);
        assertThat(adminModel.getUpdateDateTime()).isEqualTo(updateDateTime);
    }

    @Test
    public void testAdminModelToEntityWithPartialResources() {
        // GIVEN
        com.woorinaru.core.model.user.Admin adminModel = new com.woorinaru.core.model.user.Admin();
        adminModel.setId(1);
        adminModel.setName("Test Admin Name");
        adminModel.setEmail("test email");
        adminModel.setNationality("New Zealand");
        adminModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));

        com.woorinaru.core.model.management.administration.Resource resourceModel1 = new com.woorinaru.core.model.management.administration.Resource();
        resourceModel1.setId(1);
        resourceModel1.setDescription("test resource description 1");
        resourceModel1.setLocation("test resource location 1");

        com.woorinaru.core.model.management.administration.Resource resourceModel2 = new com.woorinaru.core.model.management.administration.Resource();
        resourceModel2.setId(2);
        resourceModel2.setDescription("test resource description 2");
        resourceModel2.setLocation("test resource location 2");

        adminModel.setFavouriteResources(List.of(resourceModel1, resourceModel2));

        // WHEN
        AdminMapper mapper = AdminMapper.MAPPER;
        Admin adminEntity = mapper.mapToEntity(adminModel);

        // THEN
        assertThat(adminEntity.getId()).isEqualTo(1);
        assertThat(adminEntity.getName()).isEqualTo("Test Admin Name");
        assertThat(adminEntity.getEmail()).isEqualTo("test email");
        assertThat(adminEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(adminEntity.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(adminEntity.getFavouriteResources()).isNotEmpty();

        Iterator<Resource> iter = adminEntity.getFavouriteResources().iterator();

        Resource resourceEntity1 = iter.next();
        assertThat(resourceEntity1.getId()).isEqualTo(1);
        assertThat(resourceEntity1.getDescription()).isEqualTo("test resource description 1");
        assertThat(resourceEntity1.getLocation()).isEqualTo("test resource location 1");

        Resource resourceEntity2 = iter.next();
        assertThat(resourceEntity2.getId()).isEqualTo(2);
        assertThat(resourceEntity2.getDescription()).isEqualTo("test resource description 2");
        assertThat(resourceEntity2.getLocation()).isEqualTo("test resource location 2");
    }

    @Test
    public void testAdminEntityToModelWithPartialResources() {
        // GIVEN
        Admin adminEntity = new Admin();
        adminEntity.setId(1);
        adminEntity.setName("Test Admin Name");
        adminEntity.setEmail("test email");
        adminEntity.setNationality("New Zealand");
        adminEntity.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));

        Resource resourceEntity1 = new Resource();
        resourceEntity1.setId(1);
        resourceEntity1.setDescription("test resource description 1");
        resourceEntity1.setLocation("test resource location 1");

        Resource resourceEntity2 = new Resource();
        resourceEntity2.setId(2);
        resourceEntity2.setDescription("test resource description 2");
        resourceEntity2.setLocation("test resource location 2");

        adminEntity.setFavouriteResources(List.of(resourceEntity1, resourceEntity2));

        // WHEN
        AdminMapper mapper = AdminMapper.MAPPER;
        com.woorinaru.core.model.user.Admin adminModel = mapper.mapToModel(adminEntity);

        // THEN
        assertThat(adminModel.getId()).isEqualTo(1);
        assertThat(adminModel.getName()).isEqualTo("Test Admin Name");
        assertThat(adminModel.getEmail()).isEqualTo("test email");
        assertThat(adminModel.getNationality()).isEqualTo("New Zealand");
        assertThat(adminModel.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(adminModel.getFavouriteResources()).hasSize(2);

        Iterator<com.woorinaru.core.model.management.administration.Resource> iter = adminModel.getFavouriteResources().iterator();

        com.woorinaru.core.model.management.administration.Resource resourceModel1 = iter.next();
        assertThat(resourceModel1.getId()).isEqualTo(1);
        assertThat(resourceModel1.getDescription()).isEqualTo("test resource description 1");
        assertThat(resourceModel1.getLocation()).isEqualTo("test resource location 1");

        com.woorinaru.core.model.management.administration.Resource resourceModel2 = iter.next();
        assertThat(resourceModel2.getId()).isEqualTo(2);
        assertThat(resourceModel2.getDescription()).isEqualTo("test resource description 2");
        assertThat(resourceModel2.getLocation()).isEqualTo("test resource location 2");
    }

}
