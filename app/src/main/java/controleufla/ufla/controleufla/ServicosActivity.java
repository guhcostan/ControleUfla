package controleufla.ufla.controleufla;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServicosActivity extends Activity {

    private Button consultarBotao;
    private Button escaniarBotao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private GregorianCalendar calendar;
    private Button sairBotao;
    private FirebaseAuth mAuth;
    private Button sobreBotao;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);
        consultarBotao = findViewById(R.id.consultaButton);
        escaniarBotao = findViewById(R.id.escaniarButton);
        sairBotao = findViewById(R.id.sairButton);
        calendar = new GregorianCalendar();
        sobreBotao = findViewById(R.id.sobreButton);
        sobreBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServicosActivity.this,"Programa criado por Gustavo Costa, Gustavo Reis e Danilo Guarizzo", Toast.LENGTH_LONG).show();
            }
        });
        consultarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicosActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        });
        sairBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                SharedPreferences.Editor editor = getSharedPreferences("INFORMACOES_LOGIN_AUTOMATICO", MODE_PRIVATE).edit();
                editor.putBoolean("logado", false);
                editor.commit();
                Intent intent = new Intent(ServicosActivity.this, LoginActivity.class);
                mAuth = FirebaseAuth.getInstance();
                startActivity(intent);
            }
        });
    }

    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            Toast.makeText(ServicosActivity.this, "No Scanner Found", Toast.LENGTH_LONG).show();
        }
    }

    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                reference = FirebaseDatabase.getInstance().getReference().child("Salas").child(contents);
                reference.child("ocupado").setValue(true);
                mAuth = FirebaseAuth.getInstance();
                reference.child("ultimoUsuario").setValue(mAuth.getCurrentUser().getDisplayName());

                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);

                //reference.child(contents).child("ultimaDataUso").setValue(calendar);
                reference.child("ultimaDataUso").child("hora").setValue(hora);
                reference.child("ultimaDataUso").child("minuto").setValue(minuto);
                reference.child("ultimaDataUso").child("mes").setValue(mes);
                reference.child("ultimaDataUso").child("dia").setValue(dia);
                Toast toast = Toast.makeText(this, "Computador " + contents + " foi conectado com sucesso", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.string.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
