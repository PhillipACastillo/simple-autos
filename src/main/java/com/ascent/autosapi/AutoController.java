package com.ascent.autosapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AutoController {
    private AutoService autoService;

    // Constructor injection ensures that the autoservice exists
    public AutoController (AutoService autoservice) {
        this.autoService = autoservice;
    }

    @GetMapping("/autos")
    public ResponseEntity<ArrayList<Automobile>> returnAutomobiles(@RequestParam(required = false) String color,
        @RequestParam(required = false) String make) {
        ArrayList<Automobile> automobileList;

         if (color != null && make == null) {
            automobileList = autoService.getAllAutos(color);
        } else if (color == null && make != null ) {
             automobileList = autoService.getAllAutos(make);
        } else {
            automobileList = autoService.getAllAutos();
        }
        return (automobileList.size() == 0) ? ResponseEntity.noContent().build() // Builds response entity with no body
                : ResponseEntity.ok(automobileList);
//        return color == null ? autoService.getAllAutos() : autoService.getAllAutosWithCriteria(color);
    }

    private ResponseEntity noAutomobile() throws Exception {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
        @PostMapping("")
        public HttpStatus createAutomobile(@RequestBody Automobile automobile) {
            if (autoService.createNewAuto(automobile)) {
                return HTTPStatus.OK
            }
        }
     */

//    @PostMapping("")
//    public ResponseEntity<Automobile> createAutomobile(@RequestBody Automobile automobile) {
//        Automobile serviceAutomobile = autoService.createNewAuto(automobile);
//        System.out.println("---- " + serviceAutomobile + " ----");
//        if (serviceAutomobile != null) {
//            return ResponseEntity.ok(automobile);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping("/autos")
    public Automobile createAutomobile(@RequestBody Automobile automobile) throws InvalidAutoException {
            return autoService.createNewAuto(automobile);
    }

    @GetMapping("/autos/{vin}")
    public ResponseEntity<Automobile> getAutoByVin(@PathVariable Long vin) {
        Automobile auto = autoService.getAutoByVin(vin);

        if (auto != null) {
            return ResponseEntity.ok(auto);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/autos/{vin}")
    public ResponseEntity<Automobile> updateAuto(@PathVariable Long vin, @RequestBody Map<String, Object> fields) throws InvalidAutoException {
        Automobile auto = autoService.updateAuto(vin, fields);

        if (auto != null) {
            return ResponseEntity.ok(auto);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/autos/{vin}")
    public ResponseEntity<?> deleteAuto(@PathVariable Long vin) {
        return autoService.deleteByVin(vin) ? ResponseEntity.accepted().build()
                : ResponseEntity.noContent().build();
    }

}
