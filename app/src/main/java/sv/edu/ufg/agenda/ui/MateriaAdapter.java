package sv.edu.ufg.agenda.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ufg.agenda.R;
import sv.edu.ufg.agenda.modelo.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder> {
    private List<Materia> lista = new ArrayList<>();
    private final OnMateriaClickListener listener;

    public interface OnMateriaClickListener {
        void onTareasClick(Materia materia);
        void onEliminarClick(Materia materia);
    }

    public MateriaAdapter(OnMateriaClickListener listener) {
        this.listener = listener;
    }

    public void setMaterias(List<Materia> materias) {
        this.lista = materias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia, parent, false);
        return new MateriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        Materia materia = lista.get(position);
        holder.tvTexto.setText(materia.getNombre() + " - " + materia.getDocente());

        holder.btnTareas.setOnClickListener(v -> listener.onTareasClick(materia));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(materia));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MateriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto;
        Button btnTareas, btnEliminar;

        public MateriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTexto = itemView.findViewById(R.id.tvTexto);
            btnTareas = itemView.findViewById(R.id.btnTareas);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}