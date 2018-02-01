package controleufla.ufla.controleufla;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalhesComp extends Activity {

    private Button pesquisar;
    private EditText nomeSala;
    private EditText nComp;
    private TextView detalhesComp;
    FirebaseDatabase firebaseDatabase;
    private Computador comp;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_comp);
        pesquisar = (Button) findViewById(R.id.botaoDetalhe);
        nomeSala = (EditText) findViewById(R.id.nomeSala);
        nComp = (EditText) findViewById(R.id.nComp);
        comp = new Computador();
        firebaseDatabase = FirebaseDatabase.getInstance();
        detalhesComp = findViewById(R.id.detalhesComp);
        databaseReference = firebaseDatabase.getReference();
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        comp = dataSnapshot.child("Salas").child(nomeSala.getText().toString().trim()).child("computadores").child(nComp.getText().toString()).getValue(Computador.class);
                        dataSnapshot = dataSnapshot.child("Salas").child(nomeSala.getText().toString()).child("computadores").child(nComp.getText().toString()).child("ultimaDataUso");
                        String ultimaData ="\n   hora:" + dataSnapshot.child("hora").getValue().toString() + "\n   minuto: " + dataSnapshot.child("minuto").getValue().toString()  + "\n   dia: " +  dataSnapshot.child("dia").getValue().toString()  + "\n   mes: " +  dataSnapshot.child("mes").getValue().toString();
                        detalhesComp.setText("id: " + comp.getId() + "\nOcupado: " + comp.isOcupado() + "\nUltimo usuario: " + comp.getUltimoUsuario()+ "\nUltimo horario usado:" + ultimaData);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
