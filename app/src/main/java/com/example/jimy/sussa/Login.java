package com.example.jimy.sussa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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

public class Login extends AppCompatActivity implements View.OnClickListener{

    private Button bEntrar;
    private TextView tvCadastrar;
    private TextView tvTitulo;

    BDManager bdManager = new BDManager();
    EditText etlogin, etsenha;
    String login, senha;
    UserBuilder userBuilder = new UserBuilder();

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        etlogin = (EditText)findViewById(R.id.etLogin);
        etsenha = (EditText)findViewById(R.id.etSenha);
        bEntrar = (Button)findViewById(R.id.bEntrar);
        tvCadastrar = (TextView)findViewById(R.id.tvCadastrar);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);


        tvTitulo.setGravity(Gravity.CENTER);

        bEntrar.setOnClickListener(this);
        tvCadastrar.setOnClickListener(this);
    }

    //// TODO: 10/11/15  Criar DB para usuarios/senhas
    @Override
    public void onClick(View v) {
        login = etlogin.getText().toString();
        senha = etsenha.getText().toString();

        switch (v.getId()) {
            case R.id.bEntrar:
                userBuilder.setCurrentUser(login, "", "", senha, "", null, null); //limpeza ao retornar a activity
                //offline
                authentication(login,senha);
                //authentication2(login,senha);  //autenticacao hardcoded (sem bd)

                //online
                //new AsyncLogin().execute(login,senha);
                break;
            case R.id.tvCadastrar:
                Toast.makeText(this,"Redirecionando a tela de cadastro", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,Cadastro.class));
        }
    }

    private class AsyncLogin extends AsyncTask<String,String,String> {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
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
                url = new URL("http://52.40.35.114/Login/TryLogin");

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
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("User", params[0])
                        .appendQueryParameter("Password", params[1]);
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

            //this method will be running on UI thread

            pdLoading.dismiss();
            if (result.contains("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(Login.this, Profile.class);
                startActivity(intent);
                Login.this.finish();

            } else if (result.contains("false")) {

                // If username and password does not match display a error message
                Toast.makeText(Login.this, "Usuario ou senha invalida", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Login.this, "Erro de conexao.", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(Login.this,result,Toast.LENGTH_SHORT).show();
        }
    }

    private void authentication(String login, String senha){
        if(login.equals("professor")){
            Toast.makeText(this,"Professor nao eh autorizado aqui!", Toast.LENGTH_SHORT).show();
        }
        else if(!BDUsers.hashUsers.containsKey(login))
            Toast.makeText(this,"Usuario nao encontrado",Toast.LENGTH_SHORT).show();
        else {
            User u = BDUsers.hashUsers.get(login);
            if(u.getSenha().equals(senha)){
                userBuilder.setCurrentUser(u.getNome(),u.getUsuario(),u.getEmail(),u.getSenha(),u.getCurso(),u.getMatrizBCT_fragment(),u.getMatrizBCC_fragment());
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
            else    Toast.makeText(this,"Senha incorreta",Toast.LENGTH_SHORT).show();
        }
    }

    private void authentication2(String login, String senha) {
        if(login.equals("")&&senha.equals("")){
            Toast.makeText(this, "Campos Incompletos", Toast.LENGTH_SHORT).show();
            //// TODO: 11/11/15 Linha para debug, tirar na versao final
            startActivity(new Intent(this, Profile.class));
        }
        else if((login.equals("aluno")|| login.equals("aluno@unifesp.br"))
        &&(senha.equals("unifesp"))
                ) {
            Toast.makeText(this,"Redirecionando..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Profile.class));
            //// TODO: 12/11/15 Ver forma melhor para apagar os campos apos o sucesso na autenticacao
            etlogin.setText("");
            etsenha.setText("");
        }else if(login.equals("professor")){
            Toast.makeText(this,"Professor nao eh autorizado aqui!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Usuario/Senha Incorreta", Toast.LENGTH_SHORT).show();
        }
    }
}
