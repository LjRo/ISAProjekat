package isa.projekat.Projekat.model.airline;

import java.time.LocalDate;

public class ProfitFilterData {
    private LocalDate sDate;
    private LocalDate eDate;

    public ProfitFilterData() {
        super();
    }

    public LocalDate getsDate() {
        return sDate;
    }

    public void setsDate(LocalDate sDate) {
        this.sDate = sDate;
    }

    public LocalDate geteDate() {
        return eDate;
    }

    public void seteDate(LocalDate eDate) {
        this.eDate = eDate;
    }
}
