package com.yanir.ex113;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText TextET;
    TextView TextTV;
    Intent si;


    private final String FILENAME = "inttest.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextET = (EditText) findViewById(R.id.TextET);
        TextTV = (TextView) findViewById(R.id.TextTV);
        updateText();
    }

    /**
     * this method add the text from the edit text element to the "inttest.txt" file
     * @param reset if true it will reset the file(delete everything in the file)
     */
    public void saveText(boolean reset){
        try {
            // gett the old text
            String oldText = getText();

            // Open the file.
            FileOutputStream fOS = openFileOutput(FILENAME,MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);

            // chack if reset is true and if it is write "" so the file will reset
            if(reset){
                bW.write("");
            }
            else {
                bW.write(oldText + String.valueOf(TextET.getText()));
            }

            // close the writer
            bW.close();
            oSW.close();
            fOS.close();

        } catch (IOException e) {
            System.out.println("error saving");
        }
    }

    /**
     * this method reads the text in "inttest.txt"
     * @return a String containing the text in "inttest.txt"
     */
    public String getText(){
        try {
            // create a buffered reader
            FileInputStream fIS= openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();

            // read the first line
            String line = bR.readLine();
            String newString;

            // check if the file is null
            if (line != null) {
                // if not go for all the lines
                while (line != null) {
                    sB.append(line + "\n");
                    line = bR.readLine();
                }
                // remove the /n at the end
                newString = sB.toString().substring(0, sB.length() - 1);
            }else{
                newString = sB.toString();
            }
            bR.close();
            iSR.close();
            fIS.close();
            return newString;
        } catch (IOException e) {
            System.out.println("error getting text");
        }
        return null;
    }

    /**
     * this method reads the text from the file and set the TextView to this text
     */
    public void updateText(){
        String Text = getText();
        TextTV.setText(Text);
    }

    /**
     * this method saves the text to the file and sets the TextView to this text
     * @param v
     */
    public void saveAndUpdate(View v){
        saveText(false);
        updateText();
    }

    /**
     * this method resets the file and also updates the TextView
     * @param v
     */
    public void resetText(View v){
        saveText(true);
        updateText();
    }

    /**
     * this method saves and updates the text and also exits the application
     * @param v
     */
    public void saveAndExit(View v) {
        saveAndUpdate(v);
        finishAndRemoveTask();
    }

    /**
     * This function presents the options menu for moving between activities.
     * @param menu The options menu in which you place your items.
     * @return true in order to show the menu, otherwise false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.manu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        System.out.println(item.getTitle().toString());
        if (item.getTitle().toString().equals("credit")){
            si = new Intent(this, credit.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }

}