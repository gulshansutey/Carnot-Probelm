package gulshansutey.carnotproblem.models;

/**
 * Created by asus on 11/4/2017.
 */

public class InfoModel {

    public String getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(String startMillis) {
        this.startMillis = startMillis;
    }

    public String getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(String endMillis) {
        this.endMillis = endMillis;
    }

    public String getSaveEndMillis() {
        return saveEndMillis;
    }

    public void setSaveEndMillis(String saveEndMillis) {
        this.saveEndMillis = saveEndMillis;
    }

    public String getSaveStartMillis() {
        return saveStartMillis;
    }

    public void setSaveStartMillis(String saveStartMillis) {
        this.saveStartMillis = saveStartMillis;
    }

    private String startMillis="";
    private String endMillis="";
    private String saveEndMillis="";
    private String saveStartMillis="";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url="";



}
