package base.controllers;


import base.daos.CourseDao;
import base.models.Course;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    CourseDao courseDao;

    @GetMapping("/courseReg")
    public String courseRegister(Model model, HttpServletRequest request) {

        if (!isUserAuthenticated(request)) {
            // User is not authenticated. Redirect to the login page.
            return "error";
        }

        Course course = new Course();
        String id = courseDao.getLatestCourseId();
        course.setId(id);
        System.out.println("Course Id  " + id);
        model.addAttribute("course", course);
        return "course/courseReg";
    }

    @PostMapping("/courseReg")
    public String courseRegisterForm(@ModelAttribute("course") @Validated Course course, BindingResult bs, Model model) {
        if (bs.hasErrors()) {
            System.out.println("Binding Error");
            return "course/courseReg";
        }
        String name = course.getCourseName();
        Course course1 = courseDao.getCourseByName(name);
        if (course1 != null) {
            model.addAttribute("error", "This course is already exists");
            return "course/courseReg";
        }
        int result = courseDao.insertCourse(course);
        if (result == 0) {
            System.out.println("Insert Fail");
            model.addAttribute("error", "Inserting course is failed");
            return "course/courseReg";
        }
        return "redirect:/courseView";
    }


    @GetMapping("/courseView")
    public String courseView(Model model, HttpServletRequest request) {

        if (!isUserAuthenticated(request)) {
            // User is not authenticated. Redirect to the login page.
            return "error";
        }

        Course course = new Course();
        model.addAttribute("course", course);
        List<Course> courseList = courseDao.getAllCourses();
        model.addAttribute("courseList", courseList);
        return "course/courseView";
    }

    @GetMapping("/courseUpdate")
    public String courseUpdate(@RequestParam String id, Model model) {
        Course course = courseDao.getCourseById(id);
        model.addAttribute("course", course);
        return "course/courseUpdate";
    }

    @PostMapping("/courseUpdate")
    public String courseUpdateForm(@ModelAttribute("course") Course course, Model model, RedirectAttributes redirect) {
        int result = courseDao.updateCourse(course);
        if (result == 0) {
            model.addAttribute("updError", "Updating course info is failed");
            return "course/courseUpdate";
        }
        redirect.addFlashAttribute("updSuccess", "Updating course info success");
        return "redirect:/courseView";
    }

    @GetMapping("/courseDelete")
    public String deleteCourse(@RequestParam String id, Model model, RedirectAttributes redirect) {
        int result = courseDao.deleteCourse(id);
        if (result == 0) {
            return "course/courseView";
        }
        return "redirect:/courseView";
    }

    @PostMapping("/courseSearch")
    public String courseSearch(@ModelAttribute("course") Course course, Model model) {
        if (!course.getId().isEmpty()) {
            List<Course> courses = courseDao.searchCourseById(course.getId());
            model.addAttribute("courseList", courses);
            return "course/courseView";
        } else if (!course.getCourseName().isEmpty()) {
            List<Course> courses1 = courseDao.searchCourseByName(course.getCourseName());
            model.addAttribute("courseList", courses1);
            return "course/courseView";
        } else {
            return "redirect:/courseView";
        }
    }

    private boolean isUserAuthenticated(HttpServletRequest request) {
        // Check if the "admin" attribute is present in the session.
        User user = (User) request.getSession().getAttribute("admin");
        return user != null;
    }

}
