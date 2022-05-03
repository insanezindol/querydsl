package com.example.querydsl;

import com.example.querydsl.entity.School;
import com.example.querydsl.entity.Student;
import com.example.querydsl.repository.SchoolRepository;
import com.example.querydsl.repository.StudentRepository;
import com.example.querydsl.service.TestService;
import com.example.querydsl.vo.StudentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    TestService testService;

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
        School resultBySchool = schoolRepository.findOneByName(name);

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
        List<StudentVo> students = schoolRepository.findStudentsByName(name);

        //then
        assertThat(students.size()).isGreaterThan(0);
        assertThat(students.get(0).getName()).isEqualTo(studentName1);
        assertThat(students.get(1).getName()).isEqualTo(studentName2);
    }

    // given
    private void setupStudents() {
        final String name = "학생이름";

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = Student.builder()
                    .name(name + i)
                    .age(i + 10)
                    .build();
            students.add(student);
        }
        studentRepository.saveAll(students);
    }

    @Test
    @DisplayName("등록된 학생 페이징 조회 테스트")
    void findPagingStudentsByNameTest() {
        //given
        this.setupStudents();
        final int page = 0; // 페이지 번호 : 0부터 시작
        final int size = 5; // 페이지당 컨텐츠 수

        Sort.Order order = Sort.Order.desc("id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sort);

        //when
        PageImpl<Student> result = studentRepository.findStudentsByNamePaging("학생", pageable);

        System.out.println("[logging] result.getTotalElements() : " + result.getTotalElements()); // 조회된 전체 건수
        System.out.println("[logging] result.getNumber() : " + result.getNumber()); // 현재 페이지 번호
        System.out.println("[logging] result.getTotalPages() : " + result.getTotalPages()); // 전체 페이지 숫자
        System.out.println("[logging] result.getContent().size() : " + result.getContent().size()); // 현재 페이지 컨텐츠 숫자

        List<Student> studentList = result.getContent();
        for (Student student : studentList) {
            System.out.println("[logging] student info : [" + student.getId() + "] " + student.getName());
        }

        //then
        assertThat(result.getNumber()).isEqualTo(page);
        assertThat(result.getTotalPages()).isEqualTo(20);
        assertThat(result.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Dirty Checking Update")
    void updateDirtyChecking() {
        //given
        this.setupStudents();
        final String changedName = "변경이름1";

        //when
        testService.dirtyCheckingUpdate(changedName);
        Student student = studentRepository.findById(1L).orElse(null);

        //then
        assertThat(student.getName()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("Bulk Update")
    void updateBulk() {
        //given
        this.setupStudents();
        final String oldName = "학생이름";
        final String newName = "변경이름";

        //when
        long resultCnt = testService.bulkUpdate(oldName, newName);
        System.out.println("업데이트 된 데이터 건수 : " + resultCnt);
        Student student = studentRepository.findById(1L).orElse(null);

        //then
        assertThat(student.getName()).isEqualTo(newName);
    }


}
