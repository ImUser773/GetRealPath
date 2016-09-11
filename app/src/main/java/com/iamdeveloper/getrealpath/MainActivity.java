package com.iamdeveloper.getrealpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_SOMETHING = 1;
    private TextView textURI,textPath;
    private Button buttonGetSomething;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onBindView();
        buttonGetSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
            }
        });

    }

    private void onBindView(){
        textURI = (TextView) findViewById(R.id.txt_uri);
        textPath = (TextView) findViewById(R.id.txt_path);
        buttonGetSomething = (Button) findViewById(R.id.btn_get_something);
    }

    private void getContent(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i,PICK_SOMETHING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_SOMETHING){
                Log.d("PICK SOMETHING",data.getData().toString());
                String realPath = GetRealPath.getRealPathFromAPI19(this,data.getData());
                Log.i("realPath",realPath+"");
                if(realPath == null){
                    realPath = GetRealPath.getRealPathFromAPI11_to18(this,data.getData());
                }
                Log.i("realPath",realPath+"");
                if (realPath==null){
                    realPath = GetRealPath.getRealPathFromSD_CARD(data.getData());
                }
                Log.i("realPath",realPath+"");

                textURI.setText(data.getData().toString());
                textPath.setText(realPath);

            }
        }
    }
}
