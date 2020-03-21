package client.controller;

import client.controller.helper.RestControllerHelper;
import client.controller.helper.TransportUserList;
import client.model.TransportUser;
import client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    //юзерские страницы

    //протестировано
    //из in memory authentication нет айдишника зашедшего юзера, поэтому его данные извлекаются из сервера через имя (см. 39 строку)
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser userProfile() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/user/" + userName,
                HttpMethod.GET, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }

    //протестировано
    //есть проблема: в случае редактирования, при дальнейшей отрисовке, данные в im memory authentication устаревают
    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser userUpdate(@RequestBody TransportUser updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<TransportUser> entity = new HttpEntity<>(updatedUser, headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/user",
                HttpMethod.PUT, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }

    //админские сттраницы

    //протестировано: есть проблема с десериализацией коллекции Джексоном, приходится пересылать между клиентом и сервером массив
    //аналогичная проблема у всех запросов, возвращающих листы
    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> userList() {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser[]> response = restTemplate.exchange("http://localhost:8080/admin",
                HttpMethod.GET, entity, TransportUser[].class);

        TransportUser[] userArr = response.getBody();
        List<TransportUser> userList = new ArrayList<>();

        for (TransportUser user : userArr) {
            if (Objects.equals(user, null)) {
                break;
            }
            userList.add(user);
        }

        return userList;
    }

    //протестировано
    //проблема с листами (см. userList())
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> deleteUser(@PathVariable("id") String id) {
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser[]> response = restTemplate.exchange("http://localhost:8080/admin/" + id,
                HttpMethod.DELETE, entity, TransportUser[].class);

        TransportUser[] userArr = response.getBody();
        List<TransportUser> userList = new ArrayList<>();

        for (TransportUser user : userArr) {
            if (Objects.equals(user, null)) {
                break;
            }
            userList.add(user);
        }


        return userList;
    }

    //протестировано
    //проблема с листами (см. userList())
    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> addUser(@RequestBody TransportUser newTrUser) {
        newTrUser.setPassword(passwordEncoder.encode(newTrUser.getPassword()));

        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<TransportUser> entity = new HttpEntity<>(newTrUser, headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser[]> response = restTemplate.exchange("http://localhost:8080/admin",
                HttpMethod.POST, entity, TransportUser[].class);

        TransportUser[] userArr = response.getBody();
        List<TransportUser> userList = new ArrayList<>();

        for (TransportUser user : userArr) {
            if (Objects.equals(user, null)) {
                break;
            }
            userList.add(user);
        }


        return userList;
    }

    //протестировано
    //проблема с листами (см. userList())
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TransportUser> updateUser(@RequestBody TransportUser updatedUser, @PathVariable("id") String id) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<TransportUser> entity = new HttpEntity<>(updatedUser, headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser[]> response = restTemplate.exchange("http://localhost:8080/admin/" + id,
                HttpMethod.PUT, entity, TransportUser[].class);

        TransportUser[] userArr = response.getBody();
        List<TransportUser> userList = new ArrayList<>();

        for (TransportUser user : userArr) {
            if (Objects.equals(user, null)) {
                break;
            }
            userList.add(user);
        }


        return userList;
    }

    //Профиль админа

    //протестировано
    //из in memory authentication нет айдишника зашедшего юзера, поэтому его данные извлекаются из сервера через имя (см. 211 строку)
    @RequestMapping(value = "/adminProfile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public TransportUser adminProfile() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //headers
        HttpHeaders headers = RestControllerHelper.setHeaders();

        //entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransportUser> response = restTemplate.exchange("http://localhost:8080/adminProfile/" + userName,
                HttpMethod.GET, entity, TransportUser.class);

        TransportUser user = response.getBody();

        return user;
    }
}
