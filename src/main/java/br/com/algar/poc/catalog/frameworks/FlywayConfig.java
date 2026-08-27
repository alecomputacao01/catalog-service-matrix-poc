package br.com.algar.poc.catalog.frameworks;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Spring Boot 4.1 removeu a autoconfiguração automática do Flyway (achado do piloto
 * springboot4-postgres-hexagonal — nenhuma FlywayAutoConfiguration no jar
 * spring-boot-autoconfigure-4.1.1). Dispara a migration interceptando o bean "dataSource" via
 * BeanPostProcessor: o Spring garante que "dataSource" está pronto antes de ser injetado no
 * entityManagerFactory, então a migration sempre roda antes do Hibernate validar o schema.
 */
@Configuration
public class FlywayConfig {

    @Bean
    static BeanPostProcessor flywayMigrationBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                if ("dataSource".equals(beanName) && bean instanceof DataSource dataSource) {
                    Flyway.configure().dataSource(dataSource).load().migrate();
                }
                return bean;
            }
        };
    }
}
