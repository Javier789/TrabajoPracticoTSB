/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Entidad.*;
import java.util.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastián
 */
public class VocabularioDao {
    static String url = "/home/javier/Escritorio/TrabajoPracticoTSB-master/TrabajoPracticoTSB/BasedeDatos/Prueba.sqlite";
    static Connection connect;
    
    public static Boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+url);
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "FALLO ACCESO A BASE DE DATOS "+ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "FALLO ACCESO Metd ConectarDB2 "+ex.getMessage());
           return false;
        }
        return true;
    }
    public static void close(){
       try {
           connect.close();
       } catch (SQLException ex) {
           Logger.getLogger(VocabularioDao.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    public static void GuardarLista (List<Palabra> listaPalabras){
       
        if (connect()) {
            for (Palabra palabra : listaPalabras) 
            {
                //PreparedStatement st = connect.prepareStatement("select COUNT(*) AS cant from Palabra where id = ?");
                //st.setInt(1,palabra.getIdPalabra());
                //result = st.executeQuery();
               if(palabra.getIdPalabra() == 0)// Si es igual a cero todavia no tiene un id 
               {
                   GuardarPalabraNueva(palabra);
               }
               else//La palabra ya existe en la base de datos
               {
                   ActualizarPalabra(palabra);
               }
            }
       close();
        }
       
       
    }
    private static int GuardarDocumento(Documento documentoNuevo){
        int ultimo = ultimoID("Documentos");
        try {
            

            if (documentoNuevo.getIdDocumento() == 0) {
                PreparedStatement st = connect.prepareStatement("insert into Documentos (id, Nombre) values (?,?)");
                st.setInt(1,ultimo);
                st.setString(2, documentoNuevo.getNombre());
                documentoNuevo.setIdDocumento(ultimo);
                st.execute();
            }
            else{
                return documentoNuevo.getIdDocumento();
            }
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR Guardar Documento: "+ex.getMessage());
        }
        return ultimo; //Deberia guardar el nombre del documento y devolver el id
    }
    private static void GuardarPalabraNueva(Palabra nuevaPalabra){
        //Insert Into para guardar la palabra  en la tabla palabra
        try {
            
            PreparedStatement st = connect.prepareStatement("insert into Palabra (id, palabra, repeticion) values (?,?,?)");
            int idPalabra = ultimoID("Palabra");
            st.setInt(1, idPalabra);
            st.setString(2, nuevaPalabra.getPalabra());
            st.setInt(3, nuevaPalabra.getRepeticion());
            st.execute();
            List<Documento> listaDoc = nuevaPalabra.getConjuntoDocumento();
            for(Documento doc : listaDoc )
            {
                int idDocumento = GuardarDocumento(doc);
                //Insert Into para guardar en la tabla PalabraXDocumento
                PreparedStatement stt = connect.prepareStatement("insert into PalabraPorDocumento (id_Palabra,id_Documento) values (?,?)");
                stt.setInt(1,idPalabra);
                stt.setInt(2, idDocumento);
                stt.execute();
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR Guardar Nueva Palabra: "+ex.getMessage());
        }
        
    }
    private static void ActualizarPalabra(Palabra palabraExistente){
        //Update a la tabla Partida
        try {
            PreparedStatement st = connect.prepareStatement("UPDATE Palabra SET Repeticion = ? where id = ?");
            st.setInt(1, palabraExistente.getRepeticion());
            st.setInt(2, palabraExistente.getIdPalabra());
            st.executeUpdate();
            List<Documento> listaDoc = palabraExistente.getConjuntoDocumento();
            for(Documento doc : listaDoc)
            {
                if(doc.getIdDocumento()!=0)//Quiere decir que el documento es nuevo
                {
                    int idDocumento= GuardarDocumento(doc);
                    //Insert Into para guardar en la tabla PalabraXDocumento
                    st = connect.prepareStatement("insert into PalabraPorDocumento (id_Palabra,id_Documento) values (?,?)");
                    st.setInt(1, palabraExistente.getIdPalabra());
                    st.setInt(2, idDocumento);
                    st.execute();
                }

            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR Actualizar Palabra: "+ex.getMessage());
        
    }}
    
private static int ultimoID(String tabla){
        ResultSet result = null;
        int id = 0;
        try {
            PreparedStatement st = connect.prepareStatement("select (max(id)) AS 'id' from " + tabla);
            result = st.executeQuery();
            id = result.getInt("id") + 1;// este te da el ultimo id que esta en la tabla
           
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "ERROR : "+ex.getMessage());
        }
        return id;//aca devolvemos el siguiente para guardar el la base de datos
    }
    public static List<Palabra> CargarPalabras(){
        List<Palabra> lista = new ArrayList<Palabra>();
    if (connect()) {
            try {
                PreparedStatement st = connect.prepareStatement("select id from Palabra ");
                ResultSet result1 = st.executeQuery();
                
                   while(result1.next()){
                        st = connect.prepareStatement("select doc.id,doc.nombre,pal.id as idPal,pal.palabra,pal.repeticion from Documentos doc join PalabraPorDocumento PxD on doc.id = PxD.id_documento join Palabra pal on pal.id = PXD.id_Palabra where pal.id = ?");
                        st.setInt(1,result1.getInt("id"));
                        ResultSet result = st.executeQuery();
                        List<Documento> ListaDoc = new ArrayList<Documento>();
                        Palabra p = new Palabra(result.getInt("idPal"),result.getString("palabra"),result.getInt("repeticion"),ListaDoc);
                        while(result.next()){
                            ListaDoc.add(new Documento(result.getInt("id"),result.getString("nombre")));
                            }
                       p.setConjuntoDocumento(ListaDoc);
                       lista.add(p);
                    }
                
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR Cargar Palabra: "+ex.getMessage());
            }
    }
    close();
    return lista;
    }
    
}
