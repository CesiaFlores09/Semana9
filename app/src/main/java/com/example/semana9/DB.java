package com.example.semana9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    public static final String db_usuarios="db_usuario";
    public static final int v=1;

    String sqlTabla="create table usuarios(idUsuario integer primary key autoincrement,nombre text,direccion text,telefono text)";

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,db_usuarios,factory,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(sqlTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void guardarUsuario(String nom,String dir, String tel, String accion, String id){
        SQLiteDatabase db = getWritableDatabase();
        if(accion.equals("modificar")){
            db.execSQL("update usuarios set nombre='"+nom+"',direccion='"+dir+"',telefono='"+tel+"' where idUsuario='"+id+"'");
        }else {
            db.execSQL("insert into usuarios(nombre,direccion,telefono)values('"+nom+"','"+dir+"','"+tel+"')");
        }
    }
    public void eliminarUsuario(String id){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("delete from usuarios where idUsuario='"+id+"'");
    }
    public Cursor ConsultarUsuarios() {
        String sql = "select*from usuarios order by nombre asc";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    }



