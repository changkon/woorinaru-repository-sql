package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class StaffMapper implements ModelMapper<woorinaru.core.model.user.Staff, Staff> {

    private UserMapper userMapper;
    private StaffRoleMapper staffRoleMapper;
    private TeamMapper teamMapper;

    public StaffMapper() {
        this(new UserMapper(), new StaffRoleMapper(), new TeamMapper());
    }

    public StaffMapper(UserMapper userMapper, StaffRoleMapper staffRoleMapper, TeamMapper teamMapper) {
        this.userMapper = userMapper;
        this.staffRoleMapper = staffRoleMapper;
        this.teamMapper = teamMapper;
    }

    @Override
    public Staff mapToEntity(woorinaru.core.model.user.Staff model) {
        Staff staffEntity = new Staff();
        userMapper.mapUserModelToEntity().apply(model, staffEntity);
        staffEntity.setStaffRole(staffRoleMapper.mapToEntity(model.getStaffRole()));
        staffEntity.setTeam(teamMapper.mapToEntity(model.getTeam()));
        return staffEntity;
    }

    @Override
    public woorinaru.core.model.user.Staff mapToModel(Staff entity) {
        woorinaru.core.model.user.Staff staffModel = new woorinaru.core.model.user.Staff();
        userMapper.mapEntityToUserModel().apply(staffModel, entity);
        staffModel.setStaffRole(staffRoleMapper.mapToModel(entity.getStaffRole()));
        staffModel.setTeam(teamMapper.mapToModel(entity.getTeam()));
        return staffModel;
    }

    @Override
    public Class<Staff> getEntityClass() {
        return Staff.class;
    }

    @Override
    public Class<woorinaru.core.model.user.Staff> getModelClass() {
        return woorinaru.core.model.user.Staff.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.user.Staff.class)
            .all()
            .build();
        return mapperContract.equals(contract);
    }
}
