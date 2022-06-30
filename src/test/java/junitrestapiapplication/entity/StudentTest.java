package junitrestapiapplication.entity;

import com.example.junitrestapiapplication.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentTest {

    @Test
    void testConstructor() {
        Student actualStudent = new Student();
        actualStudent.setFirstName("KRISHNA");
        actualStudent.setLastName("JAISWAL");
        actualStudent.setStudentId(1L);
        actualStudent.setSubject("JAVA");
        assertEquals("KRISHNA", actualStudent.getFirstName());
        assertEquals("JAISWAL", actualStudent.getLastName());
        assertEquals(1L, actualStudent.getStudentId().longValue());
        assertEquals("JAVA", actualStudent.getSubject());
    }

    @Test
    void testEquals() {
        Student student = new Student();
        student.setFirstName("SHAKTI");
        student.setLastName("JAISWAL");
        student.setStudentId(2L);
        student.setSubject("Java");
        assertNotEquals(student, null);
    }

    @Test
    void testEquals2() {
        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setStudentId(123L);
        student.setSubject("Hello from the Dreaming Spires");
        assertNotEquals(student, "Different type to Student");
    }

    @Test
    void testEquals3() {
        Student student = new Student();
        student.setFirstName("Doe");
        student.setLastName("Doe");
        student.setStudentId(123L);
        student.setSubject("Hello from the Dreaming Spires");

        Student student1 = new Student();
        student1.setFirstName("Jane");
        student1.setLastName("Doe");
        student1.setStudentId(123L);
        student1.setSubject("Hello from the Dreaming Spires");
        assertNotEquals(student, student1);
    }

}

