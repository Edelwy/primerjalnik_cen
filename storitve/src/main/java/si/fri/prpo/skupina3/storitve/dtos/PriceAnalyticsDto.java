package si.fri.prpo.skupina3.storitve.dtos;

public class PriceAnalyticsDto {
    String source;
    String country;
    String values;

    public PriceAnalyticsDto() {}
    public PriceAnalyticsDto(String source, String country, String values) {
        this.source = source;
        this.country = country;
        this.values = values;
    }

    public String getcountry() {
        return country;
    }

    public String getvalues() {
        return values;
    }

    public String getsource() {
        return source;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public void setvalues(String values) {
        this.values = values;
    }

    public void setsource(String source) {
        this.source = source;
    }
}
