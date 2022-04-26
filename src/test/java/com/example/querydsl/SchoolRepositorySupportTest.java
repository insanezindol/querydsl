package com.example.querydsl;

import com.example.querydsl.entity.School;
import com.example.querydsl.entity.Student;
import com.example.querydsl.repository.SchoolRepository;
import com.example.querydsl.repository.StudentRepository;
import com.example.querydsl.repository.support.SchoolRepositorySupport;
import com.example.querydsl.vo.StudentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SchoolRepositorySupportTest {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SchoolRepositorySupport schoolRepositorySupport;

    @Test
    void findOneByNameTest() {
        //given
        final String address = "주소3";
        final String name = "스토어3";

        School school = School.builder()
                .address(address)
                .name(name)
                .build();

        schoolRepository.save(school);

        //when
        School resultBySchool = schoolRepositorySupport.findOneByName(name);

        //then
        Assertions.assertEquals(name, resultBySchool.getName());
    }

    @Test
    @Transactional
    void findMembersByNameTest() {
        //given
        final String studentName1 = "studentName1";
        final Integer age1 = 31;

        final String studentName2 = "studentName2";
        final Integer age2 = 41;

        final String address = "주소4";
        final String name = "스토어4";

        System.out.println("test 1");
        School school = School.builder()
                .address(address)
                .name(name)
                .build();
        System.out.println("test 2");
        schoolRepository.save(school);
        System.out.println("test 3");

        System.out.println("test 4");
        Student student1 = Student.builder()
                .name(studentName1)
                .age(age1)
                .schoolId(school.getId())
                .build();
        System.out.println("test 5");

        System.out.println("test 6");
        Student student2 = Student.builder()
                .name(studentName2)
                .age(age2)
                .schoolId(school.getId())
                .build();
        System.out.println("test 7");
        studentRepository.saveAll(Arrays.asList(student1, student2));
        System.out.println("test 8");

        //when
        List<StudentVo> students = schoolRepositorySupport.findStudentsByName(name);

        //then
        assertThat(students.size()).isGreaterThan(0);
        assertThat(students.get(0).getName()).isEqualTo(studentName1);
        assertThat(students.get(1).getName()).isEqualTo(studentName2);
    }

}
