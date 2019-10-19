package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.Team;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class TeamMapper implements ModelMapper<woorinaru.core.model.management.administration.Team, Team> {
    @Override
    public Team mapToEntity(woorinaru.core.model.management.administration.Team model) {
        switch (model) {
            case DESIGN:
                return Team.DESIGN;
            case EDUCATION:
                return Team.EDUCATION;
            case MEDIA:
                return Team.MEDIA;
            case PLANNING:
                return Team.PLANNING;
        }
        // Not possible
        return null;
    }

    @Override
    public woorinaru.core.model.management.administration.Team mapToModel(Team entity) {
        switch (entity) {
            case DESIGN:
                return woorinaru.core.model.management.administration.Team.DESIGN;
            case EDUCATION:
                return woorinaru.core.model.management.administration.Team.EDUCATION;
            case MEDIA:
                return woorinaru.core.model.management.administration.Team.MEDIA;
            case PLANNING:
                return woorinaru.core.model.management.administration.Team.PLANNING;
        }
        // Not possible
        return null;
    }

    @Override
    public Class<Team> getEntityClass() {
        return Team.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.Team> getModelClass() {
        return woorinaru.core.model.management.administration.Team.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        return true;
    }
}
