package w.kipu.kipu.login.Produccion.ui.mainProduccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import w.kipu.kipu.R;

public class MenuProcesosPendientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_procesos_pendientes);

        BottomNavigationView navView = findViewById(R.id.nav_viewProcesPendi);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_pendiente, R.id.navigation_procesos)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragmentProcesPendi);
        NavigationUI.setupWithNavController(navView, navController);


    }
}