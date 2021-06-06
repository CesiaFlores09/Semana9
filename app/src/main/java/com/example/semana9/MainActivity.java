package com.example.semana9;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DB db;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          obtenerDatos();

        Button bsalir=(Button)findViewById(R.id.btnSalir);

        bsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        c.moveToPosition(info.position);
        menu.setHeaderTitle(c.getString(1));


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnxModificar:
                try {
                    String user[] = {
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),

                    };

                    Bundle bundle = new Bundle();
                    bundle.putString("accion", "modificar");
                    bundle.putString("id", c.getString(0));
                    bundle.putStringArray("user", user);

                    Intent iusuario = new Intent(MainActivity.this, DatosContactos.class);
                    iusuario.putExtras(bundle);
                    startActivity(iusuario);

                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this,"Error: "+ e.getMessage().
                            toString(),Toast.LENGTH_LONG).show();

                }
                return true;

            case R.id.mnxEliminar:

                AlertDialog confirmacion= eliminar();
                confirmacion.show();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
        }
        public AlertDialog eliminar(){
        AlertDialog.Builder confirmacion = new
                AlertDialog.Builder(MainActivity.this);

        confirmacion.setTitle(c.getString(1));
        confirmacion.setMessage("Estas seguro de eliminar este contacto?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.eliminarUsuario(c.getString(0));
                dialog.cancel();
                Toast.makeText(MainActivity.this,"El contacto se elimino " +
                        "correctamente.", Toast.LENGTH_LONG).show();
            }
        });

        confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(MainActivity.this,"Accion cancelada.", Toast.LENGTH_LONG).show();
            }
        });
        return confirmacion.create();

        }
        public  void obtenerDatos(){
        db=new DB(MainActivity.this,"", null,1);
        c=db.ConsultarUsuarios();
        if (c.moveToFirst()){

            ListView ItsUser=(ListView)findViewById(R.id.lstContactos);

            final ArrayList<String>alusers= new ArrayList<String>();
            final ArrayAdapter<String> aaUsers=new
                    ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                    alusers);
            ItsUser.setAdapter(aaUsers);

            do {
                alusers.add(c.getString(1));
            }while (c.moveToNext());
            aaUsers.notifyDataSetChanged();

            registerForContextMenu(ItsUser);

            }else {
            Toast.makeText(MainActivity.this,"No hay contactos " +
                    "que mostrar ",Toast.LENGTH_LONG).show();

        }
        }
        public void registrar_contactos(View view){
           Intent iagregar = new Intent(MainActivity. this, DatosContactos.class);
           startActivity(iagregar);

            }
        }





