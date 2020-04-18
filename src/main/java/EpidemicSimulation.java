
import account.UserAccount;
import account.UserAccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class EpidemicSimulation {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EpidemicSimulation.class, args);

        UserAccountRepository repository = context.getBean(UserAccountRepository.class);

    }
}
