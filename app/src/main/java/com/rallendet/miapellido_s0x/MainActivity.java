package com.rallendet.miapellido_s0x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4, ed5;
    Button b1, b2;
    ImageButton equis;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.nombre_e);
        ed2 = findViewById(R.id.apellido_e);
        ed3 = findViewById(R.id.ciclo_e);
        ed4 = findViewById(R.id.descripcion_e);
        ed5 = findViewById(R.id.imagen_e);
        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);
        equis = findViewById(R.id.imSalir1);

        progressDialog = new ProgressDialog(this);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), listar.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndInsert();
            }
        });
    }

    private void validateAndInsert() {
        String p_nombre = ed1.getText().toString();
        String p_apellido = ed2.getText().toString();
        String p_ciclo = ed3.getText().toString();
        String p_descripcion = ed4.getText().toString();
        String p_imagen_url = ed5.getText().toString();

        if (p_nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_apellido.isEmpty()) {
            Toast.makeText(this, "Ingrese apellido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_ciclo.isEmpty()) {
            Toast.makeText(this, "Ingrese ciclo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_descripcion.isEmpty()) {
            Toast.makeText(this, "Ingrese descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_imagen_url.isEmpty()) {
            Toast.makeText(this, "Ingrese url de imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Validando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.18.5:80/crud_android2/insertar_.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(MainActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), CCuenta.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", p_nombre);
                params.put("apellido", p_apellido);
                params.put("ciclo", p_ciclo);
                params.put("descripcion", p_descripcion);
                params.put("urlimagen", p_imagen_url);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    public void Salir(View view) {
        Intent intent = new Intent(MainActivity.this, Logeo.class);
        startActivity(intent);
        finish();
    }
}
