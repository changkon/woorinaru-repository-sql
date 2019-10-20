//package woorinaru.repository.sql.dao.impl;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import woorinaru.core.dao.spi.StaffDao;
//import woorinaru.core.model.user.Staff;
//import woorinaru.repository.sql.mapping.entity.StaffCopy;
//import woorinaru.repository.sql.mapping.model.impl.StaffMapper;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;
//
//public class StaffDaoImpl implements StaffDao {
//
//    private static final Logger LOGGER = LogManager.getLogger(StaffDao.class);
//
//    @Override
//    public void create(Staff staff) {
//        LOGGER.debug("Creating a staff resource");
//        // Map file
//        StaffMapper mapper = new StaffMapper();
//        woorinaru.repository.sql.entity.user.Staff staffEntity = mapper.mapToEntity(staff);
//
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        em.persist(staffEntity);
//        em.getTransaction().commit();
//        em.close();
//
//        LOGGER.debug("Finished creating a staff resource");
//    }
//
//    @Override
//    public Optional<Staff> get(int id) {
//        LOGGER.debug("Retrieving a staff resource with id: %d", id);
//        EntityManager em = getEntityManager();
//        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, id);
//
//        LOGGER.debug("Staff with id: %d. Found: %s", id, staffEntity == null ? "True" : "False");
//
//        StaffMapper mapper = new StaffMapper();
//
//        Optional<Staff> staffModel = Stream.ofNullable(staffEntity)
//            .map(mapper::mapToModel)
//            .findFirst();
//
//        em.close();
//
//        return staffModel;
//    }
//
//    @Override
//    public void delete(Staff staff) {
//        LOGGER.debug("Deleting staff with id: %d", staff.getId());
//
//        // Map file
//        EntityManager em = getEntityManager();
//        woorinaru.repository.sql.entity.user.Staff deleteStaffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
//
//        if (deleteStaffEntity != null) {
//            em.getTransaction().begin();
//            em.remove(deleteStaffEntity);
//            em.getTransaction().commit();
//            LOGGER.debug("Staff deleted");
//        } else {
//            LOGGER.debug("Staff with id: '%d' not found. Could not be deleted", staff.getId());
//        }
//
//        em.close();
//    }
//
//    @Override
//    public void modify(Staff staff) {
//        LOGGER.debug("Modifying staff with id: %d", staff.getId());
//        StaffMapper mapper = new StaffMapper();
//        woorinaru.repository.sql.entity.user.Staff modifiedStaffEntity = mapper.mapToEntity(staff);
//
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        woorinaru.repository.sql.entity.user.Staff existingStaffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
//
//        if (existingStaffEntity != null) {
//            StaffCopy copyUtil = new StaffCopy();
//            copyUtil.copy(modifiedStaffEntity, existingStaffEntity);
//            em.getTransaction().commit();
//            LOGGER.debug("Modified staff");
//        } else {
//            LOGGER.debug("Staff with id: '%d' not found. Could not be modified", staff.getId());
//        }
//
//        em.close();
//    }
//
//    @Override
//    public List<Staff> getAll() {
//        LOGGER.debug("Retrieving all staff members");
//        StaffMapper mapper = new StaffMapper();
//
//        EntityManager em = getEntityManager();
//        TypedQuery<woorinaru.repository.sql.entity.user.Staff> query = em.createQuery("SELECT s FROM Staff s", woorinaru.repository.sql.entity.user.Staff.class);
//        List<woorinaru.repository.sql.entity.user.Staff> entityStaff = query.getResultList();
//
//        List<Staff> staff = entityStaff.stream()
//            .map(mapper::mapToModel)
//            .collect(Collectors.toList());
//
//        LOGGER.debug("Retrieved %d staff", staff.size());
//        em.close();
//        return staff;
//    }
//}