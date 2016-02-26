package th.in.pnnutkung.randomfood.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pipatpol on 2559-02-26.
 */
public class Food implements Serializable{
    private String name;
    private long createdTime;

    public Food(String name){
        this.name = name;
        this.createdTime = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public String getReadableCreatedTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm");
        Date date = new Date(this.createdTime);
        return sdf.format(date);
    }

    @Override
    public String toString() {
        return "Food name: "+this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;

        Food food = (Food) o;

        return createdTime == food.createdTime;

    }

    @Override
    public int hashCode() {
        return (int) (createdTime ^ (createdTime >>> 32));
    }
}
