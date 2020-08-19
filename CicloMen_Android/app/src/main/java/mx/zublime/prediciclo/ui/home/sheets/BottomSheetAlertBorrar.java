package mx.zublime.prediciclo.ui.home.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class BottomSheetAlertBorrar extends BottomSheetDialogFragment {

    @BindView(R.id.aceptar_button) MaterialButton mAceptarButton;
    @BindView(R.id.cancelar_button) MaterialButton mCancelarButton;

    private Unbinder mUnbinder;
    private ISelectedButtonCommunication mListener;

    public BottomSheetAlertBorrar(ISelectedButtonCommunication listener)
    {
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_alert_limpiar, container, false);
        mUnbinder = ButterKnife.bind(this,view);
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
