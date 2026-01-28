package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.ArrayList;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService(TouristRepository repository){
        this.repository = repository;
    }

    public void addAttraction(String name, String description){
        repository.addAttraction(name, description);
    }

    public void deleteAttraction(String name){
        repository.deleteAttraction(name);
    }

    public void updateAttraction(String name, String newName, String newDescription){
        repository.updateAttraction(repository.findAttractionByName(name), newName, newDescription);
    }

    public ArrayList<TouristAttraction> getAttractions(){
        return repository.getAttractions();
    }

    public TouristAttraction findAttractionByName(String name){
        return repository.findAttractionByName(name);
    }





}
