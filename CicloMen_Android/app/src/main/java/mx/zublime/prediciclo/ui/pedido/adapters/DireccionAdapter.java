package mx.zublime.prediciclo.ui.pedido.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.models.Direccion;
import mx.zublime.prediciclo.data.models.Shipping;

public class DireccionAdapter  extends RecyclerView.Adapter<DireccionAdapter.ViewHolder> {

    private List<Shipping> listaDirecciones;
    private EliminarDireccionListener  eliminarDireccionListener;

    public DireccionAdapter(List<Shipping> listaDirecciones,EliminarDireccionListener  eliminarDireccionListener){
        this.listaDirecciones = listaDirecciones;
        this.eliminarDireccionListener = eliminarDireccionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_direccion,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaDirecciones.get(position),eliminarDireccionListener,position);
    }

    @Override
    public int getItemCount() {
        return listaDirecciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_direccion_title)
        TextView tvTitle;
        @BindView(R.id.tv_direcion)
        TextView tvDireccion;
        @BindView(R.id.tv_cp)
        TextView tvCp;
        @BindView(R.id.btn_eliminar)
        ImageView btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Shipping shipping, EliminarDireccionListener eliminarDireccionListener, int position) {
            tvTitle.setText("Dirección"+ " " + String.valueOf(position + 1));
            tvDireccion.setText(shipping.getAddress_1());
            tvCp.setText("C.P:" + " "+shipping.getPostcode());
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarDireccionListener.eliminarDireccion(position);
                }
            });
        }

    }
    public void eliminarBeneficiario(int position){
        listaDirecciones.remove(position);
        //notifyItemRangeChanged(position,beneficiariosDataset.size());
    }
}
