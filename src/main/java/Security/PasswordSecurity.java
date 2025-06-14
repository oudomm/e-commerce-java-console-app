package Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordSecurity {
        public static String hashing(String input) {
            try{
                MessageDigest md   = MessageDigest.getInstance("SHA-256");
                md.update(generateSalt());
                byte [] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(bytes);
            }catch (Exception exception){
                System.out.println("☹️ Error while hashing: " + exception.getMessage());
            }
            return "Not Hashed";
        }
        public static byte[] generateSalt() {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            return salt;
        }
    }
