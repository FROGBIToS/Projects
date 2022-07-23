package sample;

public class Password {

    private String category;
    private String name;
    private String login;
    private String password;
    private String www;

    public Password(String category, String name, String login, String password, String www) {
        this.category = category;
        this.name = name;
        this.login = login;
        this.password = password;
        this.www = www;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getWww() {
        return www;
    }
    public void setWww(String www) {
        this.www = www;
    }
    @Override
    public String toString() {
        return category + ":" + name + ":" + login + ":" + password + ":" + www;
    }
}

