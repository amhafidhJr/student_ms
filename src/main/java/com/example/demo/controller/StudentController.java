package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    //handler method to handle list student request
    // to return mode and view

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";

    }

    @GetMapping("/new_student")
    public String createStudent(Model model) {

        //create student object to hold student form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";

    }

    @PostMapping("/add_student")
    public  String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";

    }

    @GetMapping("/edit_student/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {

        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";

    }

    @PostMapping("/update_student/{id}")
    public String updateStudent(@PathVariable  Long id, @ModelAttribute("student") Student student, Model model) {

        //get student from db by ID

        Student existing_student = studentService.getStudentById(id);
        existing_student.setId(id);
        existing_student.setFirst_name(student.getFirst_name());
        existing_student.setLast_name(student.getLast_name());
        existing_student.setEmail(student.getEmail());

        //save updated student object

        studentService.updateStudent(existing_student);
        return "redirect:/students";

    }

    @GetMapping("/delete_student/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}
