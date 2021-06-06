package com.example.semana9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DatosContactos extends AppCompatActivity {
    DB usuarios;
    String accion= "nuevo";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_contactos);
      mostrarDatos();

        Button bRegresar=(Button)findViewById(R.id.btnRegresar);

        bRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void mostrarDatos(){
        try {
            Bundle bundle= getIntent().getExtras();
            accion= bundle.getString("accion");
            if (accion.equals("moficar")){
                id= bundle.getString("id");

                String user[]= bundle.getStringArray("user");

                TextView tempval=(TextView)findViewById(R.id.txtnombre);
                tempval.setText(user[0].toString());

                 tempval=(TextView)findViewById(R.id.txtDireccion);
                tempval.setText(user[1].toString());

               tempval=(TextView)findViewById(R.id.txtTelefono);
                tempval.setText(user[2].toString());

            }
        }catch (Exception e){
            Toast.makeText(DatosContactos.this,"Error: "+ e.getMessage().
                    toString(),Toast.LENGTH_LONG).show();

        }

    }
    public void  guardar_contactos(View v){
        try {
            TextView tempval=(TextView)findViewById(R.id.txtnombre);
          String nom = tempval.getText().toString();

            tempval=(TextView)findViewById(R.id.txtDireccion);
            String dir = tempval.getText().toString();

            tempval=(TextView)findViewById(R.id.txtTelefono);
            String tel = tempval.getText().toString();

            usuarios= new DB(DatosContactos. this,"", null,1);
            usuarios.guardarUsuario(nom, dir, tel, accion, id);

            Toast.makeText(DatosContactos.this,"Contacto registrado" +
                    " con exito", Toast.LENGTH_LONG).show();

            Intent imostrar= new Intent(DatosContactos. this,
                    MainActivity.class);
            startActivity(imostrar);

        }catch (Exception ex) {
            Toast.makeText(DatosContactos.this,"Error: "+
                    ex.getMessage().toString(),+Toast.LENGTH_LONG).show();
        }
        }


    }
