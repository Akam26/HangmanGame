package com.example.akam.s198596_s198572;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Language extends Activity implements OnClickListener {
    Button button;
   //******HER1***********
    final String LANGUAGE = "Language";
    final String NOR = "nor";
    final String ENG = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        LanguageValg.loadLang(this);

        final Button norsk =(Button)findViewById(R.id.button3);
        final Button engelsk =(Button)findViewById(R.id.button2);
        //norsk.setTypeface(MainActivity.tf);
        //engelsk.setTypeface(MainActivity.tf);
        norsk.setOnClickListener(this);
        engelsk.setOnClickListener(this);

       /* final Context context = this;
        button =(Button)findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, strings.xml);
                //startActivity(intent);
            }
        });*/
    }
    ///*********HERRRRRR***************
    @Override
   public void onClick(View v) {
        switch(v.getId()){
            case R.id.button3:
                LanguageValg.changeLang(NOR, this);
                LanguageValg.loadLang(this);
                break;

            case R.id.button2:
                LanguageValg.changeLang(ENG, this);
                LanguageValg.loadLang(this);
                break;
        }
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        System.exit(0);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

}
