public class User {
    private String login;
    private String password;
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(User user) {
        if (user != null) {
            this.login = user.login;
            this.password = user.password;
            this.role = user.role;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Пользователь: " + login + ", роль: " + role;
    }
}
