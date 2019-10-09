package woorinaru.repository.sql.main;

import woorinaru.core.dao.spi.StudentDao;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.dao.impl.StudentDaoImpl;

public class TestPersistence {
    public static void main(String[] args) {
        // Create Student model
        Student student = new Student();
        student.setEmail("chang_kon@hotmail.com");
        student.setName("Chang kon Han");
        student.setFavouriteResources(null);
        student.setNationality("New Zealand");
        StudentDao studentDao = new StudentDaoImpl();
        studentDao.create(student);
    }
}
