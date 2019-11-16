package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.UserDao;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.user.Admin;
import com.woorinaru.core.model.user.Staff;
import com.woorinaru.core.model.user.Student;
import com.woorinaru.core.model.user.User;
import com.woorinaru.repository.sql.entity.management.administration.Team;
import com.woorinaru.repository.sql.entity.user.StaffRole;
import com.woorinaru.repository.sql.mapper.model.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Objects;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    private EntityManager em;

    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    private Optional<com.woorinaru.repository.sql.entity.user.User> getUser(int id) {
        TypedQuery<com.woorinaru.repository.sql.entity.user.User> query = em.createQuery("SELECT u FROM User u WHERE u.id=:id", com.woorinaru.repository.sql.entity.user.User.class)
            .setParameter("id", id);
        com.woorinaru.repository.sql.entity.user.User userEntity = query.getSingleResult();

        if (Objects.isNull(userEntity)) {
            return Optional.empty();
        }

        return Optional.of(userEntity);
    }

    @Override
    public User get(int id) {
        LOGGER.debug("Retrieving user from database with id: %d", id);
        Optional<com.woorinaru.repository.sql.entity.user.User> userEntity = getUser(id);

        if (userEntity.isEmpty()) {
            LOGGER.warn("No user found matching id: %d", id);
            throw new ResourceNotFoundException(String.format("Could not find user with id: %d", id));
        }

        UserMapper mapper = UserMapper.MAPPER;
        User userModel = mapper.mapToModel(userEntity.get());

        return userModel;
    }

    @Override
    public void modifyUserType(User user, Class<? extends User> userClass) {
        LOGGER.debug("Changing user with id %d to class type: %s", user.getId(), userClass.getSimpleName());
        Optional<com.woorinaru.repository.sql.entity.user.User> userEntity = getUser(user.getId());

        if (userEntity.isEmpty()) {
            LOGGER.warn("No user found matching id: %d", user.getId());
            throw new ResourceNotFoundException(String.format("Could not find user with id: %d", user.getId()));
        }

        Character type = getType(userClass);
        if (Objects.isNull(type)) {
            // throw error
            throw new IllegalArgumentException("Unknown entity user class");
        }

        // User is present, change class
        Query updateUserType = em.createNativeQuery("UPDATE USER SET USER_TYPE=:type WHERE id=:id")
            .setParameter("type", type)
            .setParameter("id", user.getId());
        updateUserType.executeUpdate();

        Query updateStaffDetails = em.createNativeQuery("UPDATE USER SET STAFF_ROLE=:role, TEAM=:team WHERE id=:id")
            .setParameter("id", user.getId());

        if (userClass == Staff.class) {
            updateStaffDetails
                .setParameter("role", StaffRole.TEACHER.toString().toUpperCase())
                .setParameter("team", Team.DESIGN.toString().toUpperCase());
        } else {
            updateStaffDetails
                .setParameter("role", null)
                .setParameter("team", null);
        }

        updateStaffDetails.executeUpdate();
    }

    private Character getType(Class<? extends User> userClass) {
        if (userClass == Admin.class) {
            return com.woorinaru.repository.sql.entity.user.Admin.class.getAnnotation(DiscriminatorValue.class).value().charAt(0);
        } else if (userClass == Staff.class) {
            return com.woorinaru.repository.sql.entity.user.Staff.class.getAnnotation(DiscriminatorValue.class).value().charAt(0);
        } else if (userClass == Student.class) {
            return com.woorinaru.repository.sql.entity.user.Student.class.getAnnotation(DiscriminatorValue.class).value().charAt(0);
        }

        return null;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
