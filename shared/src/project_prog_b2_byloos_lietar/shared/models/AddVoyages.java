package project_prog_b2_byloos_lietar.shared.models;

public class AddVoyages extends Serial{
    Voyages voyages;

    public AddVoyages(Voyages voyages){
        this.voyages = voyages;
    }

    public Voyages getVoyages() {
        return voyages;
    }
}
