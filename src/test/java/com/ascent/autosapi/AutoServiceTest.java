package com.ascent.autosapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {
    AutoService autoService;
    ArrayList<Automobile> automobiles;

    @Mock
    AutosRepository autosRepository;

    @BeforeEach
    void setUp() {
        autoService = new AutoService(autosRepository);
        automobiles = new ArrayList<>();
        // Example of IntStream and lambdas
        IntStream.range(1, 6).forEach((num) -> {
            automobiles.add(new Automobile("Toyota " + num, "Color " + num));
        });
    }

    @Test
    @DisplayName("It should return a list of all automobiles")
    void getAllAutos() {
        when(autosRepository.findAll()).thenReturn(automobiles);
        assertEquals(5, autoService.getAllAutos().size());
    }

    @Test
    @DisplayName("It should return one automobile based on the search criteria")
    void getAllAutos_withOneParam() {
        when(autosRepository.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(new Automobile("Jeep", "Green"))));
        assertEquals(1, autoService.getAllAutos("Green").size());
    }

    @Test
    @DisplayName("It should return two automobiles based on the search critera")
    void getAllAutos_withTwoParams() {
        ArrayList<Automobile> filtered = new ArrayList<>();
        filtered.add(new Automobile("Jeep", "Yellow"));
        filtered.add(new Automobile("Jeep", "Yellow"));
        when(autosRepository.findAll()).thenReturn(filtered);
        assertEquals(2, autoService.getAllAutos("Jeep", "Yellow").size());
    }

    @Test
    @DisplayName("It should create a new automobile")
    void createNewAuto() {
        Automobile auto = new Automobile("Mustang Shelby GT 500", "Blue");
//        when(autosRepository.save((Automobile) any(Automobile.class))).thenReturn(new Automobile("Ford Mustang", "Blue"));
//        assertNotNull(autoService.createNewAuto((Automobile) any(Automobile.class)));
        when(autosRepository.save(auto)).thenReturn(auto);
        assertNotNull(autoService.createNewAuto(auto));
    }

    @Test
    @DisplayName("It should retrieve the correct automobile with a given VIN")
    void getAutoByVin() {
        Optional<Automobile> auto = Optional.of(new Automobile("Mustang Shelby GT 500", "Blue"));
        when(autosRepository.findById(anyLong())).thenReturn(auto);
        assertEquals(auto.get(), autoService.getAutoByVin(anyLong()));
    }

    @Test
    void updateAuto() {
        Optional<Automobile> auto = Optional.of(new Automobile("Mustang Shelby GT 500", "Blue"));
        when(autosRepository.findById(anyLong())).thenReturn(auto);

        // Refactor tests and change fields to see if update changed our auto instance
        auto.get().setColor("Red");
        when(autosRepository.save(auto.get())).thenReturn(auto.get());
        assertEquals(auto.get(), autoService.updateAuto(1L, new HashMap<>()));
    }

    @Test
    @DisplayName("It should delete automobile by vin")
    void deleteByVin() {
        Optional<Automobile> auto = Optional.of(new Automobile("Mustang Shelby GT 500", "Blue"));
        when(autosRepository.findById(anyLong())).thenReturn(auto);
        autoService.delete(auto.get().getVin());
        verify(autosRepository).delete(auto.get());
    }

    @Test
    @DisplayName("It should return false when trying to delete an auto that doesn't exist")
    void deleteByVin_noVin() {
//        Optional<Automobile> auto = Optional.of(new Automobile("Mustang Shelby GT 500", "Blue"));
        when(autosRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatExceptionOfType(InvalidAutoException.class)
                .isThrownBy(() -> {
                    autoService.delete(anyLong());
                });
    }
}