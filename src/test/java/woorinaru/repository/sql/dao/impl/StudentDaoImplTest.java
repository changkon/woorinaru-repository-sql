package woorinaru.repository.sql.dao.impl;

import org.junit.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import woorinaru.repository.sql.entity.user.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class StudentDaoImplTest extends AbstractContainerDatabaseTest {

    @Container
    public MySQLContainer mysql = (MySQLContainer) new MySQLContainer()
        .withInitScript("META-INF/sql/woorinaru_create.sql");

    @Test
    @DisplayName("Simple init test for MySQL")
    public void simpleQueryTest() throws SQLException {
        Optional<ResultSet> resultSet = performQuery(mysql, "SELECT * FROM USER");
        int size = 0;
        if (resultSet.isPresent()) {
            ResultSet rs =  resultSet.get();
            rs.next();
            rs.last();
            size = rs.getRow();
        }
        assertThat(size).isEqualTo(0);

        performQuery(mysql, "INSERT INTO USER VALUES(NULL, 'Changkon', 'New Zealand', 'chang_kon@hotmail.com', NULL, NULL, 'S')");
        resultSet = performQuery(mysql, "SELECT * FROM USER");

        Student student = new Student();
        if (resultSet.isPresent()) {
            ResultSet rs = resultSet.get();
            rs.next();
            student.setId(rs.getInt("ID"));
            student.setEmail(rs.getString("EMAIL"));
            student.setName(rs.getString("NAME"));
            student.setNationality(rs.getString("NATIONALITY"));
        }

        assertThat(student.getEmail()).isEqualTo("chang_kon@hotmail.com");
        assertThat(student.getName()).isEqualTo("Changkon");
        assertThat(student.getNationality()).isEqualTo("New Zealand");
    }
}
