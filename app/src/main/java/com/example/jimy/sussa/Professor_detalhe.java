package com.example.jimy.sussa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Professor_detalhe extends AppCompatActivity implements View.OnClickListener{

    TextView tvNome,tvAvaliar;
    String nome;
    Professor currentProfessor;
    RatingBar rbDidatica, rbCoerencia, rbDominio, rbAuxilio;
    ImageView ivProfessor;
    EditText etRatingDidatica, etRatingCoerencia, etRatingDominio, etRatingAuxilio;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_detalhe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nome = extras.getString("nome");
            //The key argument here must match that used in the other activity
        }

        if(!BDProfessores.hashProfessor.containsKey(nome))  Toast.makeText(getApplicationContext(),"Professor nao existente",Toast.LENGTH_SHORT).show();
        else currentProfessor = BDProfessores.hashProfessor.get(nome);

        try {
            ivProfessor = (ImageView) findViewById(R.id.ivImagemProfessor);
            ivProfessor.setImageResource(currentProfessor.getImagesrc());


            tvNome = (TextView) findViewById(R.id.tvNomeProfessor);
            tvNome.setText("Professor " + nome.toUpperCase());

            rbDidatica = (RatingBar) findViewById(R.id.rbDIDATICA);
            rbCoerencia = (RatingBar) findViewById(R.id.rbCOERENCIA);
            rbDominio = (RatingBar) findViewById(R.id.rbDOMINIO);
            rbAuxilio = (RatingBar) findViewById(R.id.rbAUXILIO);

            rbAuxilio.setRating(currentProfessor.getRatingAuxilio());
            rbDominio.setRating(currentProfessor.getRatingDominio());
            rbCoerencia.setRating(currentProfessor.getRatingCoerencia());
            rbDidatica.setRating(currentProfessor.getRatingDidatica());


            rbDidatica.setEnabled(false);
            rbCoerencia.setEnabled(false);
            rbDominio.setEnabled(false);
            rbAuxilio.setEnabled(false);
        }catch (NullPointerException e)
        {
            Log.d("CURRENTPROFESSOR","Professor nao existe");
            e.printStackTrace();
        }


        tvAvaliar = (TextView)findViewById(R.id.tvAvaliar);
        tvAvaliar.setOnClickListener(this);


        //AsyncTask as = new AsyncRating().execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case(R.id.tvAvaliar):
                Toast.makeText(getApplicationContext(),"Entrando tela de avaliacao",Toast.LENGTH_SHORT).show();

                LayoutInflater alertLayout = LayoutInflater.from(this);
                View view = alertLayout.inflate(R.layout.avaliacao_alertdialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder .setTitle("Avaliacao")
                        .setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                etRatingDidatica = (EditText)dialog.findViewById(R.id.etDIDATICA);
                etRatingDidatica.setSingleLine(true);
                etRatingDidatica.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                etRatingCoerencia = (EditText)dialog.findViewById(R.id.etCOERENCIA);
                etRatingCoerencia.setSingleLine(true);
                etRatingCoerencia.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);


                etRatingDominio = (EditText)dialog.findViewById(R.id.etDOMINIO);
                etRatingDominio.setSingleLine(true);
                etRatingDominio.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);


                etRatingAuxilio = (EditText)dialog.findViewById(R.id.etAUXILIO);
                etRatingAuxilio.setSingleLine(true);
                etRatingAuxilio.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);


                Button btOk = (Button)dialog.findViewById(R.id.btOK);
                Button btCancel = (Button)dialog.findViewById(R.id.btCANCELAR);

                btOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stDidatica = etRatingDidatica.getText().toString();
                        String stCoerencia = etRatingCoerencia.getText().toString();
                        String stDominio = etRatingDominio.getText().toString();
                        String stAuxilio = etRatingAuxilio.getText().toString();

                        boolean validParams = false;

                        if((stDidatica != " ")&&(stCoerencia != " ")&&(stDominio!= " ")&&(stAuxilio!=" "))
                        {
                            int numDidatica = Integer.parseInt(stDidatica);
                            int numCoerencia = Integer.parseInt(stCoerencia);
                            int numDominio = Integer.parseInt(stDominio);
                            int numAuxilio = Integer.parseInt(stAuxilio);

                            if((numDidatica < 6)&&(numCoerencia < 6)&&(numDominio< 6)&&(numAuxilio<6))
                            {
                                validParams = true;
                                currentProfessor.votos++;
                                currentProfessor.votarDidatica(numAuxilio);
                                currentProfessor.votarCoerencia(numCoerencia);
                                currentProfessor.votarDominio(numAuxilio);
                                currentProfessor.votarAuxilio(numAuxilio);


                                rbAuxilio.setRating(currentProfessor.getRatingAuxilio());
                                rbDominio.setRating(currentProfessor.getRatingDominio());
                                rbCoerencia.setRating(currentProfessor.getRatingCoerencia());
                                rbDidatica.setRating(currentProfessor.getRatingDidatica());
                            }



                        }

                        if(!validParams)    Toast.makeText(getApplicationContext(),"Insira um valor de 1 a 5!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                });
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        }
    }


    private class AsyncRating extends AsyncTask<String,String,String> {
        ProgressDialog pdLoading = new ProgressDialog(Professor_detalhe.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tCarregando...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("http://52.40.35.114/UserTestPs/Index");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder();
//                        .appendQueryParameter("Nome", params[0])
//                        .appendQueryParameter("Password", params[1]);
                String query = builder.build().getEncodedQuery();


                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            String vazio = "";
            //this method will be running on UI thread
            try {

                JSONArray jr = new JSONArray(result);
                //JSONArray jb = (JSONA)jr.getJSONObject(0);
                //JSONArray st = jb.getJSONArray("streets");
                for(int i=0;i<jr.length();i++)
                {
                    vazio += jr.getString(i);
                    //Log.i("..........", "" + street);
                    // loop and add it to array or arraylist
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),vazio,Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();
            if (result.contains("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(Professor_detalhe.this, Profile.class);
                startActivity(intent);
                Professor_detalhe.this.finish();

            } else if (result.contains("false")) {

                // If username and password does not match display a error message
                Toast.makeText(Professor_detalhe.this, "Usuario ou senha invalida", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Professor_detalhe.this, "Erro de conexao.", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(Professor_detalhe.this,result,Toast.LENGTH_SHORT).show();
        }
    }
}
