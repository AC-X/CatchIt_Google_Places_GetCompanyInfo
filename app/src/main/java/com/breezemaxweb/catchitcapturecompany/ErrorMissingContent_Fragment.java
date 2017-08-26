package com.breezemaxweb.catchitcapturecompany;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

/**
 * Created by user on 2017-07-17.
 * ERROR MESSAGE ALERT
 */

public class ErrorMissingContent_Fragment extends DialogFragment {
    private View form=null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form=
                getActivity().getLayoutInflater()
                        .inflate(R.layout.dialog, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return(builder.setTitle(R.string.eng_alert).setView(form)

                .setNegativeButton(android.R.string.ok, null).create());
    }


}//Class Close
