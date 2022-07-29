package project_prog_b2_byloos_lietar.shared.models;

import java.util.List;

public class ListVoyages extends Serial{
    List<Voyages> voyagesList;

    public ListVoyages(List<Voyages> voyagesList){
        this.voyagesList = voyagesList;
    }

    public List<Voyages> getVoyagesList() {
        return voyagesList;
    }
    public void Delete_Voyages(int position) {
        voyagesList.remove(position);
    }
    public void Add_Voyages(Voyages voyages){
        voyagesList.add(voyages);
    }

    public void Edit_Voyages(Voyages voyage,int position){
        voyagesList.set(position,voyage);
    }
}
