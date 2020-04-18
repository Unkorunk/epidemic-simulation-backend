package account;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.security.MessageDigest;
//
//@Entity
//public class UserAccount {
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Long id;
//    private String login;
//    private String password;
//    private String passHash;
//
//    private String firstName;
//    private String lastName;
//
//    protected UserAccount() {}
//
//    public UserAccount(String login, String password, String firstName, String lastName) {
//        this.login = login;
//        this.password = password;
//        this.passHash = getSha256(password);
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public UserAccount(Long id, String login, String password, String firstName, String lastName) {
//        this.id = id;
//        this.login = login;
//        this.password = password;
//        this.passHash = getSha256(password);
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "UserAccount[id=%d, login=%s, password=%s, passHash=%s firstName='%s', lastName='%s']",
//                id, login, password, passHash, firstName, lastName);
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin( String login ) {
//        this.login = login;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword( String password ) {
//        this.passHash = password;
//        this.passHash = getSha256(password);
//    }
//
//    public String getPassHash() {
//        return passHash;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName( String firstName ) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName( String lastName ) {
//        this.lastName = lastName;
//    }
//
//    public static String getSha256(String value) {
//        try{
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(value.getBytes());
//            return bytesToHex(md.digest());
//        } catch(Exception ex){
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private static String bytesToHex(byte[] bytes) {
//        StringBuffer result = new StringBuffer();
//        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
//        return result.toString();
//    }
//}
