package client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    //логин
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String auth() {
        return "login";
    }

    //юзер
    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public String userProfile() {

        return "/user/profile";
    }

    //админ
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String userList() {

        return "/admin/users";
    }

    //админ как юзер
    @RequestMapping(value = "/admin/profile", method = RequestMethod.GET)
    public String adminProfile() {

        return "/admin/profile";
    }
}