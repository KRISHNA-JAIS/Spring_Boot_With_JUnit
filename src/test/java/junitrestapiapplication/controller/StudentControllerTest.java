package junitrestapiapplication.controller;

import com.example.junitrestapiapplication.controller.StudentController;
import com.example.junitrestapiapplication.entity.Student;
import com.example.junitrestapiapplication.repository.StudentRepository;
import com.example.junitrestapiapplication.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    Student student1 = new Student(1L,"KRISHNA","JAISWAL","Java");
    Student student2 = new Student(2L,"PAPPU","BHARDWAJ","SCALA");
    Student student3 = new Student(3L,"SHAKTI","JAISWAL","JAVA");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void getAllStudent_test() throws Exception {

        List<Student> studentList = new ArrayList<>(Arrays.asList(student1,student2,student3));
        Mockito.when(studentRepository.findAll()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName").value("KRISHNA"));
    }

    @Test
    public void getStudentById_test() throws Exception {
        Mockito.when(studentRepository.findById(student3.getStudentId()))
                .thenReturn(java.util.Optional.of(student3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("SHAKTI"));
    }

    @Test
    public void createStudentRecord_test() throws Exception {
        Student student4 = Student.builder()
                        .studentId(4L)
                        .firstName("GOVINDA")
                        .lastName("JAISWAL")
                        .subject("JAVA")
                        .build();

        Mockito.when(studentRepository.save(student4)).thenReturn(student4);
        String content = objectWriter.writeValueAsString(student4);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("GOVINDA"));
    }


    @Test
    public void updateStudent_test() throws Exception {
        Student student5 = Student.builder()
                .studentId(2L)
                .firstName("ANIL")
                .lastName("JAISWAL")
                .subject("JAVA")
                .build();

        Mockito.when(studentRepository.findById(student2.getStudentId()))
                .thenReturn(java.util.Optional.of(student2));
        Mockito.when(studentRepository.save(student5)).thenReturn(student5);

        String updatedContent = objectWriter.writeValueAsString(student5);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.put("/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("ANIL"));
    }

    @Test
    public void deleteStudentById_test() throws Exception {
        doNothing().when(this.studentService).deleteStudent((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/student/delete/{id}", 3L);
        MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
