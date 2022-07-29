package project_prog_b2_byloos_lietar.shared.models;

import java.util.Date;
import java.util.List;

public class Voyages extends Serial{
    String nom_voyages = "Voyage 1";
    String ville_depart = "Aucune ville de départ";
    Date date_depart;
    float cout = 0;
    float temps_voyages = 0;
    float km = 0;
    boolean isEdited = false;

    String ville_traversee = "";

    List<Object> liste_evenement_voyage;

    public Voyages(){
        isEdited = true;
    }

    public Voyages(String nom_voyages, String ville_depart, Date date_depart, float cout, float temps_voyages, float km, String ville_traversee, List<Object> liste_evenement_voyage){
        this.nom_voyages = nom_voyages;
        this.ville_depart = ville_depart;
        this.date_depart = date_depart;
        this.cout = cout;
        this.temps_voyages = temps_voyages;
        this.km = km;
        this.ville_traversee = ville_traversee;
        this.liste_evenement_voyage = liste_evenement_voyage;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public Date getDate_depart() {return date_depart;}

    public String getNom_voyages() {return nom_voyages;}

    public String getVille_depart() {return ville_depart;}

    public float getCout() {return cout;}

    public float getTemps_voyages() {return temps_voyages;}

    public float getKm() { return km;}

    public String getVille_traversee() { return ville_traversee;}

    public void setNom_voyages(String nom_voyages){
        this.nom_voyages = nom_voyages;
    }

}
