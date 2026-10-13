package sv.edu.ufg.agenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sv.edu.ufg.agenda.AgendaApp;
import sv.edu.ufg.agenda.R;
import sv.edu.ufg.agenda.dao.MateriaDAO;
import sv.edu.ufg.agenda.modelo.Materia;

public class MateriasActivity extends AppCompatActivity {
    private MateriaDAO materiaDAO;
    private MateriaAdapter adapter;
    private EditText etNombre, etDocente, etBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);

        // Obtener el DAO desde la App
        materiaDAO = ((AgendaApp) getApplication()).daos().materias();

        etNombre = findViewById(R.id.etNombre);
        etDocente = findViewById(R.id.etDocente);
        etBuscar = findViewById(R.id.etBuscar);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        RecyclerView rvMaterias = findViewById(R.id.rvMaterias);

        // Configurar RecyclerView
        adapter = new MateriaAdapter(new MateriaAdapter.OnMateriaClickListener() {
            @Override
            public void onTareasClick(Materia materia) {
                Intent intent = new Intent(MateriasActivity.this, TareasActivity.class);
                intent.putExtra("materia_id", materia.getId());
                intent.putExtra("materia_nombre", materia.getNombre());
                startActivity(intent);
            }

            @Override
            public void onEliminarClick(Materia materia) {
                materiaDAO.eliminar(materia.getId());
                actualizarLista();
            }
        });

        rvMaterias.setLayoutManager(new LinearLayoutManager(this));
        rvMaterias.setAdapter(adapter);

        // Botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String docente = etDocente.getText().toString().trim();
            if (!nombre.isEmpty()) {
                Materia nueva = new Materia(0, nombre, docente);
                materiaDAO.insertar(nueva);
                etNombre.setText("");
                etDocente.setText("");
                actualizarLista();
            } else {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            }
        });

        // Barra de Búsqueda
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setMaterias(materiaDAO.buscarPorNombre(s.toString()));
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        actualizarLista();
    }

    private void actualizarLista() {
        adapter.setMaterias(materiaDAO.listar());
    }
}