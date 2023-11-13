package w.kipu.kipu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListaNotaCompra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_nota_compra);
        getSupportActionBar().hide();
    }
}