package com.volsu.gis.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.volsu.gis.R;
import com.volsu.gis.util.Sendmail;
import com.volsu.maplibui.dialog.NGDialog;

public class SendMessageDialog extends NGDialog {
    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_send_mail, null);
        builder.setTitle(R.string.contact_us)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Sendmail mSendmail = new Sendmail();
                        StringBuilder str = new StringBuilder();
                        TextView mName = view.findViewById(R.id.sendmail_edit_name);
                        TextView mMessage = view.findViewById(R.id.sendmail_edit_message);
                        str.append(getString(R.string.name)).append(":\n").append(mName.getText()).append('\n');
                        str.append(getString(R.string.message)).append(":\n").append(mMessage.getText()).append('\n');
                        mSendmail.sendMail(getActivity(), str.toString());
                        //Context context = getActivity().getApplicationContext();
                        //Toast.makeText(getActivity().getApplicationContext(), R.string.send_message_coming_soon, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
