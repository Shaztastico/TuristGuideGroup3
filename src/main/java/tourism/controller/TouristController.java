package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.model.UpdateRequest;
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



    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Controller is working!!", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction){
        if(attraction.getName().isEmpty() || attraction.getDescription().isEmpty()){
            return new ResponseEntity<TouristAttraction>((TouristAttraction) null, HttpStatus.BAD_REQUEST);
        }
        if(attraction.getName() == null || attraction.getDescription() == null){
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
    public ResponseEntity<UpdateRequest> update(@RequestBody UpdateRequest request){
        if (request.getOldName() == null){
            return new ResponseEntity<UpdateRequest>((UpdateRequest) null, HttpStatus.BAD_REQUEST);
        }

        TouristAttraction found = service.findAttractionByName(request.getOldName());

        if (found == null){
            return new ResponseEntity<UpdateRequest>((UpdateRequest) null, HttpStatus.NOT_FOUND);
        }

        if(request.getNewName() == null || request.getNewName().isEmpty()){
            found.setDescription(request.getNewDescription());
            return new ResponseEntity<UpdateRequest>(request, HttpStatus.OK);
        }

        if(request.getNewDescription() == null || request.getNewDescription().isEmpty()){
            found.setName(request.getNewName());
            return new ResponseEntity<UpdateRequest>(request, HttpStatus.OK);
        }
        else {

            found.setName(request.getNewName());
            found.setDescription(request.getNewDescription());

            return new ResponseEntity<UpdateRequest>(request, HttpStatus.OK);
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
