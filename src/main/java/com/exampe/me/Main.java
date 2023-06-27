package com.exampe.me;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Set;

/**
 * app
 *
 */
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication(scanBasePackages = "com.exampe.me", exclude = ValidationAutoConfiguration.class)
public class Main implements ApplicationRunner {

    @NotEmpty
    @Length(min = 2)
    private String name;

    public Main(Validator validator) {
        this.validator = validator;
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .main(Main.class)
                .sources(Main.class)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] params = args.getSourceArgs();
        name = params.length > 0 ? params[0]: "";
        Set<ConstraintViolation<Main>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<?> violation : violations) {
                errorMsg.append("ERROR: ")
                        .append(violation.getMessage())
                        .append(": ")
                        .append(violation.getInvalidValue())
                        .append("\n");
            }
            System.err.println("Validation: " + errorMsg);
        }
    }

    private final Validator validator;
}