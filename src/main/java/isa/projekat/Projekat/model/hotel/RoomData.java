package isa.projekat.Projekat.model.hotel;

import java.io.Serializable;

public class RoomData implements Serializable {
    private String name;
    private RoomType roomType;
    private int numberOfPeople;
    private int numberOfBeds;
    private int numberOfRooms;
    private int roomNumber;
    private int floor;

    public RoomData() {
        super();
    }

    public RoomData(String name, RoomType roomType, int numberOfPeople, int numberOfBeds, int numberOfRooms, int roomNumber, int floor) {
        this.name = name;
        this.roomType = roomType;
        this.numberOfPeople = numberOfPeople;
        this.numberOfBeds = numberOfBeds;
        this.numberOfRooms = numberOfRooms;
        this.roomNumber = roomNumber;
        this.floor = floor;
    }

    public String getName() { return name;}

    public void setName(String name) {this.name = name;}

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {return floor;}

    public void setFloor(int floor) {this.floor = floor; }
}
