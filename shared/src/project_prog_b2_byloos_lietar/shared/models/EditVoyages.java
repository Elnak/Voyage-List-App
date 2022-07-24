package project_prog_b2_byloos_lietar.shared.models;

public class EditVoyages extends Serial{
    Voyages voyages;
    int position;

    public EditVoyages(Voyages voyages,int position){
        this.voyages = voyages;
        this.position = position;
    }
}
