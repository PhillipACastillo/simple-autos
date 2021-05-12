package com.ascent.autosapi;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutoService {
    AutosRepository autosRepository;

    public AutoService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public ArrayList<Automobile> getAllAutos() {
        return new ArrayList<>(autosRepository.findAll());
    }

    public ArrayList<Automobile> getAllAutos(String param) {
        return new ArrayList<>(autosRepository.findAll()).stream()
                .filter(automobile -> automobile.getColor().equals(param) || automobile.getMake().equals(param))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Automobile> getAllAutos(String make, String color) {
        return new ArrayList<>(autosRepository.findAll()).stream()
                .filter(automobile -> automobile.getColor().equals(color) && automobile.getMake().equals(make))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Automobile createNewAuto(Automobile automobile) {
        return autosRepository.save(automobile);
    }

    public Automobile getAutoByVin(Long vin) {
        Optional<Automobile> auto = autosRepository.findById(vin);
        return auto.orElse(null);
    }

    public Automobile updateAuto(Long vin, Map<String, Object> fields) {
        Automobile automobile = getAutoByVin(vin);
        fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Automobile.class, key);
            if (field != null ) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, automobile, value);
                field.setAccessible(false);
            }
        });
        return autosRepository.save(automobile);
    }

    public void delete(Long vin) throws InvalidAutoException {
        Optional<Automobile> auto = autosRepository.findById(vin);
        auto.ifPresentOrElse(automobile -> autosRepository.delete(automobile), () -> {
            throw new InvalidAutoException("Invalid Auto");
        });
    }
}
