package controleufla.ufla.controleufla;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServicosActivity extends Activity {

    private Button consultarBotao;
    private Button reservarBotao;
    private Button escaniarBotao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);
        consultarBotao = findViewById(R.id.consultaButton);
        reservarBotao = findViewById(R.id.reservaButton);
        escaniarBotao = findViewById(R.id.escaniarButton);
        consultarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicosActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        });
        reservarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // reservar();
            }
        });
        escaniarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //escaniar();
            }
        });
    }
}
