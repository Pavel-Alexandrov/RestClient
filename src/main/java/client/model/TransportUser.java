package client.model;

import java.util.Set;

//Класс создан, как копия класса User,
//но более простой и с меньшим числом полей,
//поскольку не наследуются UserDetails и Authorities.
//Для передачи данных на фронт
public class TransportUser {
    private int userId;
    private String name;
    private String login;
    private String password;
    private Set<String> roles;

    public TransportUser() {}

    public TransportUser(int userId, String name, String login, String password, Set<String> roles) {
        this.userId = userId;
        this.name = name;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
