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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.ContentValues.TAG;

public class CadastroActivity extends Activity {

    private String Nome;
    private String Login;
    private String Senha;
    private String Matricula;
    private FirebaseAuth firebaseAuth;
    private EditText nomeCampo;
    private EditText loginCampo;
    private UserProfileChangeRequest profileUser;
    private EditText senhaCampo;
    private Button botaoCadastro;
    private EditText matriculaCampo;
    private Button sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        firebaseAuth = FirebaseAuth.getInstance();
        nomeCampo = findViewById(R.id.nomeCampo2);
        loginCampo = findViewById(R.id.emailCampo2);
        senhaCampo = findViewById(R.id.senhaCampo2);
        matriculaCampo = findViewById(R.id.numeroDeMatriculaCampo2);
        botaoCadastro = findViewById(R.id.cadastroButton2);
        sair = findViewById(R.id.sairButton2);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarDados();
                cadastrar();
            }
        });
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void pegarDados(){
        Nome = nomeCampo.getText().toString();
        Login = loginCampo.getText().toString();
        Senha = senhaCampo.getText().toString();
        Matricula = matriculaCampo.getText().toString();
    }

    private void cadastrar() {
        firebaseAuth.createUserWithEmailAndPassword(Login, Senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                profileUser = new UserProfileChangeRequest.Builder()
                        .setDisplayName(Nome + "-" + Matricula)
                        .build();

                user.updateProfile(profileUser)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CadastroActivity.this, "Conta criada com sucesso", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastroActivity.this, "Erro ao criar conta", Toast.LENGTH_LONG).show();
                nomeCampo.setText("");
                loginCampo.setText("");
                senhaCampo.setText("");
                matriculaCampo.setText("");
            }
        });
    }
}
