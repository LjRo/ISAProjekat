package isa.projekat.Projekat.model.airline;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FlightData {
    private Long startID;
    private Long destID;
    private Date startDate;
    private Date finishDate;
    private Double length;
    private Double distance;
    private BigDecimal price;
    private Integer segments;
    private Integer columns;
    private Integer rows;
    private Integer stopCount;
    private List<Long> stopIDs;

    public FlightData() {
    }

    public Long getStartID() {
        return startID;
    }

    public void setStartID(Long startID) {
        this.startID = startID;
    }

    public Long getDestID() {
        return destID;
    }

    public void setDestID(Long destID) {
        this.destID = destID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSegments() {
        return segments;
    }

    public void setSegments(Integer segments) {
        this.segments = segments;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStopCount() {
        return stopCount;
    }

    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }

    public List<Long> getStopIDs() {
        return stopIDs;
    }

    public void setStopIDs(List<Long> stopIDs) {
        this.stopIDs = stopIDs;
    }
}
