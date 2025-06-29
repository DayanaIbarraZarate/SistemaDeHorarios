package service;

import java.util.ArrayList;
import java.util.List;
import model.User;
import util.JsonDB;

public class UserService {

    private static final String USERS_JSON = "users.json";

    // Método para obtener todos los usuarios desde el JSON
    public static List<User> getAllUsers() {
        return JsonDB.load(USERS_JSON, User.class);
    }

    // Método para obtener usuarios por rol (ej: "PROFESOR" o "ESTUDIANTE")
    public static List<User> getUsersByRole(String role) {
        List<User> allUsers = getAllUsers();
        List<User> filteredUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (user.getRole().equalsIgnoreCase(role)) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }
}
