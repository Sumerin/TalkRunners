package com.morenakingdom.sumek.talkrunners.Views;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.morenakingdom.sumek.talkrunners.Controllers.MainController;
import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.R;

/**
 * Created by sumek on 1/1/18.
 */

public class ConnectDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_ip_address, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.connect, (dialog, id) -> {
                    System.out.println( "Connect Pressed" );
                    final EditText txt = view.findViewById(R.id.IP);
                    try {
                        MainController.getInstance().connect(txt.getText().toString());
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    dialog.cancel();
                });
        return builder.create();
    }
}