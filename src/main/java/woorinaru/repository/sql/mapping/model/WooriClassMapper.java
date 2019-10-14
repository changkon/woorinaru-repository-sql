package woorinaru.repository.sql.mapping.model;

import woorinaru.core.model.management.administration.WooriClass;
import woorinaru.core.model.user.User;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class WooriClassMapper {

    private ResourceMapper resourceMapper;
    private StudentMapper studentMapper;
    private StaffMapper staffMapper;

    public WooriClassMapper() {
        this(new ResourceMapper(), new StudentMapper(), new StaffMapper());
    }

    public WooriClassMapper(ResourceMapper resourceMapper, StudentMapper studentMapper, StaffMapper staffMapper) {
        this.resourceMapper = resourceMapper;
        this.studentMapper = studentMapper;
        this.staffMapper = staffMapper;
    }

    public BiFunction<WooriClass, woorinaru.repository.sql.entity.management.administration.WooriClass, woorinaru.repository.sql.entity.management.administration.WooriClass> mapWooriClassModelToEntity() {
        return (model, entity) -> {
            entity.setId(model.getId());

            Collection<Resource> resourceEntities = Optional.ofNullable(model.getResources())
                .orElse(List.of())
                .stream()
                .map(resourceMapper::mapToEntity)
                .collect(Collectors.toList());
            Collection<Student> studentEntities = Optional.ofNullable(model.getStudents())
                .orElse(List.of())
                .stream()
                .map(studentMapper::mapToEntity)
                .collect(Collectors.toList());
            Collection<Staff> staffEntities = Optional.ofNullable(model.getStaff())
                .orElse(List.of())
                .stream()
                .map(staffMapper::mapToEntity)
                .collect(Collectors.toList());

            entity.setResources(resourceEntities);
            entity.setStudents(studentEntities);
            entity.setStaff(staffEntities);

            return entity;
        };
    }

    public BiFunction<WooriClass, woorinaru.repository.sql.entity.management.administration.WooriClass, WooriClass> mapEntityToWooriClassModel() {
        return (model, entity) -> {

            model.setId(entity.getId());
            Collection<woorinaru.core.model.management.administration.Resource> resourceModels = Optional.ofNullable(entity.getResources())
                .orElse(List.of())
                .stream()
                .map(resourceMapper::mapToModel)
                .collect(Collectors.toList());
            Collection<woorinaru.core.model.user.Student> studentModels = Optional.ofNullable(entity.getStudents())
                .orElse(List.of())
                .stream()
                .map(studentMapper::mapToModel)
                .collect(Collectors.toList());
            Collection<woorinaru.core.model.user.Staff> staffModels = Optional.ofNullable(entity.getStaff())
                .orElse(List.of())
                .stream()
                .map(staffMapper::mapToModel)
                .collect(Collectors.toList());

            model.setResources(resourceModels);
            model.setStudents(studentModels);
            model.setStaff(staffModels);

            return model;
        };
    }

}
