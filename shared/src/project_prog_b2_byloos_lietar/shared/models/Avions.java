package project_prog_b2_byloos_lietar.shared.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Avions extends Serial {
    Ville destination;
    int attente = 0;
    int vitesse = 900;
    double prix_km = 0.025;
    double nombre_km;
    int temps;
    double prix;

    public Avions(Object[] view_info){
        destination = (Ville) view_info[0];
        attente = (int) view_info[1];
        vitesse = (int) view_info[2];
        prix_km = (Double) view_info[3];
    }
    public Object[] Resume_Avion(Ville ville_precedente){
        nombre_km = Ville.distance(ville_precedente.getLattitude(),destination.getLattitude(),ville_precedente.getLongitude(),destination.getLongitude());
        temps = (int) (attente + (60*nombre_km)/vitesse);
        prix = nombre_km*prix_km;
        return new Object[]{nombre_km,temps,prix};
    }

    public boolean IsFullyCompleted(){
        if(destination == null){
            return false;
        }
        return true;
    }

    public Avions(){

    }

    public Ville getDestination() {
        return destination;
    }

    public int getAttente() {
        return attente;
    }

    public int getVitesse() {
        return vitesse;
    }

    public double getPrix_km() {
        return prix_km;
    }

    public double getNombre_km() {
        BigDecimal nombre_km_bd = new BigDecimal(nombre_km).setScale(2, RoundingMode.HALF_UP);
        return nombre_km_bd.doubleValue();
    }

    public String getTempString() {
        double tempo = (double)temps/60;
        BigDecimal temps_bd = new BigDecimal(tempo).setScale(2, RoundingMode.HALF_UP);
        return String.valueOf(temps_bd.doubleValue());
    }

    public double getPrix() {
        BigDecimal prix_bd = new BigDecimal(prix).setScale(2, RoundingMode.HALF_UP);
        return prix_bd.doubleValue();
    }


}
