package th.in.pnnutkung.randomfood.models;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pipatpol on 2559-02-26.
 */
public class Storage {
    static final String filename = "foodData";
    private List<Food> savedFoods;
    private static Storage instance;

    private Storage(){
        savedFoods = new ArrayList<Food>();
    }

    public static Storage getInstance(){
        if(instance == null) instance = new Storage();
        return instance;
    }

    public void saveFood(Context context,Food food) {
        this.savedFoods.add(food);
        FileOutputStream outputStream;
        ObjectOutputStream ous = null;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ous = new ObjectOutputStream(outputStream);
            ous.writeObject(savedFoods);
        } catch (Exception e) {
            Log.e("Save", "Got some problem");
        } finally {
            try {
                ous.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Food> loadFoods(Context context){
        if(savedFoods == null){
            savedFoods = new ArrayList<Food>();
        }
        FileInputStream inputStream;
        ObjectInputStream ois = null;
        try {
            inputStream = context.openFileInput(filename);
            ois = new ObjectInputStream(inputStream);
            savedFoods = (ArrayList<Food>)ois.readObject();

        } catch (ClassNotFoundException cnfe){
            Log.e("Load food","Serialization problem.");
        } catch (IOException ioe) {
            Log.e("Load food","No food file.");
        } finally {
            try {
                if(ois != null) {
                    ois.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return this.savedFoods;
    }

    public List<Food> getFoods(){
        return this.savedFoods;
    }
}