package com.carr3r.waltsnspiders.lightbox;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxWin extends DialogFragment implements View.OnClickListener {

    protected OnLightboxFinishes listener;

    public void setListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_win, container,
                false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        ((Button) rootView.findViewById(R.id.closeButton)).setOnClickListener(this);

        // Do something else
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                //do your stuff
                System.exit(1);
            }
        };
    }

    public void setOnFinishListener(OnLightboxFinishes newListener) {
        listener = newListener;
    }

    @Override
    public void onClick(View v) {
        getDialog().dismiss();
        if (listener != null)
            listener.onLightBoxFinishes(LightboxWin.class);

    }
}