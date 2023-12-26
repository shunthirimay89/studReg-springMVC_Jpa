package base.controllers;

import base.daos.UserDao;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @GetMapping("/userReg")
    public String userReg(Model model) {
        User user = new User();
        String id = userDao.getLatestUserId();
        user.setId(id);
        System.out.println("id" + id);
        model.addAttribute("user", user);
        return "user/userReg";
    }

    @PostMapping("/userReg")
    public String userRegForm(Model model, @ModelAttribute("user") @Validated User user, BindingResult bs, RedirectAttributes redirect) {
        if (bs.hasErrors()) {
            model.addAttribute("regFail", "Validating error");
            return "user/userReg";
        }
        String email = null;
        User user1 = userDao.getUserByEmail(user.getEmail());
        if (user1 != null) {
            model.addAttribute("existError", "This email is already exists.Try another one");
            return "user/userReg";
        }
        int result = userDao.insertUser(user);
        if (result == 0) {
            return "user/userReg";
        } else {
            redirect.addFlashAttribute("Reg", "Successfully Register!Now you can sign in to your account! ");
            return "redirect:/";
        }
    }

    @GetMapping("/userView")
    public String userView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);
        return "user/userView";
    }

    @GetMapping("/userUpdate")
    public String userUpdate(Model model, @RequestParam String id) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        return "user/userUpdate";
    }

    @PostMapping("/userUpdate")
    public String userUpdateForm(@ModelAttribute("user") User user, Model model, RedirectAttributes redirect, HttpSession session) {
        int result = userDao.updateUser(user);
        if (user.getRole().equals("Admin")) {
            session.setAttribute("admin", user);
        } else {

            session.setAttribute("user", user);
        }
        if (result == 0) {
            return "user/userUpdate";
        }
        redirect.addFlashAttribute("success", "Updating info success");
        return "redirect:/userView";
    }

    @GetMapping("/userDelete")
    public String deleteUser(@RequestParam String id, RedirectAttributes redirectAttributes) {
        int result = userDao.deleteUser(id);
        return "redirect:/userView";
    }

    @PostMapping("/userSearch")
    public String searchUser(@ModelAttribute("user") User user, Model model) {
        if (!user.getId().isEmpty()) {
            List<User> users = userDao.searchUserById(user.getId());
            model.addAttribute("users", users);
            return "user/userView";
        } else if (!user.getName().isEmpty()) {
            List<User> userList = userDao.searchUserByName(user.getName());
            model.addAttribute("users", userList);
            return "user/userView";
        } else {
            return "redirect:/userView";
        }
    }

    private boolean isUserAuthenticated(HttpServletRequest request) {
        // Check if the "admin" attribute is present in the session.
        User user = (User) request.getSession().getAttribute("admin");
        return user != null;
    }

}
