package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:api.properties")
public interface ApiConfig extends Config {
    @Key("API_KEY")
    String getApiKey();
}
