package service;

import org.aeonbits.owner.ConfigFactory;

public class ConfigManager {
    public static final TestConfiguration TEST_CONFIG;
    public static final ReqresConfiguration REQRES_CONFIG;

    public ConfigManager() {
    }

    static {
        TEST_CONFIG = (TestConfiguration) ConfigFactory.create(TestConfiguration.class);
        REQRES_CONFIG = (ReqresConfiguration) ConfigFactory.create(ReqresConfiguration.class);
    }
}
