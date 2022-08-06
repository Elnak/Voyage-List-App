package project_prog_b2_byloos_lietar.shared.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Voyages extends Serial{
    String nom_voyages = "Voyage 1";
    Ville ville_depart;
    LocalDate date_depart;
    double cout_total = 0;
    double temps_voyages_total = 0;
    double km_total = 0;
    boolean isEdited = false;

    String ville_traversee = "";

    List<Object> liste_evenement_voyage;

    public Voyages(){
        isEdited = true;
    }

    public Voyages(Object[] Array_Info){
        this.nom_voyages = (String) Array_Info[0];
        this.ville_depart = (Ville) Array_Info[1];
        this.date_depart = (LocalDate) Array_Info[2];
        this.ville_traversee = (String) Array_Info[3];
        this.liste_evenement_voyage = (List<Object>) Array_Info[4];
        Calcul_Resume();
    }
    private void Calcul_Resume(){
        Ville ville_prec = ville_depart;
        for(Object var : liste_evenement_voyage){
            if(var instanceof Hotels){
                cout_total = cout_total + ((Hotels) var).getPrix_nuit() * ((Hotels) var).getNbr_nuit();
                temps_voyages_total = temps_voyages_total + ((Hotels) var).nuitToMinute();
            }
            else{
                Avions avions = (Avions) var;
                if(avions.IsFullyCompleted() && ville_prec != null){
                    Object[] info_Avion = avions.Resume_Avion(ville_prec);
                    km_total =  km_total + (double) info_Avion[0];
                    temps_voyages_total = temps_voyages_total + (int) info_Avion[1];
                    cout_total = cout_total + (double) info_Avion[2];
                    ville_prec = avions.getDestination();
                }
            }
        }
    }
    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public LocalDate getDate_depart() {return date_depart;}

    public String getNom_voyages() {return nom_voyages;}

    public Ville getVille_depart() {return ville_depart;}

    public double getCout() {
        BigDecimal cout_total_bd = new BigDecimal(cout_total).setScale(2, RoundingMode.HALF_UP);
        return cout_total_bd.doubleValue();
    }

    public double getKm() {
        BigDecimal km_total_bd = new BigDecimal(km_total).setScale(2, RoundingMode.HALF_UP);
        return km_total_bd.doubleValue();
    }

    public double getTemps_voyages() {
        double tempo = temps_voyages_total/60;
        BigDecimal temps_bd = new BigDecimal(tempo).setScale(2, RoundingMode.HALF_UP);
        return temps_bd.doubleValue();
    }

    public String getVille_traversee() {return ville_traversee;}

    public List<Object> getListe_evenement_voyage() {return liste_evenement_voyage;}

    public Object[] getTopBarInfo(){
        Object[] info_top_bar = new Object[3];
        info_top_bar[0] = getNom_voyages();
        info_top_bar[1] = getVille_depart();
        info_top_bar[2] = getDate_depart();
        return info_top_bar;
    }

    public String getBottomBar(){
        String return_string = getKm() + "km , " + getTemps_voyages()+ "heures, " + getCout() + " euros";
        return return_string;
    }
}
