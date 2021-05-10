package com.ascent.autosapi;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest
public class AutoControllerTests {
    // Q: Why is constructor injection preferred over field injection via Autowired?
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService autoService;

    private ArrayList<Automobile> automobiles = new ArrayList<>();

    @BeforeEach
    void setUpAutomobiles() {
        for(int i = 0; i < 5; i++) {
            Automobile automobile = new Automobile(i);
            automobiles.add(automobile);
        }
    }

//     GET: /api/autos
//         - GET: /api/autos returns a list of all automobiles from database (STATUS CODE: 200)
    @Test
    @DisplayName("It should GET all automobiles")
    void findAllAutomobiles() throws Exception{
        when(autoService.getAllAutos()).thenReturn(automobiles);
        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

//         - GET: /api/autos?color=green&make=Mercedes returns a list of automobiles that match GREEN and MERCEDES (STATUS CODE: 200)

//    @Test
//    @DisplayName("It should return a list of automobiles with color search criteria")
//    void findAllAutomobilesWithColorCriteria() throws Exception {
//        // SETUP
//        String searchColor = "green";
//        Automobile mercedes = new Automobile(5, "Mercedes", "green");
//        Automobile porsche = new Automobile(5, "Porsche", "green");
//        automobiles.add(mercedes);
//        automobiles.add(porsche);
//
//        ArrayList<Automobile> expected = automobiles.stream()
//                .filter(automobile -> automobile.getColor().equals("green"))
//                .collect(Collectors.toCollection(ArrayList::new)); // Method reference
//
//        // EXECUTE AND ASSERT
//        when(autoService.getAllAutosWithCriteria(searchColor)).thenReturn(expected);
//        mockMvc.perform(get("/api/autos/?color=green"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                //.andExpect(jsonPath("$.automobiles[0].color"));
//                .andExpect(jsonPath("$", hasItemInArray(porsche.toString()))); // to string?
//
//    }
//         - GET: /api/autos returns a status code of 204 when no automobiles are found in the database
//
//     POST: /api/autos
//         - POST: /api/autos returns a status of 200 when adding an automobile with year, make, and model
//         - POST: /api/autos returns a status of 400 when making a bad request
//
//     GET: /api/autos/{vin}
//         - GET: /api/autos/{vin} returns a particular automobile, distinguished by unique vin (STATUS CODE: 200)
//         - GET: /api/autos/{vin} returns a status code of 204 when there are no automobiles matching vin in the database (STATUS CODE: 204)
//
//     PATCH: /api/autos/{vin}
//         - PATCH: /api/autos/{vin} returns JSON of updated automobile based on vin, can only update color and owner (STATUS CODE: 200)
//         - PATCH: /api/autos/{vin} returns a status code of 204 when there are no automobiles matching vin in the database (STATUS CODE: 204)
//         - PATCH: /api/autos/{vin} returns a status of 400 when making a bad request
//
//     DELETE: /api/autos/{vin}
//       - DELETE: /api/autos/{vin} returns response code of 202 when we delete a vehicle found in the database
//       - DELETE: /api/autos/{vin} returns response code of 204 when no vehicle is found in the database
}
