package isa.projekat.Projekat.model.airline;

import java.util.List;

public class SeatData {
    private Integer segments;
    private Integer columns;
    private Integer rows;
    private List<Seat> seats;

    public SeatData() {
        super();
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}

