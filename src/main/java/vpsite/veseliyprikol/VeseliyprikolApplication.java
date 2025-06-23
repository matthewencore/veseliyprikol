package vpsite.veseliyprikol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VeseliyprikolApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeseliyprikolApplication.class, args);
    }

}
