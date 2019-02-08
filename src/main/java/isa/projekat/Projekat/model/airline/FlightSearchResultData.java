package isa.projekat.Projekat.model.airline;

import java.math.BigDecimal;

public class FlightSearchResultData {
    private Flight flight;
    private Boolean hasExtraLuggage;
    private Boolean hasOtherServices;
    private Boolean hasFood;
    private BigDecimal luggagePrice;
    private BigDecimal foodPrice;

    public FlightSearchResultData() {
        super();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Boolean getHasExtraLuggage() {
        return hasExtraLuggage;
    }

    public void setHasExtraLuggage(Boolean hasExtraLuggage) {
        this.hasExtraLuggage = hasExtraLuggage;
    }

    public Boolean getHasOtherServices() {
        return hasOtherServices;
    }

    public void setHasOtherServices(Boolean hasOtherServices) {
        this.hasOtherServices = hasOtherServices;
    }

    public Boolean getHasFood() {
        return hasFood;
    }

    public void setHasFood(Boolean hasFood) {
        this.hasFood = hasFood;
    }

    public BigDecimal getLuggagePrice() {
        return luggagePrice;
    }

    public void setLuggagePrice(BigDecimal luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

    public BigDecimal getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(BigDecimal foodPrice) {
        this.foodPrice = foodPrice;
    }
}
