package client.controller;

import client.controller.helper.RestControllerHelper;
import client.controller.helper.TransportUserList;
import client.model.TransportUser;
import client.model.User;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    //юзерские страницы
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser userProfile() {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/user",
                HttpMethod.GET, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser userUpdate(@RequestBody TransportUser updatedUser) {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/user",
                HttpMethod.PUT, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }

    //админские сттраницы
    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> userList() {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUserList> response = restTemplate.exchange("http://localhost:8080/admin",
                HttpMethod.GET, entity, TransportUserList.class);

        List<TransportUser> userList = response.getBody();

        return userList;
    }

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> deleteUser(@PathVariable("id") String id) {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUserList> response = restTemplate.exchange("http://localhost:8080/admin{" + id + "}",
                HttpMethod.DELETE, entity, TransportUserList.class);

        List<TransportUser> userList = response.getBody();

        return userList;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> addUser(@RequestBody TransportUser newTrUser) {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<TransportUser> entity = new HttpEntity<>(newTrUser, headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUserList> response = restTemplate.exchange("http://localhost:8080/admin",
                HttpMethod.POST, entity, TransportUserList.class);

        List<TransportUser> userList = response.getBody();

        return userList;
    }

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> updateUser(@RequestBody TransportUser updatedUser, @PathVariable("id") String id) {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<TransportUser> entity = new HttpEntity<>(updatedUser, headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUserList> response = restTemplate.exchange("http://localhost:8080/admin{" + id + "}",
                HttpMethod.PUT, entity, TransportUserList.class);

        List<TransportUser> userList = response.getBody();

        return userList;
    }

    //Профиль админа
    @RequestMapping(value = "/adminProfile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser adminProfile() {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/adminProfile",
                HttpMethod.GET, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }
}
