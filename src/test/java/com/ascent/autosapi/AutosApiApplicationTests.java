package com.ascent.autosapi;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations= "classpath:application-test.properties")
class AutosApiApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AutosRepository autosRepository;

    Random r = new Random();
    ArrayList<Automobile> automobiles;

    @BeforeEach
    void setup() {
        automobiles = new ArrayList<>();
        String[] colors = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Indigo"};
        String[] make = {"Ford", "Mazda", "Dodge", "Tesla", "BMW", "Mercedes", "Nissan"};
        IntStream.range(1, 26).forEach((num) -> {
            automobiles.add(new Automobile(make[r.nextInt(7)], colors[r.nextInt(7)]));
        });
        autosRepository.saveAll(automobiles);
    }

    @AfterEach
    void teardown() {
        autosRepository.deleteAll();
    }

    @Test
    void contextLoads() {
      //  ResponseEntity<Automobile> response = restTemplate.getForEntity("/api/autos", Automobile.class);
        ResponseEntity<Automobile[]> response = restTemplate.getForEntity("/api/autos", Automobile[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }
}
