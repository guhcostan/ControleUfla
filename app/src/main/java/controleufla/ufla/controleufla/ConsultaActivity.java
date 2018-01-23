package controleufla.ufla.controleufla;

import android.app.Activity;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ConsultaActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Sala> listDataHeader;
    Sala sala1;
    private GregorianCalendar calendar;
    ArrayList<Computador> computador1;
    DatabaseReference databaseReference;
    HashMap<Sala, List<Computador>> listDataChild;
    FirebaseDatabase firebaseDatabase;

    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = new GregorianCalendar();
        setContentView(R.layout.activity_consulta);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.listaDeSalas);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        listDataHeader = new ArrayList<Sala>();
        listDataChild = new HashMap<Sala, List<Computador>>();
        databaseReference = firebaseDatabase.getReference();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int mes = calendar.get(Calendar.MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        texto = (TextView) findViewById(R.id.textView2);
        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(!listDataHeader.isEmpty()){
                    listDataHeader.clear();
                }
                if(!listDataChild.isEmpty()){
                    listDataChild.clear();
                }
                dataSnapshot = dataSnapshot.child("Salas");
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    computador1 = new ArrayList<Computador>();
                    computador1.clear();
                    int h = childDataSnapshot.child("nroDeComputadores").getValue(Integer.class);
                    int j = 0;
                    while(j < h){
                        if(childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ocupado").getValue(Boolean.class) == true) {
                            if ((childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ultimaDataUso").child("dia").getValue(Integer.class)) != dia
                                    || (childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ultimaDataUso").child("mes").getValue(Integer.class)) != mes
                                    || (((hora - childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ultimaDataUso").child("hora").getValue(Integer.class)) >= 1)
                                    && ((minuto - childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ultimaDataUso").child("minuto").getValue(Integer.class)) > 0))) {
                                DatabaseReference test = childDataSnapshot.child("computadores").child(Integer.toString(j)).getRef();
                                test.child("ocupado").setValue(false);

                            }
                        }
                        Computador novo = new Computador(j,childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ocupado").getValue(Boolean.class),childDataSnapshot.child("computadores").child(Integer.toString(j)).child("ultimoUsuario").getValue(String.class));
                        computador1.add(novo);
                        j++;
                    }
                    sala1 = new Sala(childDataSnapshot.child("nomeDaSala").getValue(String.class),childDataSnapshot.child("nroDeComputadores").getValue(Integer.class),childDataSnapshot.child("nroDeComputadoresEmUso").getValue(Integer.class),computador1);

                    listDataHeader.add(sala1);

                    listDataChild.put(sala1, computador1);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error){
                // Failed to read value
                Toast.makeText(ConsultaActivity.this, "Ocorreu um erro",Toast.LENGTH_LONG);
            }
        });

    }
}
