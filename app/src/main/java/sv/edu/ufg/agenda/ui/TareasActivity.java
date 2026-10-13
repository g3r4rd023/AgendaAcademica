package sv.edu.ufg.agenda.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import sv.edu.ufg.agenda.AgendaApp;
import sv.edu.ufg.agenda.R;
import sv.edu.ufg.agenda.dao.TareaDAO;
import sv.edu.ufg.agenda.modelo.Tarea;

public class TareasActivity extends AppCompatActivity {
    private TareaDAO tareaDAO;
    private int materiaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        tareaDAO = ((AgendaApp) getApplication()).daos().tareas();

        // Recibir datos de la pantalla anterior
        materiaId = getIntent().getIntExtra("materia_id", -1);
        String materiaNombre = getIntent().getStringExtra("materia_nombre");

        TextView tvEncabezado = findViewById(R.id.tvEncabezado);
        tvEncabezado.setText("Tareas de: " + materiaNombre);

        EditText etTitulo = findViewById(R.id.etTituloTarea);
        EditText etFecha = findViewById(R.id.etFechaEntrega);
        EditText etPrioridad = findViewById(R.id.etPrioridad);
        Button btnGuardar = findViewById(R.id.btnGuardarTarea);

        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String prioridad = etPrioridad.getText().toString().trim();

            if (!titulo.isEmpty()) {
                Tarea nueva = new Tarea(0, materiaId, titulo, fecha, prioridad);
                tareaDAO.insertar(nueva);
                Toast.makeText(this, "Tarea guardada correctamente", Toast.LENGTH_SHORT).show();
                etTitulo.setText("");
                etFecha.setText("");
                etPrioridad.setText("");
            } else {
                Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}