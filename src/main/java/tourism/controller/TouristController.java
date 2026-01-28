package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService service;

    public TouristController(TouristService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TouristAttraction>> getAllAttractions(){
        return new ResponseEntity<>(service.getAttractions(), HttpStatus.OK);
    }


    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> findAttractionByName(@PathVariable String name){
        ResponseEntity<TouristAttraction> response = new ResponseEntity<TouristAttraction>(service.findAttractionByName(name), HttpStatus.OK);

        return response;
    }



    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Controller is working!!", HttpStatus.OK);
    }


    @PostMapping //TODO FIX THIS!!!
    public ResponseEntity<TouristAttraction> update(@RequestBody(required = true) String name, String newName, String newDescription){
        service.updateAttraction(name,newName,newDescription);
        return new ResponseEntity<TouristAttraction>(service.findAttractionByName(newName), HttpStatus.OK);
    }



}
