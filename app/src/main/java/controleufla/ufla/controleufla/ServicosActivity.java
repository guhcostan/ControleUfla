package controleufla.ufla.controleufla;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServicosActivity extends Activity {

    private Button consultarBotao;
    private Button escaniarBotao;
    private DatabaseReference database;
    private Button sairBotao;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);
        consultarBotao = findViewById(R.id.consultaButton);
        escaniarBotao = findViewById(R.id.escaniarButton);
        sairBotao = findViewById(R.id.sairButton);
        consultarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicosActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        });
        escaniarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("Salas/DCC-02/computadores/0/ocupado").setValue(false);
            }
        });
        sairBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(ServicosActivity.this, LoginActivity.class);
                mAuth = FirebaseAuth.getInstance();
                startActivity(intent);
            }
        });
    }
}
