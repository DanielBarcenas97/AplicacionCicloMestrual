package mx.zublime.prediciclo.ui.home.sheets;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class BottomSheetSolicitarPrueba extends BottomSheetDialogFragment
{

    @BindView(R.id.positivo_imageview) ImageView mPositivoImageview;
    @BindView(R.id.positivo_textview) TextView mPositivoTextview;
    @BindView(R.id.negativo_imageview) ImageView mNegativoImageview;
    @BindView(R.id.negativo_textview) TextView mNegativoTextview;
    @BindView(R.id.invalido_imageview) ImageView mInvalidoImageview;
    @BindView(R.id.invalido_textview) TextView mInvalidoTextview;
    @BindView(R.id.confirmar_button) MaterialButton mConfirmarButton;

    private Unbinder mUnbinder;
    private ISelectedButtonCommunication mListener;
    private boolean mSelected;

    public BottomSheetSolicitarPrueba(ISelectedButtonCommunication listener)
    {
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bottom_sheet_prueba, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mConfirmarButton.setEnabled(false);
        return view;
    }

    @Override
    public void onDestroyView()
    {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.confirmar_button)
    public void onGuardarButtonClicked()
    {
        mListener.onResultSavePrueba(mSelected);
        this.dismiss();
    }

    @OnClick(R.id.positivo_imageview)
    public void onPositivoImageviewClicked()
    {
        mConfirmarButton.setEnabled(true);
        mSelected = true;
        mInvalidoTextview.setTextColor(getResources().getColor(R.color.negro));
        mNegativoTextview.setTextColor(getResources().getColor(R.color.negro));
        mPositivoTextview.setTextColor(getResources().getColor(R.color.colorPrimary));
        mPositivoTextview.setTypeface(Typeface.DEFAULT_BOLD);
        mNegativoTextview.setTypeface(Typeface.DEFAULT);
        mInvalidoTextview.setTypeface(Typeface.DEFAULT);
    }

    @OnClick(R.id.negativo_imageview)
    public void onNegativoImageviewClicked()
    {
        mConfirmarButton.setEnabled(true);
        mSelected = false;
        mInvalidoTextview.setTextColor(getResources().getColor(R.color.negro));
        mNegativoTextview.setTextColor(getResources().getColor(R.color.colorPrimary));
        mPositivoTextview.setTextColor(getResources().getColor(R.color.negro));
        mNegativoTextview.setTypeface(Typeface.DEFAULT_BOLD);
        mPositivoTextview.setTypeface(Typeface.DEFAULT);
        mInvalidoTextview.setTypeface(Typeface.DEFAULT);
    }

    @OnClick(R.id.invalido_imageview)
    public void onInvalidoImageviewClicked()
    {
        mConfirmarButton.setEnabled(true);
        mSelected = false;
        mInvalidoTextview.setTextColor(getResources().getColor(R.color.colorPrimary));
        mNegativoTextview.setTextColor(getResources().getColor(R.color.negro));
        mPositivoTextview.setTextColor(getResources().getColor(R.color.negro));
        mInvalidoTextview.setTypeface(Typeface.DEFAULT_BOLD);
        mPositivoTextview.setTypeface(Typeface.DEFAULT);
        mNegativoTextview.setTypeface(Typeface.DEFAULT);
    }

}
