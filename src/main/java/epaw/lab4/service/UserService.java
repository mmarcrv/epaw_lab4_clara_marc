package epaw.lab4.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import epaw.lab4.model.User;
import epaw.lab4.repository.UserRepository;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserService {

    private static UserService instance;
    private UserRepository userRepository;

    private UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Set<String> COMARQUES_VALIDES = Set.of(
        "Alt Camp", "Alt Empordà", "Alt Penedès", "Anoia", "Bages",
        "Baix Camp", "Baix Ebre", "Baix Llobregat", "Baix Penedès", "Barcelona",
        "Berguedà", "Cerdanya", "Conca de Barberà", "Garraf", "Garrigues",
        "Garrotxa", "Gironès", "Maresme", "Montsià", "Noguera",
        "Osona", "Pallars Jussà", "Pallars Sobirà", "Pla d'Urgell", "Pla de l'Estany",
        "Priorat", "Ribera d'Ebre", "Ripollès", "Segarra", "Segrià",
        "Selva", "Solsonès", "Tarragonès", "Terra Alta", "Urgell",
        "Vallès Occidental", "Vallès Oriental"
    );

    public Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();

        String name = user.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "El nom d'usuari no pot estar buit.");
        } else if (name.length() < 5 || name.length() > 15) {
            errors.put("name", "El nom d'usuari ha de tenir entre 5 i 15 caràcters.");
        } else if (userRepository.existsByUsername(name)) {
            errors.put("name", "El nom d'usuari ja existeix.");
        }

        String password = user.getPassword();
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.put("password", "La contrasenya ha de tenir almenys 8 caràcters, incloent majúscules, minúscules, números i caràcters especials.");
        }

        String firstName = user.getFirstName();
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "El nom no pot estar buit.");
        } else if (firstName.length() < 2 || firstName.length() > 50) {
            errors.put("firstName", "El nom ha de tenir entre 2 i 50 caràcters.");
        }

        String lastName = user.getLastName();
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "El cognom no pot estar buit.");
        } else if (lastName.length() < 2 || lastName.length() > 50) {
            errors.put("lastName", "El cognom ha de tenir entre 2 i 50 caràcters.");
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "El correu electrònic no pot estar buit.");
        } else if (!email.matches(EMAIL_REGEX)) {
            errors.put("email", "Format de correu electrònic invàlid.");
        } else if (userRepository.existsByEmail(email)) {
            errors.put("email", "El correu electrònic ja està registrat.");
        }

        String dateOfBirth = user.getDateOfBirth();
        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            errors.put("dateOfBirth", "La data de naixement no pot estar buida.");
        } else {
            try {
                LocalDate birthDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
                if (birthDate.isAfter(LocalDate.now())) {
                    errors.put("dateOfBirth", "La data de naixement no pot ser en el futur.");
                }
            } catch (DateTimeParseException e) {
                errors.put("dateOfBirth", "Format de data invàlid.");
            }
        }

        String comarca = user.getComarca();
        if (comarca != null && !comarca.trim().isEmpty() && !COMARQUES_VALIDES.contains(comarca)) {
            errors.put("comarca", "Comarca d'origen no vàlida.");
        }

        return errors;
    }

    public Map<String, String> register(User user) {
        Map<String, String> errors = validate(user);
        if (errors.isEmpty()) {
            userRepository.save(user);
        }
        return errors;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Map<String, String> login(User user) {
        Map<String, String> errors = new HashMap<>();
        if (!userRepository.checkLogin(user)) {
            errors.put("password", "The combination of name and password does not match in our database.");
        }
        return errors;
    }

    public String saveProfilePicture(Part filePart, String username) {
        if (filePart == null || filePart.getSize() <= 0) return null;
        try {
            String fileName = filePart.getSubmittedFileName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = username + extension;
            String resourcesDir = "EXTERNAL_RESOURCES";
            Files.createDirectories(Paths.get(resourcesDir));
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(resourcesDir, newFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
