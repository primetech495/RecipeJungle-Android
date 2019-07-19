package com.prime.redef.view;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.prime.redef.core.Action1;

public class OptionsDialog {
    private String[] options;
    private int[] ids;
    private Context context;
    private AlertDialog dialog;
    private Action1<Integer> clickListener;
    private String title;

    public OptionsDialog(@NonNull Context context, @NonNull String[] options, @NonNull int[] ids, String title) {
        if (options.length != ids.length)
            throw new IllegalArgumentException("options.length != ids.length");
        this.options = options;
        this.ids = ids;
        this.context = context;
        this.title = title;
        init();
    }

    private void init() {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        if (title != null)
            b.setTitle(title);
        b.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickListener != null)
                    clickListener.action(ids[which]);
                dialog.dismiss();
            }
        });

        dialog = b.create();
    }

    public void show() {
        dialog.show();
    }

    public void setClickListener(Action1<Integer> clickListener) {
        this.clickListener = clickListener;
    }
}
