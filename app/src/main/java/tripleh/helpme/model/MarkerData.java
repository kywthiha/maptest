package tripleh.helpme.model;

public class MarkerData {
    private double lat;
    private double lon;
    private String title;
    private String descriptioin;

    public MarkerData() {
    }

    public MarkerData(double lat, double lon, String title) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public MarkerData(double lat, double lon, String title, String descriptioin) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.descriptioin = descriptioin;
    }

    public String getDescriptioin() {
        return descriptioin;
    }

    public void setDescriptioin(String descriptioin) {
        this.descriptioin = descriptioin;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
