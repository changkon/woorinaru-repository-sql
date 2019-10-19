package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.Event;

public class EventCopy implements Copy<Event> {

    @Override
    public void copy(Event src, Event dest) {
        dest.setId(src.getId());
        dest.setDescription(src.getDescription());
        dest.setStartDateTime(src.getStartDateTime());
        dest.setEndDateTime(src.getEndDateTime());
        dest.setAddress(src.getAddress());
        dest.setStudentReservations(src.getStudentReservations());
        dest.setWooriClasses(src.getWooriClasses());
    }
}
