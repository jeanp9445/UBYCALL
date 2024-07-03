package com.rallendet.miapellido_s0x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CCuenta extends AppCompatActivity {

    EditText edCo, edPa, edPPa;
    Button bCr;
    String ultimoIdAgente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccuenta);

        edCo = findViewById(R.id.edC);
        edPa = findViewById(R.id.edP);
        edPPa = findViewById(R.id.edPP);
        bCr = findViewById(R.id.btC);

        // Obtener el último idAgente al iniciar la actividad
        obtenerUltimoIdAgente();

        bCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    private void obtenerUltimoIdAgente() {
        String url = "http://192.168.18.5:80/crud_android2/obtener_ultimo_id.php"; // Asegúrate de que la URL sea correcta
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ultimoIdAgente = response.getString("ultimo_id");
                            Log.d("CCuenta", "Último idAgente obtenido: " + ultimoIdAgente);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CCuenta.this, "Error al obtener idAgente", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CCuenta.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void insert() {
        try {
            String p_correo = edCo.getText().toString();
            String p_password = edPa.getText().toString();
            String p_passwordC = edPPa.getText().toString();

            if (ultimoIdAgente == null || ultimoIdAgente.isEmpty()) {
                Toast.makeText(this, "idAgente no disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cargando");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.18.5:80/crud_android2/insertar2_.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(CCuenta.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), Logeo.class));
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CCuenta.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("correo", p_correo);
                    params.put("password", p_password);
                    params.put("idAgente", ultimoIdAgente); // Utiliza el idAgente dinámico obtenido
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(CCuenta.this);
            requestQueue.add(request);

            edCo.setText("");
            edPa.setText("");
            edPPa.setText("");
        } catch (Exception ex) {
            Toast.makeText(this, "Verificar datos a ingresar", Toast.LENGTH_SHORT).show();
        }
    }
}
