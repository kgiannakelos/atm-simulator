package gr.kgiannakelos.atmsimulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final AtmSimulation atmSimulation;

    @Autowired
    public Application(AtmSimulation atmSimulation) {
        this.atmSimulation = atmSimulation;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        atmSimulation.run();
    }
}
