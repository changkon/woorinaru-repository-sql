package woorinaru.repository.sql.dao.impl;

import woorinaru.core.dao.spi.EventDao;
import woorinaru.core.model.management.administration.Event;

import java.util.List;
import java.util.Optional;

public class EventDaoImpl implements EventDao {
    @Override
    public void create(Event event) {

    }

    @Override
    public Optional<Event> get(int i) {
        return Optional.empty();
    }

    @Override
    public void delete(Event event) {

    }

    @Override
    public void modify(Event event) {

    }

    @Override
    public List<Event> getAll() {
        return null;
    }
}
