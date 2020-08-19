package mx.zublime.prediciclo.ui.home.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.ui.home.interfaz.ISelectedButtonCommunication;

public class BottomSheetConfirmationDialog extends BottomSheetDialogFragment {

    @BindView(R.id.aceptar_button) MaterialButton mAceptarButton;
    @BindView(R.id.cancelar_button) MaterialButton mCancelarButton;
    @BindView(R.id.pregunta_borrar_textview)
    TextView pregunta_borrar_textview;
    @BindView(R.id.descripcion_pregunta_textview)
    TextView descripcion_pregunta_textview;

    private Unbinder mUnbinder;
    private ISelectedButtonCommunication mListener;
    private String title = "Por favor confirma está acción";
    private String message = "¿Estás segura que deseas hacer esta acción?";

    public BottomSheetConfirmationDialog(ISelectedButtonCommunication listener)
    {
        this.mListener = listener;
    }

    public BottomSheetConfirmationDialog(ISelectedButtonCommunication listener, String message)
    {
        this.mListener = listener;
        this.message = message;
    }

    public BottomSheetConfirmationDialog(ISelectedButtonCommunication listener, String message, String title)
    {
        this.mListener = listener;
        this.title = title;
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_confirmation_dialog, container, false);
        mUnbinder = ButterKnife.bind(this,view);

        pregunta_borrar_textview.setText(title);
        descripcion_pregunta_textview.setText(message);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.aceptar_button)
    public void onAceptarButtonClicked()
    {
        mListener.onResultFragmentClicked(true);
        this.dismiss();
    }

    @OnClick(R.id.cancelar_button)
    public void onCancelarButtonClicked()
    {
        mListener.onResultFragmentClicked(false);
        this.dismiss();
    }

}
