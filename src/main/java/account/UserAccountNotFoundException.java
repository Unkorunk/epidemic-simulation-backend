package account;

class UserAccountNotFoundException extends RuntimeException {

    UserAccountNotFoundException(Long id) {
        super("Could not find account " + id);
    }
}