package Flagg.app.src.main.java.com.example.akam.flagg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b;
        (b=(Button)findViewById(R.id.flagg_button))
                .setBackgroundDrawable(this.getResources()
                .getDrawable(R.drawable.flag));
    }

}
