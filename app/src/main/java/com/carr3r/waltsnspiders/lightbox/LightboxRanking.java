package com.carr3r.waltsnspiders.lightbox;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.carr3r.waltsnspiders.CloudDatabase;
import com.carr3r.waltsnspiders.Environment;
import com.carr3r.waltsnspiders.R;
import com.carr3r.waltsnspiders.SoundController;
import com.carr3r.waltsnspiders.UserBean;
import com.carr3r.waltsnspiders.listeners.OnLightboxFinishes;
import com.carr3r.waltsnspiders.styled.StyledButton;
import com.carr3r.waltsnspiders.visual.Score;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wneto on 27/10/2015.
 */
public class LightboxRanking extends DialogFragment implements View.OnClickListener {

    public static String TAG = "LightboxRanking";
    protected OnLightboxFinishes listener;
    protected LoginButton loginButton;

    protected android.support.v7.widget.GridLayout rankingList;
    protected CallbackManager callbackManager;
    protected Context fragContext;
    protected TextView tdNameTitle;
    protected int maxWidth = 0;

    public LightboxRanking() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lightbox_ranking, container,
                false);

        fragContext = getContext();

        SoundController.getInstance().playBackgroundSound(SoundController.background_menu);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().getWindow().setDimAmount(0f);
        getDialog().setCanceledOnTouchOutside(false);

        ((Button) rootView.findViewById(R.id.btnClose)).setOnClickListener(this);

        FacebookSdk.sdkInitialize(getContext());

        rankingList = (android.support.v7.widget.GridLayout) rootView.findViewById(R.id.list);
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "user_friends", "email");
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        tdNameTitle = (TextView) rootView.findViewById(R.id.nameColumn);
        maxWidth = tdNameTitle.getWidth();


        AccessTokenTracker tracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Environment.getInstance().registerFacebook("", "", "");
                    Environment.getInstance().registerHighestScore(null);
                    doLocal();
                }
            }
        };
        tracker.startTracking();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Environment.getInstance().registerFacebook(loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken(), "");
                (new DatabaseUpdater()).execute(Environment.getInstance().getHighestScore());

            }

            @Override
            public void onCancel() {
                // App code
                (Toast.makeText(getContext(), getString(R.string.facebook_cancel), Toast.LENGTH_LONG)).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                (Toast.makeText(getContext(), getString(R.string.facebook_error), Toast.LENGTH_LONG)).show();
            }
        });

        if (Environment.getInstance().getFacebookData().get("accessToken").length() > 0)
            (new DatabaseUpdater()).execute(Environment.getInstance().getHighestScore());

        doLocal();

        // Do something else
        return rootView;
    }

    public void doLocal() {
        clearTable();
        Score high = Environment.getInstance().getHighestScore();
        Map<String, String> data = Environment.getInstance().getFacebookData();
        if (data.get("name").length() > 0)
            addRank("?", data.get("name"), String.valueOf(high.getLevel()), String.valueOf(high.getPoints()), Color.YELLOW, Color.BLACK);
        else
            addRank("?", fragContext.getString(R.string.you), String.valueOf(high.getLevel()), String.valueOf(high.getPoints()), Color.YELLOW, Color.BLACK);

    }

    public void clearTable() {
        rankingList.removeViews(4, rankingList.getChildCount() - 4);
    }

    public void addRank(String position, String name, String level, String points, int backgroundColor, int textColor) {

        StyledButton tdPosition = new StyledButton(fragContext);
        tdPosition.setTextColor(textColor);
        tdPosition.setTextSize(14);
        tdPosition.setPadding(5, 5, 5, 5);
        android.support.v7.widget.GridLayout.LayoutParams layoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        tdPosition.setGravity(Gravity.RIGHT);
        tdPosition.setLayoutParams(layoutParams);
        tdPosition.setBackgroundColor(backgroundColor);
        tdPosition.setText(position);

        StyledButton tdName = new StyledButton(fragContext);
        tdName.setVerticalScrollBarEnabled(true);
        tdName.setTextSize(14);
        tdName.setTextColor(textColor);
        tdName.setPadding(5, 5, 5, 5);

        layoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        layoutParams.width = maxWidth;
        tdName.setGravity(Gravity.LEFT);
        tdName.setLayoutParams(layoutParams);
        tdName.setMaxLines(1);
        tdName.setBackgroundColor(backgroundColor);
        tdName.setText(name);

        StyledButton tdLevel = new StyledButton(fragContext);
        tdLevel.setTextColor(textColor);
        tdLevel.setTextSize(14);
        tdLevel.setPadding(5, 5, 5, 5);
        layoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        tdLevel.setGravity(Gravity.RIGHT);
        tdLevel.setLayoutParams(layoutParams);
        tdLevel.setBackgroundColor(backgroundColor);
        tdLevel.setText(level);

        StyledButton tdPoints = new StyledButton(fragContext);
        tdPoints.setTextColor(textColor);
        tdPoints.setTextSize(14);
        tdPoints.setPadding(5, 5, 5, 5);

        layoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);

        tdPoints.setGravity(Gravity.RIGHT);
        tdPoints.setLayoutParams(layoutParams);
        tdPoints.setBackgroundColor(backgroundColor);
        tdPoints.setText(points);

        rankingList.addView(tdPosition);
        rankingList.addView(tdName);
        rankingList.addView(tdLevel);
        rankingList.addView(tdPoints);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {

                getDialog().dismiss();
                if (listener != null)
                    listener.onLightBoxFinishes(LightboxRanking.class);

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
            listener.onLightBoxFinishes(LightboxRanking.class);
    }

    private class DatabaseUpdater extends AsyncTask<Score, Boolean, Boolean> {

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;

        public DatabaseUpdater() {
            dialog = new ProgressDialog(getContext());
        }

        protected List<UserBean> ranking;
        protected volatile boolean running;
        protected Map<String, String> face;

        protected void onPreExecute() {
            this.dialog.setIndeterminate(true);
            this.dialog.show();
        }

        protected void onPostExecute(Boolean result) {

            dialog.dismiss();
            if (result) {
                clearTable();

                Iterator<UserBean> it = ranking.iterator();
                while (it.hasNext()) {
                    UserBean bean = (UserBean) it.next();
                    if (bean.getUserId().equals(face.get("userId"))) {
                        if (Environment.getInstance().getHighestScore().getPoints() < bean.getPoints()) {
                            Score score = new Score(false);
                            score.setLevel(bean.getLevel());
                            score.setPoints(bean.getPoints());
                            Environment.getInstance().registerHighestScore(score);
                        }
                        addRank(String.valueOf(bean.getPosition()), bean.getName(), String.valueOf(bean.getLevel()), String.valueOf(bean.getPoints()), Color.YELLOW, Color.BLACK);
                    } else
                        addRank(String.valueOf(bean.getPosition()), bean.getName(), String.valueOf(bean.getLevel()), String.valueOf(bean.getPoints()), Color.TRANSPARENT, Color.WHITE);
                }
            } else
                Toast.makeText(getContext(), getString(R.string.connection_problem), Toast.LENGTH_LONG);
        }

        @Override
        protected Boolean doInBackground(Score... params) {

            running = true;

            face = Environment.getInstance().getFacebookData();
            final AccessToken token = new AccessToken(face.get("accessToken"), CloudDatabase.applicationId, face.get("userId"), null, null, null, null, null);
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + token.getUserId(),
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {

                            if (response.getError() != null && response.getError().getErrorCode() > 0) {
                                Environment.getInstance().registerFacebook("", "", "");
                            } else
                                try {
                                    JSONObject resp = response.getJSONObject();
                                    if (resp != null && resp.has("name"))
                                        Environment.getInstance().registerFacebook(token.getUserId(), token.getToken(), resp.getString("name"));
                                } catch (JSONException e) {
                                }
                            running = false;

                        }
                    }
            ).executeAsync();

            while (running) {
            }

            ranking = CloudDatabase.getRanking();
            return ranking != null;
        }
    }

}