package base.controllers;

import base.daos.UserDao;
import base.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    UserDao userDao;

    @GetMapping("/")
    public String Login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/")
    public String login(@ModelAttribute("user") User user, Model model, HttpServletRequest request, RedirectAttributes red) {

        HttpSession session = request.getSession();
        String email = user.getEmail();

        User user1 = new User();
        user1 = userDao.getUserByEmail(email);
        System.out.println("user" + user1);
        if (user1 != null) {

            if (user.getPassword().equals(user1.getPassword())) {
                if (user1.getRole().equals("Admin")) {
                    session.setAttribute("admin", user1);
                    System.out.println("admin");
                } else {
                    session.setAttribute("user", user1);
                }
            } else {
                model.addAttribute("password", "passwordIncorrect");
                return "login";
            }
            model.addAttribute("loginSuccess", "Successful Login");
            return "menu";

        }
        red.addFlashAttribute("loginFail", "User not found! You have to registered First!");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirect) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("admin");
        return "redirect:/";
    }

}

