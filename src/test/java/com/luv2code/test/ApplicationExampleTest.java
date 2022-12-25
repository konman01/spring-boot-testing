package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

    public static int count = 0;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach(){
        count = count + 1;
        System.out.println("Testing: " + appName + " which is " + appDescription +
                " version: " + appVersion + ".Execution of test method  "+count);

        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 92.0, 78.0)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    void basicTest(){

    }

    @Test
    @DisplayName("Add grade result for student grades")
    public void addGradeResultForStudentGrades(){
        assertEquals(355.0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));
    }

    @Test
    @DisplayName("Add grade result for student grades not Equals")
    public void addGradeResultForStudentGradesNotEquals(){
        assertNotEquals(354.0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));
    }

    @Test
    @DisplayName("is Grade Greater")
    public void isGradeGreaterStudentGrades(){
        assertTrue(studentGrades.isGradeGreater(90, 75));
    }

    @Test
    @DisplayName("is Grade Greater False")
    public void isGradeGreaterStudentGradesFalse(){
        assertFalse(studentGrades.isGradeGreater(89, 92));
    }

    @Test
    @DisplayName("Check Null For Student Grades")
    public void checkNullForStudentGrades(){
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Create Student with grade init")
    public void createStudentWithoutGradeInit(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Chad");
        studentTwo.setLastname("Darby");
        studentTwo.setEmailAddress("chad.darby@gmail.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentTwo.getStudentGrades());
    }

    @Test
    @DisplayName("Verify Students are prototype")
    public void verifyStudentsArePrototype(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(studentTwo, student);

    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage(){
        assertAll(()->assertEquals(355.0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        )),
                ()->assertEquals(88.75, studentGrades.findGradePointAverage(
                        student.getStudentGrades().getMathGradeResults()
                )));
    }
}
