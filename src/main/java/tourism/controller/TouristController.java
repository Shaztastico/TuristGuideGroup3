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

        TouristAttraction createdAttraction = new TouristAttraction(attraction.getName(), attraction.getDescription());
        service.addAttraction(createdAttraction.getName(), createdAttraction.getDescription());
        return new ResponseEntity<TouristAttraction>(createdAttraction, HttpStatus.CREATED);
    }


    @PostMapping("/update")
    public ResponseEntity<AttractionUpdate> update(@RequestBody AttractionUpdate update){
        // must include oldName, otherwise we don't know what to update
        if (update.getOldName() == null){
            return new ResponseEntity<AttractionUpdate>((AttractionUpdate) null, HttpStatus.BAD_REQUEST);
        }

        // Look up the existing attraction
        TouristAttraction found = service.findAttractionByName(update.getOldName());

        // If the attraction doesn't exist, we can't update it
        if (found == null){
            return new ResponseEntity<AttractionUpdate>((AttractionUpdate) null, HttpStatus.NOT_FOUND);
        }

        // Prevent renaming to an already existing attraction name
        // ATTN: There is a design conflict: if oldName and newName fields are the same
        // and the user actually only wanted to update the description of an existing attraction
        // this check prevents that user case.
        for (TouristAttraction attraction : service.getAttractions()){
            if (update.getNewName() != null && update.getNewName().equalsIgnoreCase(attraction.getName())){
                // reject update if the newName is already taken
                return new ResponseEntity<AttractionUpdate>(update, HttpStatus.BAD_REQUEST);
            }
        }

        // Case 1: Only updating the description: (newName is missing or empty)
        if(update.getNewName() == null || update.getNewName().isEmpty()){
            found.setDescription(update.getNewDescription());
            return new ResponseEntity<AttractionUpdate>(update, HttpStatus.OK);
        }

        // Case 2: Only updating the name (newDescription is missing or empty)
        if(update.getNewDescription() == null || update.getNewDescription().isEmpty()){
            found.setName(update.getNewName());
            return new ResponseEntity<AttractionUpdate>(update, HttpStatus.OK);
        }
        else {

            // Case 3: Updating both name and description
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
