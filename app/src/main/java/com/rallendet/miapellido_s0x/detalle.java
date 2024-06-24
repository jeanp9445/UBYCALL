package com.rallendet.miapellido_s0x;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class detalle extends AppCompatActivity {

    TextView detalleNom, detalleApe, detalleCic, detalleDes;
    ImageView detalleImagen;

    Button b1;

    private RequestQueue rq;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        b1 = (Button) findViewById(R.id.bt1);

        detalleNom=findViewById(R.id.detalleNom);
        detalleApe=findViewById(R.id.detalleApe);
        detalleCic=findViewById(R.id.detalleCic);
        detalleDes=findViewById(R.id.detalleDes);
        detalleImagen = findViewById(R.id.detalleImage);
        rq= Volley.newRequestQueue(this);

        Intent intent=getIntent();
        position=intent.getExtras().getInt("position");


        detalleNom.setText(listar.listaUsuarios.get(position).getNombre());
        detalleApe.setText(listar.listaUsuarios.get(position).getApellido());
        detalleCic.setText(listar.listaUsuarios.get(position).getCiclo());
        detalleDes.setText(listar.listaUsuarios.get(position).getDescripcion());
        cargarImagenUrl(listar.listaUsuarios.get(position).getUrlimagen());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),editar.class).putExtra("position",position));
            }
        });


    }

    private void cargarImagenUrl(String url) {
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        detalleImagen.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Manejar errores de carga de imagen, si es necesario
                    }
                });
        // Agregar la solicitud a la cola de solicitudes de Volley
        rq.add(request);
    }


}