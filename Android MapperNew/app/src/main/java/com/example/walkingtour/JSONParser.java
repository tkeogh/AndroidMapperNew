package com.example.walkingtour;


import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.util.Log;

/** Class used to send the JSON objects and recieve the response from the server, does not work perfectly but well enough to send
 * 
 * @author ww3ref
 *
 */

/* This class was mainly hacked together from outside sources, these are listed below. While the sites post them they do not credit
 * the original source as themselves so it is possible some of the code came from elsewhere. All comments within the code are of my own to show
 * my understanding.
 * 
 * Source 1 : http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
 * Source 2 : http://stackoverflow.com/questions/18268873/correct-way-to-post-json-data-from-android-to-php
 * Source 3 : https://github.com/stefanklumpp/Simple-HTTP-Client/tree/master/src/com/devstream/http
 * 
 */

public class JSONParser {
        private static final String TAG = "HttpClient";

        public static JSONObject SendHttpPost(String URL, JSONObject jsonObjSend) {  //takes URL and the json to be sent

                try {
                        DefaultHttpClient httpclient = new DefaultHttpClient();
                        HttpPost httpPostRequest = new HttpPost(URL);   //start new client and post request

                        StringEntity se;
                        se = new StringEntity(jsonObjSend.toString()); //Get the json as a string to be sent

                        
                        httpPostRequest.setEntity(se);  //set the item to be sent
                        httpPostRequest.setHeader("json", "application/json"); //header info about how its being sent

                        long t = System.currentTimeMillis();  //just used to measure response time in testing
                        HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest); //executing the send
                        Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]"); //logging the response time
                        //Logging response time became useful when sending images as to figure what was best
                }
                catch (Exception e)
                {
                       
                        e.printStackTrace();
                }
                return null;
        }
}

       