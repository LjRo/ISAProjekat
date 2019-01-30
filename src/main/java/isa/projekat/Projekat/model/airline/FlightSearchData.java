package isa.projekat.Projekat.model.airline;

import java.util.Date;

public class FlightSearchData {
    private String cityFrom;
    private String cityTo;
    private Date startDate;
    private Date landDate;

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLandDate() {
        return landDate;
    }

    public void setLandDate(Date landDate) {
        this.landDate = landDate;
    }
}
