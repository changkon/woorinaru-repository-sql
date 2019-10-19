package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.management.administration.Term;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TermMapper implements ModelMapper<woorinaru.core.model.management.administration.Term, Term> {

    private EventMapper eventMapper;
    private StaffMapper staffMapper;

    public TermMapper() {
        this(new EventMapper(), new StaffMapper());
    }

    public TermMapper(EventMapper eventMapper, StaffMapper staffMapper) {
        this.eventMapper = eventMapper;
        this.staffMapper = staffMapper;
    }

    @Override
    public Term mapToEntity(woorinaru.core.model.management.administration.Term model) {
        Term termEntity = new Term();
        termEntity.setId(model.getId());
        termEntity.setStartDate(model.getStartDate());
        termEntity.setEndDate(model.getEndDate());
        termEntity.setTerm(model.getTerm());

        List<Event> eventEntities = model.getEvents()
            .stream()
            .map(eventMapper::mapToEntity)
            .collect(Collectors.toList());

        termEntity.setEvents(eventEntities);

        Collection<Staff> staffEntities = model.getStaffMembers()
            .stream()
            .map(staffMapper::mapToEntity)
            .collect(Collectors.toList());

        termEntity.setStaffMembers(staffEntities);

        return termEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.Term mapToModel(Term entity) {
        woorinaru.core.model.management.administration.Term termModel = new woorinaru.core.model.management.administration.Term();
        termModel.setId(entity.getId());
        termModel.setStartDate(entity.getStartDate());
        termModel.setEndDate(entity.getEndDate());
        termModel.setTerm(entity.getTerm());

        List<woorinaru.core.model.management.administration.Event> eventModels = entity.getEvents()
            .stream()
            .map(eventMapper::mapToModel)
            .collect(Collectors.toList());

        termModel.setEvents(eventModels);

        Collection<woorinaru.core.model.user.Staff> staffModels = entity.getStaffMembers()
            .stream()
            .map(staffMapper::mapToModel)
            .collect(Collectors.toList());

        termModel.setStaffMembers(staffModels);

        return termModel;
    }

    @Override
    public Class<Term> getEntityClass() {
        return Term.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.Term> getModelClass() {
        return woorinaru.core.model.management.administration.Term.class;
    }

    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.Term.class)
            .all()
            .includePartialFieldNames("events", "staffMembers")
            .build();
        return mapperContract.equals(contract);
    }
}
