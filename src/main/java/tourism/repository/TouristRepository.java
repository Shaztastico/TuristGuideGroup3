package tourism.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

@Repository
public class TouristRepository {
    private final ArrayList<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository(){
        addSampleToList();
    }

    public void addSampleToList(){
        TouristAttraction a1 = new TouristAttraction("Dolphin Show", "A show featuring acrobatic dolphins");
        TouristAttraction a2 = new TouristAttraction("Cat Cafe", "A coffee shop chock full of cats to pet and play with");
        attractions.add(a1);
        attractions.add(a2);
    }

    private TouristAttraction createAttraction(String name, String description){
        return new TouristAttraction(name, description);
    }

    public ArrayList<TouristAttraction> getAttractions() {
        return attractions;
    }

    public void addAttraction(String name, String description){
        attractions.add(createAttraction(name, description));
    }

    public TouristAttraction findAttractionByName(String name){
        for(TouristAttraction attraction : attractions){
            if (attraction.getName().equalsIgnoreCase(name)){
                return attraction;
            }
        }
        return null;
    }

    public void deleteAttraction(String name){
        attractions.remove(findAttractionByName(name));
    }

    public void updateAttraction(TouristAttraction attraction, String newName, String newDescription){
        attraction.setName(newName);
        attraction.setDescription(newDescription);
    }



}
