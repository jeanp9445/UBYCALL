package com.rallendet.miapellido_s0x;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class editar extends AppCompatActivity {

    EditText ed0,ed1,ed2,ed3,ed4,ed5;
    Button b1,b2,b3;
    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);

        ed0 = (EditText) findViewById(R.id.id);

        ed1 = (EditText) findViewById(R.id.nombre_e);
        ed2 = (EditText) findViewById(R.id.apellido_e);
        ed3 = (EditText) findViewById(R.id.ciclo_e);
        ed4 = (EditText) findViewById(R.id.descripcion_e);
        ed5 = (EditText) findViewById(R.id.imagen_e);

        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        b3 = (Button) findViewById(R.id.bt3);
        Intent i = getIntent();
        position=i.getExtras().getInt("position");
        Toast.makeText(editar.this, "paso el "+position, Toast.LENGTH_SHORT).show();
        ed0.setText(listar.listaUsuarios.get(position).getId());
        ed1.setText(listar.listaUsuarios.get(position).getNombre());
        ed2.setText(listar.listaUsuarios.get(position).getApellido());
        ed3.setText(listar.listaUsuarios.get(position).getCiclo());
        ed4.setText(listar.listaUsuarios.get(position).getDescripcion());
        ed5.setText(listar.listaUsuarios.get(position).getUrlimagen());


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar(ed0.getText().toString());
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),detalle.class).putExtra("position",position));
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
    }

    public  void Eliminar(final  String id){
        StringRequest request =new StringRequest(Request.Method.POST, "http://192.168.18.5:80/crud_android2/eliminar_.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("datos eliminados")) {
                            Toast.makeText(editar.this, "elimino correctamente", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), listar.class));
                            finish();
                        } else {
                            Toast.makeText(editar.this, "Error no se puede registrar", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editar.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>params=new HashMap<>();
                params.put("id",id);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(editar.this);
        requestQueue.add(request);

    }

    public void Update()
    {
        String id=ed0.getText().toString().trim();
        String p_nombre=ed1.getText().toString().trim();
        String p_apellido=ed2.getText().toString().trim();
        String p_ciclo=ed3.getText().toString().trim();
        String p_descripcion=ed4.getText().toString().trim();
        String p_imagen_url=ed5.getText().toString().trim();


        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");


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
            StringRequest request =new StringRequest(Request.Method.POST, "http://192.168.18.5:80/crud_android2/actualizar_.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(editar.this, "Actualizo correctamente", Toast.LENGTH_SHORT).show();
                            progressDialog.show();
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), listar.class));
                            finish();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(editar.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            ){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>params=new HashMap<>();
                    params.put("id",id);
                    params.put("nombre",p_nombre);
                    params.put("apellido",p_apellido);
                    params.put("ciclo",p_ciclo);
                    params.put("descripcion",p_descripcion);
                    params.put("urlimagen",p_imagen_url);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(editar.this);
            requestQueue.add(request);


    }
}
}