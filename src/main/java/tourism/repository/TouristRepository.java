package tourism.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

@Repository
public class TouristRepository {
    private ArrayList<TouristAttraction> attractions = new ArrayList<>();
    
    public TouristRepository(){
        addSampleToList();
    }

    public void addSampleToList(){
        TouristAttraction a1 = new TouristAttraction("Dolphin Show", "A show featuring acrobatic dolphins");
        TouristAttraction a2 = new TouristAttraction("Cat Cafe", "A coffee shop chock full of cats to pet and play with");
        attractions.add(a1);
        attractions.add(a2);
    }

    public ArrayList<TouristAttraction> getAttractions() {
        return attractions;
    }
}
