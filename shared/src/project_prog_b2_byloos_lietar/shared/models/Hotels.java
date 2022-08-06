package project_prog_b2_byloos_lietar.shared.models;

public class Hotels extends Serial{
    int nbr_nuit = 1;
    int prix_nuit = 100;

    public Hotels(int[] view_info){
        this.nbr_nuit = view_info[0];
        this.prix_nuit = view_info[1];
    }

    public Hotels(){

    }

    public void setNbr_nuit(int nbr_nuit) {
        this.nbr_nuit = nbr_nuit;
    }

    public void setPrix_nuit(int prix_nuit) {
        this.prix_nuit = prix_nuit;
    }

    public int nuitToMinute(){
        return nbr_nuit*1440;
    }

    public int getNbr_nuit() {
        return nbr_nuit;
    }

    public int getPrix_nuit() {return prix_nuit;}

    public static String Calcul_Resume(int prix_nuit,int nbr_nuit){
        float resultat = prix_nuit * nbr_nuit;
        return String.valueOf(resultat);
    }
}
