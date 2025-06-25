package service;

import model.User;
import util.JsonDB;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static final String USERS_FILE = "users.json";
    private static List<User> users = JsonDB.load(USERS_FILE, User.class);

    // Devuelve todos los usuarios cargados
    public static List<User> getAllUsers() {
        return users;
    }

    // Filtra usuarios por rol (ejemplo: "profesor", "estudiante", etc)
    public static List<User> getUsersByRole(String role) {
        if (role == null) return List.of();
        return users.stream()
                .filter(u -> role.equalsIgnoreCase(u.getRole()))
                .collect(Collectors.toList());
    }

    // Actualiza un usuario existente
    public static void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                JsonDB.save(USERS_FILE, users);
                break;
            }
        }
    }

    // Agrega un nuevo usuario y guarda la lista
    public static void addUser(User newUser) {
        users.add(newUser);
        JsonDB.save(USERS_FILE, users);
    }
}