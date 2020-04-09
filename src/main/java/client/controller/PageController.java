package client.controller;

import client.controller.helper.RestControllerHelper;
import client.model.TransportUser;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class PageController {

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public AuthenticationManager authManager;

    //вход через google
    @RequestMapping(value = "/login/tokensignin", method = RequestMethod.POST)
    public String googleSignIn(@RequestBody String idTokenString) throws Exception {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("760838010289-betu9j9su1m184i77k2pl52pr6mol0nr.apps.googleusercontent.com"))
                .build();

        int endIndex = idTokenString.length() - 1;
        idTokenString = idTokenString.substring(1, endIndex);

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String email = (String) payload.getEmail();

            //создание нового юзера в базе

            Set<String> roles = new HashSet<>();
            roles.add("ROLE_user");
            TransportUser newUser = new TransportUser(999,"empty", email, "12345", roles);

            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            //headers
            HttpHeaders headers = RestControllerHelper.setHeaders();

            //entity
            HttpEntity<TransportUser> entity = new HttpEntity<>(newUser, headers);

            //restTemplate
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TransportUser[]> response = restTemplate.exchange("http://localhost:8080/admin",
                    HttpMethod.POST, entity, TransportUser[].class);


            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(newUser.getLogin(), newUser.getPassword());
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);


            return "redirect:/user/home";
        }

        throw new Exception();
    }

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