package w.kipu.kipu.login.Produccion.DialogSerie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import w.kipu.kipu.R;

public class AppDialogSerie extends DialogFragment {

    Button btn_regSerie;
    EditText txt_title;
    EditText txt_numSer,txt_serie;
    String nser,ser;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_app_dialog_serie,null);
        builder.setView(view);
        txt_numSer = view.findViewById(R.id.txt_numSerieAPPDialogSer);
        txt_serie = view.findViewById(R.id.txt_serieAppDialogSer);
        txt_title = view.findViewById(R.id.txt_titleAppDialogSerie);
        btn_regSerie = view.findViewById(R.id.btn_registrarSerieFormatoControl);

        nser = txt_numSer.getText().toString();
        ser = txt_serie.getText().toString();

        return builder.create();
    }
}