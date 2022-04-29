package dipsa.cicd.weather.models;

import jakarta.json.JsonObject;

public class Description {
    private String main;
    private String description;
    private String icon;

    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Description create(JsonObject json) {
        final Description desc = new Description();
        desc.setMain(json.getString("main"));
        desc.setDescription(json.getString("description"));
        desc.setIcon(json.getString("icon"));
        return desc;
    }
    
}
