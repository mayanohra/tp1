/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Retouch
 */
public class Prog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        File file = new File("files.json"); // Il faut changer le files.json en args[1]
        String string = FileUtils.readFileToString(file); 
//        System.out.println("Read in: " + string);
        JSONObject rObj = JSONObject.fromObject(string);
//      System.out.println(jsonObject); 

        String nDep = rObj.getString("nom_departement");
        int typ = rObj.getInt("type_ departement");
        String tMin = rObj.getString("taux_horaire_min");
        int min = Integer.parseInt(tMin.replaceAll("\\D+", ""));
        String tMax = rObj.getString("taux_ horaire_max");
        int max = Integer.parseInt(tMax.replaceAll("\\D+", ""));
        
        String nom;
        int nAnc;
        int nDip;
        int nTra;
        String dat;
        JSONArray jAray = rObj.getJSONArray("employes");
        JSONObject dep = new JSONObject();
        
        int vEm = 0;  // valeur employé
        double vSal; // valeur salariale
        double vAnc;
        
        
        for(int i = 0; i < jAray.size(); i++){
            
            rObj = jAray.getJSONObject(i);
            
            nom = rObj.getString("nom");
            nAnc = rObj.getInt("nombre_droit_anciennete");
            nDip = rObj.getInt("nombre_ diplomes");
            nTra = rObj.getInt("charge_travail");
            dat = rObj.getString("date_revision_salaire");
            
            rObj.clear(); 
            
            // Calcul de la valeur de l'employé
            vSal = vEmploye(typ, nTra, min, max);
            vAnc = dAncien(0, 3, 17000);
            System.out.print(vAnc);
            dep.accumulate("nom", nom);
            dep.accumulate("valeur_par_employe", vEm + "$");
            
            
        }
        
        // Valeur salariale = val. sal. + droit d'anc + diplomes 
        // Valeur salariale totale = 9733.70 + val. sal. de tous les employes
        
        
    }
    public static double vEmploye(int typ, int nTra, int min, int max){
        if(typ == 0){
            return nTra * min;
        } else if (typ == 1){
            return nTra * ( (min + max) / 2 );
        }else {
            return nTra * max;
        }
    }
    
    public static double dAncien(int typ , int nAnc, double vSal){
        if (typ == 0){
            return nAnc * ((vSal * 5) / 100) - 5000;
        } else if(typ == 1){
            return nAnc * ((vSal * 10) / 100) - 5000;
        } else{
            return nAnc * ((vSal * 15) / 100) - 5000;
        }
        
    }
}
