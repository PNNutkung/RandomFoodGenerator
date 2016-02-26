package th.in.pnnutkung.randomfood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.in.pnnutkung.randomfood.R;
import th.in.pnnutkung.randomfood.models.Food;
import th.in.pnnutkung.randomfood.controllers.Storage;

public class MainActivity extends AppCompatActivity {

    private int currentIndex;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents(){
        foodList = new ArrayList<Food>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshFoods();
    }

    public void randomFoodName(View view){
        foodList = Storage.getInstance().getFoods();
        Log.i("Random","ArrayList size"+foodList.size());
        if(foodList.size() <= 1) {
            Storage.getInstance().saveFood(this,new Food("กระเพราหมูสับ"));
            Storage.getInstance().saveFood(this, new Food("คาโบนาร่า"));
            Storage.getInstance().saveFood(this, new Food("ไข่เจียว"));
            Storage.getInstance().saveFood(this, new Food("ข้าวต้มปลา"));
            Log.i("Add","4 starter food.");
        }

        int rand = (int)Math.floor(Math.random() * foodList.size());
        if(currentIndex != rand) {
            currentIndex = rand;
            TextView textView = (TextView) findViewById(R.id.random_text_view);
            textView.setText(foodList.get(rand).getName());
        } else if ( foodList.size() > 1){
            Log.i("Random","Got same previous index");
            randomFoodName(view);
        }
    }

    public void goToAddNewFood(View view){
        Intent intent = new Intent(this, addNewFood.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode == RESULT_OK)&&(requestCode == 1)){
            Log.i("Add", "New food name = " + data.getStringExtra("name"));
            addNewFood(data.getStringExtra("name"));
        }
    }

    private void refreshFoods(){
        foodList.clear();
        for (Food food:
                Storage.getInstance().loadFoods(this)) {
            foodList.add(food);
        }
    }

    private void addNewFood(String food){
        Storage.getInstance().saveFood(this, new Food(food));
    }
}
