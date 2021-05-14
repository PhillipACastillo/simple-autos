package com.ascent.autosapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebMvcTest
public class AutoControllerTests {
    // Q: Why is constructor injection preferred over field injection via Autowired?
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService autoService;
    ObjectMapper mapper = new ObjectMapper();

    private ArrayList<Automobile> automobiles = new ArrayList<>();

    @BeforeEach
    void setUpAutomobiles() {
        for(int i = 0; i < 5; i++) {
            Automobile automobile = new Automobile("Mercedes " + i, "Color " + i);
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

    @Test
    @DisplayName("It should return a list of automobiles with color search criteria")
    void findAllAutomobilesWithColorCriteria() throws Exception {
        // SETUP
        String searchColor = "green";
        Automobile mercedes = new Automobile(searchColor, "Mercedes");
        Automobile porsche = new Automobile(searchColor, "Porsche");
        automobiles.add(mercedes);
        automobiles.add(porsche);

        ArrayList<Automobile> expected = automobiles.stream()
                .filter(automobile -> automobile.getColor().equals("green"))
                .collect(Collectors.toCollection(ArrayList::new));

        // EXECUTE AND ASSERT
        when(autoService.getAllAutos(searchColor)).thenReturn(expected);
        mockMvc.perform(get("/api/autos").param("color", "green"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                //.andExpect(jsonPath("$.automobiles[0].color"));
                .andExpect(jsonPath("$[0].color", is("green"))); // to string?
    }
//         - GET: /api/autos returns a status code of 204 when no automobiles are found in the database

    @Test
    @DisplayName("It should return a status code of 204 when no automobiles are found")
    void findAllAutomobiles_NoAutosFound() throws Exception{
        when(autoService.getAllAutos()).thenReturn(new ArrayList<>()); // Make sure to have the same return type as the Controller
        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isNoContent());
    }

    //     POST: /api/autos
//         - POST: /api/autos returns a status of 200 when adding an automobile with year, make, and model
    @Test
    @DisplayName("It should return a status of 200 when adding an automobile with params")
    void addAutomobile_withCorrectParams() throws Exception {
        Automobile automobile = new Automobile("Honda", "red");

        when(autoService.createNewAuto(any(Automobile.class))).thenReturn(automobile);

        mockMvc.perform(post("/api/autos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(automobile)))
                .andExpect(status().isOk());
    }
    //         - POST: /api/autos returns a status of 400 when making a bad request
    @Test
    @DisplayName("It should return a status code of 400 when a bad request is made")
    void addAutomobile_withBadRequest() throws Exception {
        when(autoService.createNewAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
    //     GET: /api/autos/{vin}
//         - GET: /api/autos/{vin} returns a particular automobile, distinguished by unique vin (STATUS CODE: 200)
    @Test
    @DisplayName("It should return a particular automobile based on vin")
    void getAutomobileByVin() throws Exception {
        Automobile automobile = new Automobile("Red", "Honda");
        when(autoService.getAutoByVin(anyLong())).thenReturn(automobile);
        mockMvc.perform(get("/api/autos/" + automobile.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("make", is("Honda")));
    }
    //         - GET: /api/autos/{vin} returns a status code of 204 when there are no automobiles matching vin in the database (STATUS CODE: 204)
    @Test
    @DisplayName("It should return a status code of 204 when there are no automobiles matching a particular vin")
    void getAutomobileByVin_noMatchingVin() throws Exception {
        when(autoService.getAutoByVin(anyLong())).thenReturn(null);
        mockMvc.perform(get("/api/autos/1"))
                .andExpect(status().isNoContent());
    }
    //     PATCH: /api/autos/{vin}
//         - PATCH: /api/autos/{vin} returns JSON of updated automobile based on vin, can only update color and owner (STATUS CODE: 200)
    @Test
    @DisplayName("It should return JSON of updated auto based on vin")
    void updateAutomobileByVin() throws Exception {
        Automobile automobile = new Automobile("Red", "Honda");
        when(autoService.updateAuto(anyLong(), anyMap())).thenReturn(automobile);
        mockMvc.perform(patch("/api/autos/" + automobile.getVin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(automobile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("color", is("Red")));
    }
    //         - PATCH: /api/autos/{vin} returns a status code of 204 when there are no automobiles matching vin in the database (STATUS CODE: 204)
    @Test
    @DisplayName("It should return a status code of 204 when no automobiles match vin")
    void updateAutomobileByVin_noMatchingVin() throws Exception {
        when(autoService.updateAuto(anyLong(), anyMap())).thenReturn(null);
        Automobile automobile = new Automobile("Red", "Honda");
        mockMvc.perform(patch("/api/autos/234234")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(automobile)))
                .andExpect(status().isNoContent());
    }
    //         - PATCH: /api/autos/{vin} returns a status of 400 when making a bad request
    @Test
    @DisplayName("It should return a status code 400 when making a bad request")
    void updateAutomobileByVin_makingABadRequest() throws Exception {
        when(autoService.updateAuto(anyLong(), anyMap())).thenThrow(InvalidAutoException.class);

        Automobile automobile = new Automobile("Honda", "red");
        mockMvc.perform(patch("/api/autos/" + automobile.getVin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
    //     DELETE: /api/autos/{vin}
//       - DELETE: /api/autos/{vin} returns response code of 202 when we delete a vehicle found in the database
    @Test
    @DisplayName("It should return a status code of 202 when deleting by vin")
    void deleteAutomobileByVin() throws Exception {
        when(autoService.getAutoByVin(anyLong())).thenReturn(new Automobile("Tesla", "White"));
        doNothing().when(autoService).delete(anyLong());
        mockMvc.perform(delete("/api/autos/" + anyLong()))
                .andExpect(status().isAccepted());
    }
    //       - DELETE: /api/autos/{vin} returns response code of 204 when no vehicle is found in the database
    @Test
    @DisplayName("It should return a status code of 204 when no vehicle is found in the database")
    void deleteAutomobileByVin_noMatchingVin() throws Exception {
        doNothing().when(autoService).delete(null);
        mockMvc.perform(delete("/api/autos/" + anyLong()))
                .andExpect(status().isNoContent());
    }
}
