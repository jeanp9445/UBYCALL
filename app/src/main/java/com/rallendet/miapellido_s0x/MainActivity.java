package com.rallendet.miapellido_s0x;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    Button b1,b2;
    ImageButton equis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ed1 = (EditText) findViewById(R.id.nombre_e);
        ed2 = (EditText) findViewById(R.id.apellido_e);
        ed3 = (EditText) findViewById(R.id.ciclo_e);
        ed4 = (EditText) findViewById(R.id.descripcion_e);
        ed5 = (EditText) findViewById(R.id.imagen_e);
        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        equis = (ImageButton) findViewById(R.id.imSalir1);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(getApplicationContext(), listar.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               insert();
            }
        });

    }

    public void insert()
    {
        try
        {
            String p_nombre = ed1.getText().toString();
            String p_apellido = ed2.getText().toString();
            String p_ciclo = ed3.getText().toString();
            String p_descripcion = ed4.getText().toString();
            String p_imagen_url = ed5.getText().toString();

            ProgressDialog progressDialog =new ProgressDialog(this);
            progressDialog.setMessage("cargando");


            if (p_nombre.isEmpty()){
                Toast.makeText(this,"Ingrese nombre",Toast.LENGTH_SHORT).show();
            }else if (p_apellido.isEmpty()){
                Toast.makeText(this,"Ingrese apellido",Toast.LENGTH_SHORT).show();
            }else if (p_ciclo.isEmpty()){
                Toast.makeText(this,"Ingrese ciclo",Toast.LENGTH_SHORT).show();
            }else if (p_descripcion.isEmpty()){
                Toast.makeText(this,"Ingrese descripcion",Toast.LENGTH_SHORT).show();
            }else if (p_imagen_url.isEmpty()){
                Toast.makeText(this,"Ingrese url de imagen",Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.show();
                progressDialog.dismiss();
                StringRequest request =new StringRequest(Request.Method.POST, "http://192.168.18.5:80/crud_android2/insertar_.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                ){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String>params=new HashMap<>();
                        params.put("nombre",p_nombre);
                        params.put("apellido",p_apellido);
                        params.put("ciclo",p_ciclo);
                        params.put("descripcion",p_descripcion);
                        params.put("urlimagen",p_imagen_url);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);
            }
            Toast.makeText(this,"Estudiante registrado",Toast.LENGTH_SHORT).show();
            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");
            ed5.setText("");
            ed1.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Verificar datos a ingresar",Toast.LENGTH_SHORT).show();
        }
    }

    public void Salir(View view){
        Intent intent = new Intent(MainActivity.this, Logeo.class);
        startActivity(intent);
        finish(); // Para cerrar este activity
    }
}