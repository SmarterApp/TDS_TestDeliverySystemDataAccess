package tds.dll.common.diagnostic.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status extends AbstractStatus {

    private String unit;
    private Level level;
    private Date dateTime;

    private Configuration configuration = null;
    private LocalSystem localSystem = null;
    private Providers providers = null;
    private Database database = null;

    public Status(String unit, Level level, Rating rating, Date dateTime) {
        super(rating);
        this.unit = unit;
        this.level = level;
        this.dateTime = dateTime;
    }

    public Status(String unit, Level level, Date dateTime) {
        super(Rating.IDEAL);
        this.unit = unit;
        this.level = level;
        this.dateTime = dateTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getLevel() {
        return level.getLevel();
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getDateTime() {
        return dateTime.toString();
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        super.updateRating(configuration);
    }

    public LocalSystem getLocalSystem() {
        return localSystem;
    }

    public void setLocalSystem(LocalSystem localSystem) {
        this.localSystem = localSystem;
        super.updateRating(localSystem);
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
        super.updateRating(providers);
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
        super.updateRating(database);
    }
}
