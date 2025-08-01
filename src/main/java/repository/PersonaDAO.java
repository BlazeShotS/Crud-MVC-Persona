
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;
import modelo.Persona;

/**
 *
 * @author PC
 */
public class PersonaDAO {
    
    Conexion conectar = new Conexion(); //la variable conectar esta instanciando a la clase Conexion
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    
    //----------------MIS METODOS------------------
    
    public List listar(){
        List<Persona>datos=new ArrayList<>();
        
        String sql="select*from persona";
        try{
           con=conectar.getConnection();
           ps=con.prepareStatement(sql);
           rs=ps.executeQuery();
           
           while(rs.next()){
               Persona p = new Persona ();
               p.setId(rs.getInt(1)); //esos numeros es el numero de orden de la TABLA DE LA BASE DE DATOS
               p.setNom(rs.getString(2));
               p.setCorreo(rs.getString(3));
               p.setTel(rs.getInt(4));
               datos.add(p);
           }
        }catch(Exception e){
            
        }
        
        return datos;
    }
    
    
    
    public int agregar(Persona p) {

        String sql = "INSERT INTO persona(Nombre, Correo, Telefono) VALUES (?, ?, ?)";
        int resultado = 0; // Para saber si la inserción fue exitosa

        try {
            con = conectar.getConnection();

            // 🔹 Desactivar autoCommit para usar rollback
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNom()); //LOS NUMEROS 1 2 3 Representan la posicion de los parametros de la consula SQL
            ps.setString(2, p.getCorreo());
            ps.setInt(3, p.getTel());

            resultado = ps.executeUpdate(); // Devuelve 1 si la inserción fue exitosa

            // 🔹 Confirmar cambios
            con.commit();

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Revertir si hay error
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Muestra el error en consola
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar el autoCommit
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }

    
    public int Actualizar (Persona p){
        String sql = "update persona set Nombre=?, Correo=?, Telefono=? where Id=?";
        try{
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getCorreo());
            ps.setInt(3, p.getTel());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
                    
        }catch (Exception e){
            
        }
        
        return 1;
    }
    
    
    public void delete(int id){
        String sql = "delete from persona Where Id="+id;
        
        try{
            con = conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
        }catch(Exception e){
            
        }
        
    }
    

    
}
