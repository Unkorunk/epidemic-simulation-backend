package account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.MessageDigest;

// SHA 512
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String token;

    protected UserAccount() {}

    public UserAccount(String login, String password) {
        this.login = login;
        this.password = getSha256(password);
    }

    public UserAccount(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = getSha256(password);

    }

    @Override
    public String toString() {
        return String.format(
                "UserAccount[id=%d, login=%s, password=%s]",
                id, login, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = getSha256(password);
    }


    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedToken = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedToken = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedToken;
    }

    public static String getSha256(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
