package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.management.administration.Team;

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
}
