package isa.projekat.Projekat.model.airline;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyProfitData {
    private LocalDate startTime;
    private BigDecimal price;

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
