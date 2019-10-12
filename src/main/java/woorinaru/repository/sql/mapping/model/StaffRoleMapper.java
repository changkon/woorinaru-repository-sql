package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.user.StaffRole;

public class StaffRoleMapper implements ModelMapper<woorinaru.core.model.user.StaffRole, StaffRole> {

    @Override
    public StaffRole mapToEntity(woorinaru.core.model.user.StaffRole model) {
        switch(model) {
            case LEADER:
                return StaffRole.LEADER;
            case VICE_LEADER:
                return StaffRole.VICE_LEADER;
            case SUB_LEADER:
                return StaffRole.SUB_LEADER;
            case TEACHER:
                return StaffRole.TEACHER;
        }
        // Not possible
        return null;
    }

    @Override
    public woorinaru.core.model.user.StaffRole mapToModel(StaffRole entity) {
        switch(entity) {
            case LEADER:
                return woorinaru.core.model.user.StaffRole.LEADER;
            case VICE_LEADER:
                return woorinaru.core.model.user.StaffRole.VICE_LEADER;
            case SUB_LEADER:
                return woorinaru.core.model.user.StaffRole.SUB_LEADER;
            case TEACHER:
                return woorinaru.core.model.user.StaffRole.TEACHER;
        }
        return null;
    }
}
