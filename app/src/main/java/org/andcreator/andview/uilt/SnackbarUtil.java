package org.andcreator.andview.uilt;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.andcreator.andview.R;


public class SnackbarUtil {

    public SnackbarUtil(Context context, View views, String content){

        Snackbar snackbar = Snackbar.make(views, content, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.white));
        snackbar.show();

    }
}
