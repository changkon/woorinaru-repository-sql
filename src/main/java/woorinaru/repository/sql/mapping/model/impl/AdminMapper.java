package woorinaru.repository.sql.mapping.model.impl;


import woorinaru.repository.sql.entity.user.Admin;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

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

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public Class<woorinaru.core.model.user.Admin> getModelClass() {
        return woorinaru.core.model.user.Admin.class;
    }

    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.user.Admin.class).all().build();
        return mapperContract.equals(contract);
    }
}
