package base.controllers;

import base.daos.CourseDao;
import base.daos.StudentDao;
import base.models.Course;
import base.models.Student;
import base.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;

    @GetMapping("/studentReg")
    public String studentReg(Model model) {

        List<Course> courseList = courseDao.getAllCourses();
        model.addAttribute("courseList", courseList);
        Student student = new Student();
        String id = studentDao.getLatestStudentId();
        student.setId(id);
        model.addAttribute("student", student);
        return "student/studentReg";
    }

    @PostMapping("/studentReg")
    public String studentRegForm(@ModelAttribute("student") @Validated Student student, BindingResult bs,
                                 Model model, HttpServletRequest request) {

        System.out.println("Received student: " + student);
        if (bs.hasErrors()) {
            System.out.println("binding Error" + bs);
            model.addAttribute("bindingErrors", bs);
            return "student/studentReg";
        }
        MultipartFile file = student.getFile();
        System.out.println("file" + file);

        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && (originalFilename.endsWith(".jpg") ||
                    originalFilename.endsWith(".jpeg") || originalFilename.endsWith(".png") ||
                    originalFilename.endsWith(".gif"))) {
                String image = saveImage(file, request);
                student.setImage(image);
            } else {
                System.err.println("Uploaded file is not an allowed image type.");
                model.addAttribute("imageError", "Uploaded file is not an allowed image type.");
                return "student/studentReg";
            }
        }
        List<Course> courses = new ArrayList<>();
        List<String> courseIds = student.getCourseIds();

        for (String id : courseIds) {
            Course course = courseDao.getCourseById(id);
            courses.add(course);
        }
        student.setCourses(courses);
        int result = studentDao.insertStudent(student);
        if (result == 0) {
            return "student/studentReg";
        }
        return "redirect:/studentView";
    }

    @GetMapping("/studentDelete")
    public String studentDelete(@RequestParam String id, Model model) {
        int result = studentDao.deleteStudent(id);
        if (result == 0) {
            System.out.println("Deleting failed");
        }
        return "redirect:/studentView";
    }

    @GetMapping("/studentView")
    public String studentView(Model model, HttpServletRequest request) {
        if (!isUserAuthenticated(request)) {
            // User is not authenticated. Redirect to the login page.
            return "error";
        }

        model.addAttribute("student", new Student());
        List<Student> studentList = studentDao.getAllStudents();
        model.addAttribute("studentList", studentList);
        System.out.println(studentList);
        return "student/studentSearch";
    }

    @GetMapping("/studentDetail")
    public String studentDetail(@RequestParam String id, Model model) {

        Student student = studentDao.findStudentById(id);
        model.addAttribute("student", student);
        System.out.println("student :" + student);

        List<Course> courses = courseDao.getAllCourses();
        model.addAttribute("courses", courses);
        System.out.println(courses);

        return "student/studentDetails";
    }

    @PostMapping("/studentDetail")
    public String studentDetailForm(@ModelAttribute("student") Student student,
                                    HttpServletRequest request, Model model) {

        MultipartFile file = student.getFile();
        System.out.println("file" + file);

        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && (originalFilename.endsWith(".jpg") ||
                    originalFilename.endsWith(".jpeg") || originalFilename.endsWith(".png") ||
                    originalFilename.endsWith(".gif"))) {
                String image = saveImage(file, request);
                student.setImage(image);
            } else {
                System.err.println("Uploaded file is not an allowed image type.");
                model.addAttribute("imageError", "Uploaded file is not an allowed image type.");
                return "student/studentDetails";
            }
        }
        List<Course> courses = new ArrayList<>();
        List<String> courseIds = student.getCourseIds();
        for (String courseId : courseIds) {

            //find the course that concern with single course id
            Course course = courseDao.getCourseById(courseId);
            //add single course to courses list
            courses.add(course);
        }
        //add courses list to student object back
        student.setCourses(courses);
        int result = studentDao.updateStudent(student);
        return "redirect:/studentView";
    }

    @PostMapping("/studentSearch")
    public String studentSearch(@ModelAttribute("student") Student student, Model model, @RequestParam("courseName") String course) {
        if (!student.getId().isEmpty()) {
            List<Student> studentList = studentDao.searchStudentById(student.getId());
            model.addAttribute("studentList", studentList);
            return "student/studentSearch";
        } else if (!student.getName().isEmpty()) {
            List<Student> students = studentDao.searchStudentByName(student.getName());
            model.addAttribute("studentList", students);
            return "student/studentSearch";
        } else if (!course.isEmpty()) {
            List<Student> studList = studentDao.getStudentsByCourse(course);
            model.addAttribute("studentList", studList);
            return "student/studentSearch";
        } else {
            return "redirect:/studentView";
        }
    }

    //image save extract method
    private String saveImage(MultipartFile file, HttpServletRequest request) {

        if (file != null && !file.isEmpty()) {

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

            String image = System.currentTimeMillis() + fileExtension;

            String rootDirectory = request.getSession().getServletContext().getRealPath("/");
            Path path = Paths.get(rootDirectory + "/WEB-INF/assets/images/" + image);
            try {
                file.transferTo(new File(path.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        } else {
            return null;
        }
    }

    private boolean isUserAuthenticated(HttpServletRequest request) {
        // Check if the "admin" attribute is present in the session.
        User user = (User) request.getSession().getAttribute("admin");
        return user != null;
    }
}
