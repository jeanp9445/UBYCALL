package com.rallendet.miapellido_s0x;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rallendet.miapellido_s0x.databinding.ActivityListarBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class listar extends AppCompatActivity implements OnItemClickListener{
    ActivityListarBinding binding;

    ImageButton boton_imagen;
    public static ArrayList<Estudiante> listaUsuarios;
    private RequestQueue rq;
    private RecyclerView lst1;
    private AdaptadorUsuario adaptadorUsuario;

    String id , nombre, apellido, ciclo, descripcion,foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        binding = ActivityListarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        boton_imagen=findViewById(R.id.imageButton);
        lst1=findViewById(R.id.lst1);



          boton_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(listar.this, "Registrar Datos", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });




        listaUsuarios=new ArrayList<>();
        rq= Volley.newRequestQueue(this);

        cargarPersona();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        lst1.setLayoutManager(linearLayoutManager);
        adaptadorUsuario=new AdaptadorUsuario();
        lst1.setAdapter(adaptadorUsuario);


    }

    private void cargarPersona() {
        String url="http://192.168.18.5:80/crud_android2/mostrar_.php";
        JsonObjectRequest requerimiento=new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String valor=response.get("datos").toString();
                            JSONArray arreglo=new JSONArray(valor);
                            JSONArray jsonArray =response.getJSONArray("datos");

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject objeto = new JSONObject(arreglo.get(i).toString());
                                 id = objeto.getString("id");
                                 ciclo = objeto.getString("ciclo");
                                 nombre = objeto.getString("nombre");
                                 foto = objeto.getString("urlimagen");
                                 apellido=objeto.getString("apellido");
                                 descripcion=objeto.getString("descripcion");

                                Estudiante usuario=new Estudiante(id,nombre,apellido, ciclo, descripcion,foto);
                                listaUsuarios.add(usuario);
                                adaptadorUsuario.notifyItemRangeInserted(listaUsuarios.size(), i+1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(requerimiento);
    }

    @Override
    public void onItemClick(Estudiante estudiante) {
    Toast.makeText(this, "HOla",Toast.LENGTH_LONG).show();
    }


    private class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.AdaptadorUsuarioHolder>{
        private OnItemClickListener listener;
        @NonNull
        @Override
        public AdaptadorUsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorUsuarioHolder(getLayoutInflater().inflate(R.layout.list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorUsuarioHolder holder, int position) {
            holder.imprimir(position);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),detalle.class).putExtra("position",position));
                }
            });

        }

        @Override
        public int getItemCount() {
            return listaUsuarios.size();
        }


        class AdaptadorUsuarioHolder extends RecyclerView.ViewHolder {
            TextView tvNombre,tvApellido, tvCiclo;
            ImageView ivFoto;
            public CardView cardView;
            public AdaptadorUsuarioHolder(@NonNull View itemView) {
                super(itemView);
                tvNombre=itemView.findViewById(R.id.nombre);
                tvApellido=itemView.findViewById(R.id.apellido);
                tvCiclo=itemView.findViewById(R.id.ciclo);
                ivFoto=itemView.findViewById(R.id.listImage);
                cardView=itemView.findViewById(R.id.main_container);
            }

            public void imprimir(int position) {
                tvNombre.setText(""+listaUsuarios.get(position).getNombre());
                tvApellido.setText(""+listaUsuarios.get(position).getApellido());
                tvCiclo.setText("Ciclo\n"+listaUsuarios.get(position).getCiclo());
                recuperarImagen(listaUsuarios.get(position).getUrlimagen(),ivFoto);

              //  listener.onItemClick(listaUsuarios.get(position));
            }

            public void recuperarImagen(String foto,ImageView iv)
            {
                ImageRequest peticion=new ImageRequest(foto,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setImageBitmap(response);
                            }
                        },
                        0,
                        0,
                        null,
                        null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                rq.add(peticion);
            }

        }
    }
}