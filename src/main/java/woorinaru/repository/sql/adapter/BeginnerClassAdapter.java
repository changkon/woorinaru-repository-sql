package woorinaru.repository.sql.adapter;

import org.mapstruct.factory.Mappers;
import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.mapping.model.PartialResourceMapper;

import java.util.Collection;
import java.util.stream.Collectors;

public class BeginnerClassAdapter extends BeginnerClass {

    private woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity;

    public BeginnerClassAdapter(woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity) {
        this.beginnerClassEntity = beginnerClassEntity;
    }

    @Override
    public Grade getGrade() {
        return super.getGrade();
    }

    @Override
    public Collection<Resource> getResources() {
        PartialResourceMapper mapper = Mappers.getMapper(PartialResourceMapper.class);
        return this.beginnerClassEntity.getResources()
            .stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Staff> getStaff() {
        return super.getStaff();
    }

    @Override
    public Collection<Student> getStudents() {
        return super.getStudents();
    }

    @Override
    public void setResources(Collection<Resource> resources) {
        super.setResources(resources);
    }

    @Override
    public void setStaff(Collection<Staff> staff) {
        super.setStaff(staff);
    }

    @Override
    public void setStudents(Collection<Student> students) {
        super.setStudents(students);
    }

    @Override
    public Event getEvent() {
        return super.getEvent();
    }

    @Override
    public void setEvent(Event event) {
        super.setEvent(event);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }
}
