package controleufla.ufla.controleufla;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;
    private EditText campoLogin;
    private String loginString;
    private EditText campoSenha;
    private String senhaString;
    private Button botaoLogin;
    private Button botaoCadastro;
    private Boolean logado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        campoLogin = (EditText) findViewById(R.id.loginCampo);
        campoSenha = (EditText)  findViewById(R.id.senhaCampo);
        logado = false;
        mAuth = FirebaseAuth.getInstance();
        botaoCadastro = (Button) findViewById(R.id.cadastroBotao);
        botaoLogin = (Button) findViewById(R.id.loginButton);
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        loginString = campoLogin.getText().toString();
        senhaString = campoSenha.getText().toString();
        if(loginString != null && senhaString != null) {
            mAuth.signInWithEmailAndPassword(loginString, senhaString).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(LoginActivity.this, ServicosActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
