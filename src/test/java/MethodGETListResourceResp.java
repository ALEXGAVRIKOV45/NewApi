import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodGETListResourceResp {
    private int id;
    private String name;
    private Integer year;
    private String color;
    @JsonProperty("pantone_value")
    private String pantoneValue;

    public MethodGETListResourceResp() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPantoneValue() {
        return pantoneValue;
    }
}
