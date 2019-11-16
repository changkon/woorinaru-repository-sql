package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.StaffDao;
import com.woorinaru.core.dao.spi.StudentDao;
import com.woorinaru.core.dao.spi.UserDao;
import com.woorinaru.core.model.management.administration.Team;
import com.woorinaru.core.model.user.Staff;
import com.woorinaru.core.model.user.StaffRole;
import com.woorinaru.core.model.user.Student;
import com.woorinaru.repository.sql.dao.helper.DatabaseContainerRule;
import com.woorinaru.repository.sql.util.EntityManagerFactoryUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DatabaseContainerRule.class)
public class UserDaoImplIT extends AbstractContainerDatabaseIT {

    @Test
    @DisplayName("Test user type is changed to staff")
    public void testUserTypeIsChangedToStaff() {
        // GIVEN
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();

        StudentDao studentDao = new StudentDaoImpl(em1);
        UserDao userDao = new UserDaoImpl(em1);

        Student studentModel = new Student();

        executeInTransaction().accept(em1, () -> {
            int generatedId = studentDao.create(studentModel);
            assertThat(generatedId).isEqualTo(1);
            studentModel.setId(generatedId);
        });

        // WHEN
        executeInTransaction().accept(em1, () -> {
            userDao.modifyUserType(studentModel, Staff.class);
        });

        em1.close();

        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(em2);

        // THEN
        executeInTransaction().accept(em2, () -> {
            Staff staffModel = staffDao.get(1);
            assertThat(staffModel).isNotNull();
            assertThat(staffModel.getId()).isEqualTo(1);
            assertThat(staffModel.getStaffRole()).isEqualTo(StaffRole.TEACHER);
            assertThat(staffModel.getTeam()).isEqualTo(Team.DESIGN);
        });

        em2.close();
    }

    @Test
    @DisplayName("Test user type is changed to student. Staff roles are changed to null")
    public void testUserTypeIsChangedToStudent() {
        // GIVEN
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();

        StaffDao staffDao = new StaffDaoImpl(em1);
        UserDao userDao = new UserDaoImpl(em1);

        Staff staffModel = new Staff();

        executeInTransaction().accept(em1, () -> {
            int generatedId = staffDao.create(staffModel);
            assertThat(generatedId).isEqualTo(1);
            staffModel.setId(generatedId);
        });

        // WHEN
        executeInTransaction().accept(em1, () -> {
            userDao.modifyUserType(staffModel, Student.class);
        });

        em1.close();

        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        StudentDao studentDao = new StudentDaoImpl(em2);

        // THEN
        executeInTransaction().accept(em2, () -> {
            Student studentModel = studentDao.get(1);
            assertThat(studentModel).isNotNull();
            assertThat(studentModel.getId()).isEqualTo(1);
        });

        executeInTransaction().accept(em2, () -> {
            List<Object[]> list = em2.createNativeQuery("SELECT STAFF_ROLE, TEAM FROM USER").getResultList();
            assertThat(list).hasSize(1);
            Object[] user = list.get(0);
            assertThat(user).hasSize(2);
            assertThat(user[0]).isNull();
            assertThat(user[1]).isNull();
        });

        em2.close();
    }

}
