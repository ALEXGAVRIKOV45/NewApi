package service;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Reloadable;

@Config.Sources({"file:src/test/resources/reqresTest.properties"})
public interface ReqresConfiguration extends Config, Reloadable {

    @Key("name")
    @DefaultValue("")
    String name();

    @Key("job")
    @DefaultValue("")
    String job();

    @Key("jobCREATE")
    @DefaultValue("")
    String jobCREATE();

    @Key("id")
    @DefaultValue("")
    int id();

    @Key("token")
    @DefaultValue("")
    String token();
}
