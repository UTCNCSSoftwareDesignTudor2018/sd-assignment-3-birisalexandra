package BlogApp.BlogApp.communication;

import java.io.Serializable;

public class Mapper implements Serializable {

    private String message;
    private String object;

    public Mapper(String message, String object) {
        this.message = message;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
