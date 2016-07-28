package com.carr3r.waltsnspiders;

import android.content.res.AssetManager;

import com.carr3r.waltsnspiders.visual.DrawingUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Neto on 27/10/2015.
 */
public class Quiz {

    public static final String TAG = "Quiz";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    protected static JSONArray db;
    protected int level;
    protected String description;
    protected String answers[];
    protected int rightAnswer;

    public String getDefaultDB() {
        AssetManager manager = Environment.getInstance().getContext().getAssets();
        try {
            InputStream file = manager.open("json/en.json");
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        } catch (Exception e) {
            return "";
        }
    }

    public String getDB() {
        AssetManager manager = Environment.getInstance().getContext().getAssets();
        try {
            InputStream file = manager.open("json/" + Environment.getInstance().getLocale().getLanguage().toLowerCase().replace("_", "-") + ".json");
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public Quiz(int level) {
        JSONObject element;
        try {

            if (db == null)
                db = new JSONArray(getDB());

            element = db.getJSONObject(DrawingUtils.randomInt(0, db.length() - 1));


            description = element.getString("q");
            JSONArray a = element.getJSONArray("a");

            Vector<String> candidates = new Vector<String>();
            candidates.add(a.getString(0));
            candidates.add(a.getString(1));
            candidates.add(a.getString(2));
            candidates.add(a.getString(3));
            Collections.shuffle(candidates);
            answers = new String[4];
            answers[0] = candidates.remove(0);
            answers[1] = candidates.remove(0);
            answers[2] = candidates.remove(0);
            answers[3] = candidates.remove(0);
            for (int i = 0; i < answers.length; i++)
                if (answers[i].startsWith("*")) {
                    rightAnswer = i + 1;
                    answers[i] = answers[i].substring(1);
                    break;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.level = level;
    }


}
