package th.in.pnnutkung.randomfood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import th.in.pnnutkung.randomfood.R;

public class addNewFood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getNewFood(View view){
        EditText editText = (EditText) findViewById(R.id.new_food_edit_text);
        String getText = editText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("name",getText);
        setResult(RESULT_OK, intent);
        finish();
    }
}
