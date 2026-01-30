package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.model.UpdateRequest;
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
        TouristAttraction found = service.findAttractionByName(name);
        if (found == null){
            return new ResponseEntity<TouristAttraction>((TouristAttraction) null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(found);
    }



    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Controller is working!!", HttpStatus.OK);
    }

    // null check
    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction){
        TouristAttraction createdAttraction = new TouristAttraction(attraction.getName(), attraction.getDescription());
        service.addAttraction(createdAttraction.getName(), createdAttraction.getDescription());
        return new ResponseEntity<TouristAttraction>(createdAttraction, HttpStatus.valueOf(201));
    }



    @PostMapping("/update")
    public ResponseEntity<UpdateRequest> update(@RequestBody UpdateRequest attraction){
        if (service.findAttractionByName(attraction.getOldName()) != null){
            service.findAttractionByName(attraction.getOldName()).setName(attraction.getNewName());
            service.findAttractionByName(attraction.getNewName()).setDescription(attraction.getNewDescription());
        }

        return new ResponseEntity<UpdateRequest>(attraction, HttpStatus.OK);
    }

     @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        service.deleteAttraction(name);
        return new ResponseEntity<String>(name, HttpStatus.OK);
     }



}
