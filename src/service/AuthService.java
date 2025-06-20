package service;

import model.User;
import session.Session;
import util.JsonDB;

import java.util.List;
import java.util.UUID;

public class AuthService {
    private static final String USERS_FILE = "users.json";
    private static List<User> users = JsonDB.load(USERS_FILE, User.class);

    public static User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Session.setCurrentUser(user);
                return user;
            }
        }
        return null;
    }

    public static boolean signUp(String username, String password, String role) {
        // Verificar si ya existe el usuario
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Usuario ya existe
            }
        }
        // Generar un id único para el nuevo usuario
        String newId = UUID.randomUUID().toString();

        // Crear nuevo usuario con id, username, password y role
        User newUser = new User(newId, username, password, role);

        // Opcional: inicializar campos adicionales si quieres
        newUser.setNombre("");
        newUser.setApellido("");
        newUser.setCiudad("");
        newUser.setCorreo("");
        newUser.setTimezone(java.util.TimeZone.getDefault().getID());

        users.add(newUser);
        JsonDB.save(USERS_FILE, users);
        return true;
    }

    public static void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                JsonDB.save(USERS_FILE, users);
                break;
            }
        }
    }
}
