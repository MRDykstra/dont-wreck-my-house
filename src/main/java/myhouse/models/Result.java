package myhouse.models;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private List<String> errorMessages =  new ArrayList<>();

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }

    public boolean isSuccessful() {
        return errorMessages.size() == 0;
    }

    public List<String> getErrorMessages() {

        return new ArrayList<>(errorMessages);
    }

}
