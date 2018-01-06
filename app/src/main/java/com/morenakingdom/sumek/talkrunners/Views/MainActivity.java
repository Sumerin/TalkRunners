package com.morenakingdom.sumek.talkrunners.Views;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.morenakingdom.sumek.talkrunners.Controllers.MainController;
import com.morenakingdom.sumek.talkrunners.Exceptions.ConnectionException;
import com.morenakingdom.sumek.talkrunners.R;

public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        final Button create_btn = findViewById(R.id.createButton);

        create_btn.setOnClickListener(e -> {
            System.out.println("Create Server Button");
            try {
                MainController.getInstance().createServer();
            } catch (ConnectionException ex) {
                ex.printStackTrace();
                builder.setTitle(ex.getClass().getSimpleName())
                        .setMessage(ex.getMessage())
                        .show();
            }
        });

        final Button join_btn = findViewById(R.id.joinButton);

        join_btn.setOnClickListener(e -> {
            System.out.println("Join Server Button");
            DialogFragment t = new ConnectDialog();
            t.show(getSupportFragmentManager(), "Connect");
        });
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
    }

    public void TestActivity(View view) {
        Intent test = new Intent( getApplicationContext(), RoomActivity.class );
        startActivity( test );
    }
}
