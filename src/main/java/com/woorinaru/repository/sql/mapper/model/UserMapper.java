package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.user.Admin;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    default User mapToEntity(com.woorinaru.core.model.user.User userModel) {
        if (userModel instanceof com.woorinaru.core.model.user.Admin) {
            return AdminMapper.MAPPER.mapToEntity((com.woorinaru.core.model.user.Admin) userModel);
        } else if (userModel instanceof com.woorinaru.core.model.user.Staff) {
            return StaffMapper.MAPPER.mapToEntity((com.woorinaru.core.model.user.Staff) userModel);
        } else if (userModel instanceof com.woorinaru.core.model.user.Student) {
            return StudentMapper.MAPPER.mapToEntity((com.woorinaru.core.model.user.Student) userModel);
        }

        return null;
    }

    default com.woorinaru.core.model.user.User mapToModel(User userEntity) {
        if (userEntity instanceof Admin) {
            return AdminMapper.MAPPER.mapToModel((Admin) userEntity);
        } else if (userEntity instanceof Staff) {
            return StaffMapper.MAPPER.mapToModel((Staff) userEntity);
        } else if (userEntity instanceof Student) {
            return StudentMapper.MAPPER.mapToModel((Student) userEntity);
        }

        return null;
    }

}
