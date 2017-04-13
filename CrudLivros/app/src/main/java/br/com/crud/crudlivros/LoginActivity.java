package br.com.crud.crudlivros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.crud.crudlivros.dao.UsuarioDAO;
import br.com.crud.crudlivros.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    //private final String LOGIN_DEFAULT = "android";
    //private final String SENHA_DEFAULT = "123";

    public static final String KEY_APP_PREFERENCES = "APP_PREFERENCES";
    public static final String KEY_LOGIN = "login";

    private TextInputLayout tilLogin;
    private TextInputLayout tilSenha;
    private CheckBox cbManterConectado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);

        usuarioDAO = new UsuarioDAO(this);

        if (isConectado()) {
            iniciarApp();
        }
    }

    //Método que será chamado no onclick do botao
    public void logar(View v) {
        if (isLoginValido()) {
            if (cbManterConectado.isChecked()) {
                manterConectado();
            }
            iniciarApp();
        } else {
            Toast.makeText(this, "Inválido", Toast.LENGTH_LONG).show();
        }
    }

    private UsuarioDAO usuarioDAO;

    // Valida o login
    private boolean isLoginValido() {
        String login = tilLogin.getEditText().getText().toString();
        String senha = tilSenha.getEditText().getText().toString();

        Usuario usuario = usuarioDAO.findUsuario(login, senha);

        // if (login.equals(LOGIN_DEFAULT) && senha.equals(SENHA_DEFAULT)) {
        if (usuario != null) {
            return true;
        } else
            return false;
    }

    private void manterConectado() {
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if (login.equals(""))
            return false;
        else
            return true;
    }

    private void iniciarApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
