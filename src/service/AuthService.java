package service;

import java.util.List;
import java.util.UUID;

import model.User;
import util.JsonDB;

public class AuthService {
    private static final String USERS_JSON = "users.json";

    public static User login(String username, String password) {
        System.out.println("AuthService: attempting login for username='" + username + "'");
        List<User> users = JsonDB.load(USERS_JSON, User.class);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("AuthService: login successful: " + user);
                return user;
            }
        }
        System.out.println("AuthService: login failed for username='" + username + "'");
        return null;
    }

    public static boolean signUp(String username, String password, String role) {
        List<User> users = JsonDB.load(USERS_JSON, User.class);
        
        // Verificar si usuario ya existe
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        
        // Crear nuevo usuario
        User newUser = new User(
            UUID.randomUUID().toString(),
            username,
            password,
            role
        );
        
        users.add(newUser);
        JsonDB.save(USERS_JSON, users);
        return true;
    }


    
}
