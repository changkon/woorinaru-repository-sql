//package woorinaru.repository.sql.dao.impl;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import woorinaru.core.dao.spi.EventDao;
//import woorinaru.core.model.management.administration.Event;
//import woorinaru.repository.sql.mapping.model.factory.ModelMapperFactory;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Optional;
//
//import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;
//
//public class EventDaoImpl implements EventDao {
//
//    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);
//
//    @Override
//    public void create(Event event) {
//        LOGGER.debug("Creating an event resource");
//
//        // Map file
//        ModelMapperFactory factory = new ModelMapperFactory();
//
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        em.persist(event);
//        em.getTransaction().commit();
//        // Close connection
//        em.close();
//    }
//
//    @Override
//    public Optional<Event> get(int i) {
//        EntityManager em = getEntityManager();
//        Event event = em.find(Event.class, i);
//        em.close();
//        return Optional.ofNullable(event);
//    }
//
//    @Override
//    public void delete(Event event) {
//        EntityManager em = getEntityManager();
//        Event deleteEvent = em.find(Event.class, event.getId());
//        em.getTransaction().begin();
//        em.remove(deleteEvent);
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    @Override
//    public void modify(Event event) {
//        // TODO
//    }
//
//    @Override
//    public List<Event> getAll() {
//        EntityManager em = getEntityManager();
////        TypedQuery<Event>
////        List<Event> events = ;
//        return null;
//    }
//}
