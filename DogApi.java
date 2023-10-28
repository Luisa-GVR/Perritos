import java.util.ArrayList;

public class DogApi {
    public ArrayList<String> message;
    public String status;

    public DogApi(ArrayList<String> message, String status) {
        this.message = message;
        this.status = status;
    }


    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
