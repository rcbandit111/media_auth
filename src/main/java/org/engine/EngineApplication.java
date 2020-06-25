package org.engine;

import org.engine.config.KeycloakServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableConfigurationProperties(KeycloakServerProperties.class)
@EnableSwagger2WebMvc
@SpringBootApplication(scanBasePackages = { "org.engine.*", "org.engine.production.service", "org.engine.warehouse.service" })
public class EngineApplication {

    private static final Logger LOG = LoggerFactory.getLogger(EngineApplication.class);

    public static void main(final String[] args)
    {
        SpringApplication.run(EngineApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties, KeycloakServerProperties keycloakServerProperties) {

        return (evt) -> {

            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();

            LOG.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, keycloakContextPath);
        };
    }
}
