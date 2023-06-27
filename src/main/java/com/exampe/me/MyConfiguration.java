package com.exampe.me;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.validation.ValidationConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * my configuration
 *
 */
@Configuration
public class MyConfiguration {

    @Bean
    public ValidatorFactory myValidatorFactory(ObjectProvider<ValidationConfigurationCustomizer> customizers) {
        jakarta.validation.Configuration<?> configure = Validation.byDefaultProvider()
                .configure();
        customizers.orderedStream()
                .forEach(c -> c.customize(configure));
        return configure
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
    }

    @Bean
    public Validator myValidator(ValidatorFactory ferryValidatorFactory) {
        return ferryValidatorFactory.usingContext()
                .messageInterpolator(new ParameterMessageInterpolator())
                .getValidator();
    }
}
