package com.ascent.autosapi;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AutoService {
    public ArrayList<Automobile> getAllAutos() {
        return null;
    }

    public ArrayList<Automobile> getAllAutos(String param) {
        return null;
    }

    /*
        ArrayList<Automobile> automobiles;

        for (Automobile automobile : automobiles) {
            if (automobile.getColor.equals(param)) {
                automobiles.add(automobile);
            } else if (automobile.getMake.equals(param)) {
                automobiles.add(automobile);
            }

            return automobiles;
        }
     */

    public ArrayList<Automobile> getAllAutos(String color, String make) { return null; }

    public Automobile createNewAuto(Automobile automobile) { return null; }

    public Automobile getAutoByVin(Long vin) { return null; }

    public Automobile updateAuto(Long vin, Map fields) { return null; }

    public boolean deleteByVin(Long vin) { return false; }
}
