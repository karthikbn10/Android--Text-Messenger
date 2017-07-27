package com.apppartner.androidprogrammertest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.y;
import static android.R.id.message;
import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends ActionBarActivity
{
    EditText username;
    EditText password;
    private static final String TAG = "AppAPartner";
    long startTime;
    long endTime;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        username = (EditText) findViewById(R.id.username_TextView);
        password = (EditText) findViewById(R.id.password_TextView);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Jelloween - Machinato.ttf");

        username.setTypeface(custom_font);
        password.setTypeface(custom_font);



    }

    public void onLoginButtonClicked(View view)

    {

        username = (EditText) findViewById(R.id.username_TextView);
        password = (EditText) findViewById(R.id.password_TextView);

        if((username.getText().toString().length() < 1) && (password.getText().toString().length() < 1))
        {
            Toast.makeText(this,"please enter both Username and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {

            new MySyncTask().execute(username.getText().toString(),password.getText().toString());
        }

    }



    private class MySyncTask extends AsyncTask<String,Integer, Double>
    {

        @Override
        protected Double doInBackground(String... params) {

            postData(params[0],params[1]);

            return null;
        }


        @Override
        protected void onPostExecute(Double aDouble) {


        }




        public void postData(String Username_retrieved, String password_retrieved)
        {

            try
            {

                String postReceiverUrl = "http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php";
                Log.v(TAG, "postURL: " + postReceiverUrl);

                // HttpClient
                HttpClient httpClient = new DefaultHttpClient();

                // post header
                HttpPost httpPost = new HttpPost(postReceiverUrl);

                // add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", Username_retrieved));
                nameValuePairs.add(new BasicNameValuePair("password", password_retrieved));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                startTime = System.nanoTime();
                // execute HTTP post request
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();
                endTime = System.nanoTime();

                if (resEntity != null) {

                    final String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v(TAG, "Response: " + responseStr);

                    new Thread()
                    {
                        public void run()
                        {
                            LoginActivity.this.runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    StringBuilder stringBuilder = new StringBuilder("");
                                    String str = responseStr.replaceAll("[{}]","");
                                    stringBuilder.append(str);
                                    long duration = (endTime - startTime)/1000000;
                                    stringBuilder.append("   | Time taken: " + duration + "ms");

                                    new AlertDialog.Builder(LoginActivity.this)
                                            .setTitle("Login")
                                            .setMessage(stringBuilder.toString())
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent myIntent = new Intent(((Dialog) dialog).getContext(), MainActivity.class);
                                                    startActivity(myIntent);
                                                    return;
                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();

                                }
                            });
                        }
                    }.start();

                }

            }


            catch (IOException ex)
            {
                new Thread()
                {
                    public void run()
                    {
                        LoginActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(),"IO  Exception has occured:",Toast.LENGTH_SHORT).show();                               }
                        });
                    }
                }.start();


            }

            catch (Exception e)
            {
                new Thread()
                {
                    public void run()
                    {
                        LoginActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(),"Exception has occured:",Toast.LENGTH_SHORT).show();                               }
                        });
                    }
                }.start();


            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }
}
