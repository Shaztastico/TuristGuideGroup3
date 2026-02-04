package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.model.AttractionUpdate;
import tourism.service.TouristService;
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

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction){

        if(attraction.getName() == null || attraction.getDescription() == null){
            return new ResponseEntity<TouristAttraction>((TouristAttraction) null, HttpStatus.BAD_REQUEST);
        }
        if(attraction.getName().isEmpty() || attraction.getDescription().isEmpty()){
            return new ResponseEntity<TouristAttraction>((TouristAttraction) null, HttpStatus.BAD_REQUEST);
        }
        for (TouristAttraction a : service.getAttractions()){
            if (attraction.getName().equalsIgnoreCase(a.getName())){
                return new ResponseEntity<TouristAttraction>(attraction, HttpStatus.BAD_REQUEST);
            }
        }

        service.addAttraction(attraction.getName(), attraction.getDescription());
        return new ResponseEntity<TouristAttraction>(attraction, HttpStatus.CREATED);
    }



    @PostMapping("/update")
    public ResponseEntity<AttractionUpdate> update(@RequestBody AttractionUpdate update){
        if (update.getOldName() == null){
            return new ResponseEntity<AttractionUpdate>((AttractionUpdate) null, HttpStatus.BAD_REQUEST);
        }

        TouristAttraction found = service.findAttractionByName(update.getOldName());

        if (found == null){
            return new ResponseEntity<AttractionUpdate>((AttractionUpdate) null, HttpStatus.NOT_FOUND);
        }

        for (TouristAttraction attraction : service.getAttractions()){
            if (update.getNewName() != null && update.getNewName().equalsIgnoreCase(attraction.getName())){
                return new ResponseEntity<AttractionUpdate>(update, HttpStatus.BAD_REQUEST);
            }
        }

        if(update.getNewName() == null || update.getNewName().isEmpty()){
            found.setDescription(update.getNewDescription());
            return new ResponseEntity<AttractionUpdate>(update, HttpStatus.OK);
        }

        if(update.getNewDescription() == null || update.getNewDescription().isEmpty()){
            found.setName(update.getNewName());
            return new ResponseEntity<AttractionUpdate>(update, HttpStatus.OK);
        }
        else {

            found.setName(update.getNewName());
            found.setDescription(update.getNewDescription());

            return new ResponseEntity<AttractionUpdate>(update, HttpStatus.OK);
        }
    }

     @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        TouristAttraction found = service.findAttractionByName(name);
        if (found == null){
            return new ResponseEntity<>(name+" Not Found", HttpStatus.NOT_FOUND);
        }
        service.deleteAttraction(name);
        return new ResponseEntity<String>("Deleted "+name, HttpStatus.OK);
     }



}
