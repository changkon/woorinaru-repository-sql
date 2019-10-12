package woorinaru.repository.sql.mapping.model;


import woorinaru.repository.sql.entity.user.Admin;

public class AdminMapper implements ModelMapper<woorinaru.core.model.user.Admin, Admin> {

    private UserMapper userMapper;

    public AdminMapper() {
        this(new UserMapper());
    }

    public AdminMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Admin mapToEntity(woorinaru.core.model.user.Admin model) {
        Admin adminEntity = new Admin();
        userMapper.mapUserModelToEntity().apply(model, adminEntity);
        return adminEntity;
    }

    @Override
    public woorinaru.core.model.user.Admin mapToModel(Admin entity) {
        woorinaru.core.model.user.Admin adminModel = new woorinaru.core.model.user.Admin();
        userMapper.mapEntityToUserModel().apply(adminModel, entity);
        return adminModel;
    }
}
