package example.demo.shared.config;

import example.demo.shared.domain.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class TenantsConfig {

    //@Bean
    public static YamlPropertiesFactoryBean tenantsYamlProperties() {
        log.info("loading tenants.yml");
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("tenants.yml"));
        return yaml;
    }

    @Bean
    public PropertySource<?> tenantsYamlPropertySource() throws IOException {
        log.info("loading tenants.yml");
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        return loader.load("tenants", new ClassPathResource("tenants.yml")).get(0);
    }
}
