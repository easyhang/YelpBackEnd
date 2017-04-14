package Model;

/**
 * Created by leon on 4/14/17.
 */
public class Restrurant {
    private String name;
    private String address;
    private String type;
    private int rate;

    public void setRestrurantName(String name) {
        this.name = name;
    }

    public String getRestrurantName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
    
}
