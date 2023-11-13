package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import w.kipu.kipu.R;

public class DialogAddProductFormatControl extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_add_product_format_control,null);
        builder.setView(view);

        return super.onCreateDialog(savedInstanceState);
    }

}