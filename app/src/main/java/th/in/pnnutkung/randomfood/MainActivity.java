package th.in.pnnutkung.randomfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String filename = "foodData";
    private List<String> foodList;
    private int currentIndex;
    private File foodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void saveData(){
        FileOutputStream outputStream;
        ObjectOutputStream ous = null;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            ous = new ObjectOutputStream(outputStream);
            ous.writeObject(foodData);
        } catch (Exception e){
            Log.e("Save","Got some problem");
        } finally {
            try{
                ous.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void loadData(){
        FileInputStream inputStream;
        ObjectInputStream ois = null;
        try {
            inputStream = openFileInput(filename);
            ois = new ObjectInputStream(inputStream);
            foodList = (ArrayList<String>)ois.readObject();
            if(foodList == null){
                foodList = new ArrayList<String>();
            }
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
    }

    public void randomFoodName(View view){
        if(foodList == null) {
            foodList = new ArrayList<String>();
        }
        if(foodList.size() == 0) {
            foodList.add("กระเพราหมูสับ");
            foodList.add("คาโบนาร่า");
            foodList.add("ไข่เจียว");
            foodList.add("ข้าวต้มปลา");
            Log.i("Add","4 starter food.");
        }

        int rand = (int)Math.floor(Math.random() * foodList.size());
        if(currentIndex != rand) {
            currentIndex = rand;
            TextView textView = (TextView) findViewById(R.id.random_text_view);
            textView.setText(foodList.get(rand));
        } else {
            Log.i("Random","Got same previous index");
            randomFoodName(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode == RESULT_OK)&&(requestCode == 1)){
            Log.i("Add", "New food name = " + data.getStringExtra("name"));
            addNewFood(data.getStringExtra("name"));
        }
    }

    public void addNewFood(String food){
        foodList.add(food);
        saveData();
    }
}
