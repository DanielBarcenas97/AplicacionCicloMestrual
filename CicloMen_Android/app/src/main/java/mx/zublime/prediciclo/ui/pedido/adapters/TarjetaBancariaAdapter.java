package mx.zublime.prediciclo.ui.pedido.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.models.Shipping;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;

public class TarjetaBancariaAdapter extends RecyclerView.Adapter<TarjetaBancariaAdapter.ViewHolder> {

    List<TarjetaBancaria> listTarjetas;
    private EliminarTarjetaListener eliminarTarjetaListener;
    public TarjetaBancariaAdapter(List<TarjetaBancaria> listTarjetas, EliminarTarjetaListener eliminarTarjetaListener ){
        this.listTarjetas = listTarjetas;
        this.eliminarTarjetaListener = eliminarTarjetaListener;
    }

    @NonNull
    @Override
    public TarjetaBancariaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarjeta,parent,false);
        return new TarjetaBancariaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarjetaBancariaAdapter.ViewHolder holder, int position) {
        holder.bind(listTarjetas.get(position),eliminarTarjetaListener,position);
    }

    @Override
    public int getItemCount() {
        return listTarjetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_numero_cuenta)
        TextView tvNumCuenta;
        @BindView(R.id.img_btn_eliminar_tarjeta)
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(TarjetaBancaria tarjeta, EliminarTarjetaListener eliminarTarjetaListener, int position) {
            tvNumCuenta.setText(tarjeta.getNumeroTarjeta());
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarTarjetaListener.eliminarTarjeta(position);
                }
            });
        }


    }

    public void eliminarTarjeta(int position){
        listTarjetas.remove(position);
    }
}
