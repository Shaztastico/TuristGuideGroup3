package tourism.model;

public class UpdateRequest {
    private String oldName;
    private String newName;
    private String newDescription;

    public String getOldName(){
        return oldName;
    }

    public String getNewName(){
        return newName;
    }

    public String getNewDescription(){
        return newDescription;
    }

}
