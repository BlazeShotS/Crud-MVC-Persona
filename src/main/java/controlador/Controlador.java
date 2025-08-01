
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Persona;
import repository.PersonaDAO;
import vista.Vista;

/**
 *
 * @author PC
 */
public class Controlador implements ActionListener {

    
    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista vista = new Vista();
    
    
    DefaultTableModel modelo = new DefaultTableModel();       
    
    
    //DECLARAMOS NUESTROS BOTONES EN ESTE CONSTRUCTOR QUE CREAMOS
    public Controlador(Vista v){
        this.vista=v;
        this.vista.btnListar.addActionListener(this); //Hacemos referencia o llamado a nuestro boton listar
        this.vista.btnGuardar.addActionListener(this); //Hacemos referencia o llamado a nuestro boton guardar
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnElimin.addActionListener(this);
        
        //listar(vista.tbDatos);//NUESTRO METODO LISTAR NI BIEN SE EJECUTA EL PROGRAMA SE EJECUTA DICHO METODO
    }
    
    
    
    
    
    //----LA CAPA CONTROLADOR---
    @Override
    public void actionPerformed(ActionEvent e) { 
        if(e.getSource()==vista.btnListar){  
            limpiarTabla();
            listar(vista.tbDatos);//referencia al METODO listar
        }
        //ESTO ES PARA SABER CUANDO SE EJECUTARA EL METODO AGREGAR , es decir cuando el usuario presione el boton btnGuardar se ejecutara este metodo
        if (e.getSource()==vista.btnGuardar) {
            agregar();//ejecuta metodo agregar
            
            limpiarTabla(); //LIMPIO LOS DATOS DE LA TABLA Y SE VUELVEN A LISTAR AUTOMATICAMENTE
            listar(vista.tbDatos);
        }
        if (e.getSource()==vista.btnEditar) { //SE SELECCIONA LA FILA DE LA TABLA Y CUANDO DAMOS EN btnEditar se llena en las casillas de textos
            int fila = vista.tbDatos.getSelectedRow();
            if (fila==-1) {
               JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
            }else{ //QUE SE MUESTRE EN LA CAJA DE TEXTO LOS DATOS QUE CONTIENE EL USUARIO SELECCIONADO
               
               int id=Integer.parseInt((String)vista.tbDatos.getValueAt(fila,0).toString());
               String nom=(String)vista.tbDatos.getValueAt(fila, 1);
               String correo=(String)vista.tbDatos.getValueAt(fila, 2);
               int tel=Integer.parseInt((String)vista.tbDatos.getValueAt(fila,3).toString());

               vista.txtId.setText(""+id); //el fila que fue seleccionada de la tabla captura el id que esta en la linea 62 y es ubicado en el txtId
               vista.txtNom.setText(nom);
               vista.txtCorreo.setText(correo);
               vista.txtTelef.setText(""+tel);
            }
            
        }if (e.getSource()==vista.btnActualizar) { //CUANDO LE DA AL BOTON ACTUALIZAR SE EJECUTA ESOS METODOS
            Actualizar();
            
            limpiarTabla();
            listar(vista.tbDatos);
            
        }if (e.getSource()==vista.btnElimin){ //LO QUE HAGO ES CAPTURAR EL ID DE LA TABLA            
            delete();
            limpiarTabla();
            listar(vista.tbDatos);
        }
        
        
    }
        
    
    
    
    
    //metodo para mostrador los datos en un jtable
    public void listar (JTable tabla){
        modelo=(DefaultTableModel)tabla.getModel();
        
        List<Persona>lista=dao.listar(); //variable lista, dao.listar es un metodo que esta en La pestaña PersonaDAO
        Object[]object = new Object [4];
        for(int i=0; i<lista.size(); i++){
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getNom();
            object[2]=lista.get(i).getCorreo();
            object[3]=lista.get(i).getTel();
            modelo.addRow(object);
        }
        vista.tbDatos.setModel(modelo);
    }
    
    
    
    public void agregar(){
        
        String nom=vista.txtNom.getText();
        String correo=vista.txtCorreo.getText();
        int tel = Integer.parseInt(vista.txtTelef.getText()); //Convertir a entero
        
        /*
        try {
            int tel = Integer.parseInt(vista.txtTelef.getText());
            p.setTel(tel);
        } catch (NumberFormatException e) {
            System.out.println("Error: El número de teléfono debe ser numérico.");
            return;
        }
        */
        
        p.setNom(nom);
        p.setCorreo(correo);
        p.setTel(tel);
        int r = dao.agregar(p);
        if (r==1) {
           JOptionPane.showMessageDialog(vista, "Usuario Agregado con exito");
        }else{
           JOptionPane.showMessageDialog(vista, "Error Al Agregar Usuario");
        }            
        
    }
    
    
    
    public void Actualizar() {

        int id = Integer.parseInt(vista.txtId.getText());
        String nom = vista.txtNom.getText();
        String correo = vista.txtCorreo.getText() ;
        int tel = Integer.parseInt(vista.txtTelef.getText());
        p.setId(id);
        p.setNom(nom);
        p.setCorreo(correo);
        p.setTel(tel);        
        int r = dao.Actualizar(p);
        if (r==1) {
           JOptionPane.showMessageDialog(vista, "Usuario Actualizado con exito");
        }else{
            JOptionPane.showMessageDialog(vista, "Error al editar usuario");

        }
    }
    
    //ELIMINA TODA LA TABLA EN LA INTERFAZ GRAFICA
    void limpiarTabla() {

        int filas = vista.tbDatos.getRowCount();
        for (int i = filas - 1; i >= 0; i--) {
            ((DefaultTableModel) vista.tbDatos.getModel()).removeRow(i);
        }
    }

    

    public void delete() {
        int fila = vista.tbDatos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un usario");

        } else {
            int id = Integer.parseInt((String) vista.tbDatos.getValueAt(fila, 0).toString());
            dao.delete(id);
            JOptionPane.showMessageDialog(vista, "Usuario eliminado");
        }
    }
    
    
}
