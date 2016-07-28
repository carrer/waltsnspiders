package com.carr3r.waltsnspiders;

import com.carr3r.waltsnspiders.visual.Score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wneto on 06/11/2015.
 */
public class CloudDatabase {

    public static String TAG = "CloudDatabase";
    public static String applicationId = "1657688597838026";

    public static String performPostCall(String requestURL, String postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "0";

            }
        } catch (Exception e) {
            response = "0";
        }

        return response;
    }

    public static List<UserBean> getRanking() {
        Map<String, String> face = Environment.getInstance().getFacebookData();
        if (face.get("name").length() != 0) {
            Score score = Environment.getInstance().getHighestScore();
            Random randomno = new Random();
            long value = randomno.nextLong();
            String json = removeAccents("{\"s\":\"" + String.valueOf(value) + "\",\"p\":\"" + score.getPoints() + "\",\"l\":\"" + score.getLevel() + "\",\"u\":\"" + face.get("userId") + "\",\"n\":\"" + face.get("name")  + "\",\"t\":\"" + face.get("accessToken") + "\"}");
            String r = performPostCall("http://waltsnspiders.carr3r.com/r.php", encrypt(json));
            if (r.length() > 1)
                return UserBean.parseJSON(decrypt(r));
        }

        return null;
    }

    public static String removeAccents(String input)
    {
        return Normalizer
                .normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static String encrypt(String text) {
        MCrypt crypt = new MCrypt();
        String out;
        text = HtmlUtils.escapeHtml(text.trim()).toString();
        try {
            out = MCrypt.bytesToHex(crypt.encrypt(text));
        } catch (Exception e) {
            e.printStackTrace();
            out = null;
        }
        return out;
    }

    public static String decrypt(String text) {
        MCrypt crypt = new MCrypt();
        String out;
        try {
            out = new String(crypt.decrypt(text.trim()));
        } catch (Exception e) {
            out = null;
        }
        return out;
    }

}


