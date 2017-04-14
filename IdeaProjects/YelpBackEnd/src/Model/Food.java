package Model;

/**
 * Created by leon on 4/14/17.
 */
public class Food {
    private String name;
    private int restrurantId;

    public void setFoodName(String foodname) {
        this.name = foodname;
    }

    public String getFoodName() {
        return name;
    }

    public void setRestrurantId(int restrurantId) {
        this.restrurantId = restrurantId;
    }

    public int getRestrurantId() {
        return restrurantId;
    }
}
