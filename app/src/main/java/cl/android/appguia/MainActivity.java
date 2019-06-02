package cl.android.appguia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;

import cl.android.appguia.network.RestClient;
//import cl.android.appguia.network.request.User;
import cl.android.appguia.network.request.Usuarios;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;
import cl.android.appguia.network.*;
import cl.android.appguia.network.request.*;
import cl.android.appguia.network.UserService;

public class MainActivity extends AppCompatActivity {
    private TextView idUsuario, nombre, apellido, usuario, texto;
    private EditText editTextNombre, editTextApellido, editTextUsuario;
    private Button botonConsulta, botonSiguiente, bEditar, bVolver, bEliminar;

    UserService userService;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.idUsuario = findViewById(R.id.idUsuario);
        this.nombre = findViewById(R.id.nombre);
        this.apellido = findViewById(R.id.apellido);
        this.usuario = findViewById(R.id.usuario);
        this.botonConsulta = findViewById(R.id.bConsulta);
        this.botonSiguiente = findViewById(R.id.bSiguiente);

        this.texto = findViewById(R.id.texto);
        this.editTextNombre = findViewById(R.id.editTextNombre);
        this.editTextApellido = findViewById(R.id.editTextApellido);
        this.editTextUsuario = findViewById(R.id.editTextUsuario);

        this.bEditar = findViewById(R.id.bEditar);
        this.bVolver = findViewById(R.id.bVolver);
        this.bEliminar = findViewById(R.id.bEliminar);


        //Setea Consulta
        nombre.setVisibility(View.VISIBLE);
        apellido.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);

        editTextNombre.setVisibility(View.INVISIBLE);
        editTextApellido.setVisibility(View.INVISIBLE);
        editTextUsuario.setVisibility(View.INVISIBLE);
        bEditar.setVisibility(View.INVISIBLE);
        bVolver.setVisibility(View.INVISIBLE);
        bEliminar.setVisibility(View.INVISIBLE);


        botonConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Escribe Log
                //System.out.println("El ID ES = "+idUsuario.getText().toString());
                if (idUsuario.getText().toString() != null) {
                    try {

                        RestClient.postSetup().getUsuario(idUsuario.getText().toString()).enqueue(new Callback<Usuarios>() {
                            @Override
                            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                                limpiar();
                                idUsuario.setText(response.body().getId());
                                nombre.setText(response.body().getNombre());
                                apellido.setText(response.body().getApellido());
                                usuario.setText(response.body().getNombre_usuario());

                                password = response.body().getPassword();

                                editTextNombre.setText(response.body().getNombre());
                                editTextApellido.setText(response.body().getApellido());
                                editTextUsuario.setText(response.body().getNombre_usuario());
                                Toast.makeText(MainActivity.this, "Servicio consumido exitosamente", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Usuarios> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Falla en servicio", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                //startActivity(intent);
                nombre.setVisibility(View.INVISIBLE);
                apellido.setVisibility(View.INVISIBLE);
                usuario.setVisibility(View.INVISIBLE);

                editTextNombre.setVisibility(View.VISIBLE);
                editTextApellido.setVisibility(View.VISIBLE);
                editTextUsuario.setVisibility(View.VISIBLE);
                bEditar.setVisibility(View.VISIBLE);
                bVolver.setVisibility(View.VISIBLE);
                bEliminar.setVisibility(View.VISIBLE);

                editTextNombre.setText(nombre.getText());
                editTextApellido.setText(apellido.getText());
                editTextUsuario.setText(usuario.getText());

            }
        });

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                //startActivity(intent);
                limpiar();
            }
        });

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuarios u = new Usuarios();
                u.setNombre(editTextNombre.getText().toString());
                u.setApellido(editTextApellido.getText().toString());
                u.setNombre_usuario(editTextUsuario.getText().toString());
                u.setPassword(password);
                //    updateUser(idUsuario.getText(), u);
                u.setId(idUsuario.getText().toString());

                //Call<Usuarios> call = userService.updateUser(idUsuario.getText().toString(), u);
                //call.enqueue(new Callback<Usuarios>() {
                RestClient.postSetup().updateUser(idUsuario.getText().toString(), u).enqueue(new Callback<Usuarios>() {
                    @Override
                    public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuario fue actualizado!", Toast.LENGTH_SHORT).show();
                            limpiar();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuarios> call, Throwable t) {
                        //Log.e("ERROR: ", t.getMessage());
                        Toast.makeText(MainActivity.this, "Usuario fue actualizado!", Toast.LENGTH_SHORT).show();
                        limpiar();
                    }
                });
            }
        });


        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call<Usuarios> call = userService.deleteUser(idUsuario.getText().toString());
                //call.enqueue(new Callback<Usuarios>() {

                RestClient.postSetup().deleteUser(idUsuario.getText().toString()).enqueue(new Callback<Usuarios>() {
                    @Override
                    public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuario fue eliminado!", Toast.LENGTH_SHORT).show();

                            limpiar();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuarios> call, Throwable t) {
                        //Log.e("ERROR: ", t.getMessage());
                        Toast.makeText(MainActivity.this, "Usuario fue eliminado!", Toast.LENGTH_SHORT).show();

                        limpiar();
                    }
                });

            }
        });


    }

    public void limpiar() {
        nombre.setText("");
        apellido.setText("");
        usuario.setText("");


        nombre.setVisibility(View.VISIBLE);
        apellido.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);

        editTextNombre.setVisibility(View.INVISIBLE);
        editTextApellido.setVisibility(View.INVISIBLE);
        editTextUsuario.setVisibility(View.INVISIBLE);
        bEditar.setVisibility(View.INVISIBLE);
        bVolver.setVisibility(View.INVISIBLE);
        bEliminar.setVisibility(View.INVISIBLE);
    }




}
