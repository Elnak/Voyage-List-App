package project_prog_b2_byloos_lietar.shared.models;

import java.math.BigDecimal;

public class Ville extends Serial {
    String nom_ville;
    Double lattitude;
    Double longitude;
    String nom_pays;

    public Ville(String[] info_received){
        this.nom_ville = info_received[0];
        this.lattitude = Double.parseDouble(info_received[1]);
        this.longitude = Double.parseDouble(info_received[2]);
        this.nom_pays = info_received[3];
    }

    @Override
    public String toString() {
        return nom_ville + " (" + nom_pays + ")";
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
            final int R = 6371;
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) *
                    Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c;
            distance = Math.pow(distance, 2);
            return Math.sqrt(distance);
    }

    public Double getLattitude() {return lattitude;}

    public Double getLongitude() {
        return longitude;
    }
}
