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
import javax.swing.ProgressMonitor;


public class VocabularioDao {
    static String url = "Prueba.sqlite";
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
                if(doc.getIdDocumento() == 0)//Quiere decir que el documento es nuevo
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
                 
                 PreparedStatement st = connect.prepareStatement("select id, nombre from Documentos");
                ResultSet rsDOc = st.executeQuery();
                List<Documento> ListaDoc = new ArrayList<Documento>();
                 while(rsDOc.next()){
                    ListaDoc.add(new Documento(rsDOc.getInt("id"),rsDOc.getString("nombre")));
                 }
                
                
                st = connect.prepareStatement("select p.id, p.palabra,p.repeticion from Palabra p");
                ResultSet rsPal = st.executeQuery();
                while(rsPal.next()){
                   Palabra p = new Palabra(rsPal.getInt("id"),rsPal.getString("palabra"),rsPal.getInt("repeticion"),new ArrayList<Documento>());
                    lista.add(p);
                }
                
                
                
                for (int i = 0; i < ListaDoc.size(); i++) {
                   st = connect.prepareStatement("select p.id, p.palabra,p.repeticion from Palabra p,  PalabraPorDocumento pD, Documentos d where ? = pD.id_Documento and p.id = pD.id_Palabra");
                   st.setInt(1,ListaDoc.get(i).getIdDocumento());
                   ResultSet rsPxD = st.executeQuery();
                    while(rsPxD.next()){
                        
                            lista.get(rsPxD.getInt("id")-1).nuevoDocumento(ListaDoc.get(i));
                            
                            
                        } 
                }
                
               
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR Cargar Palabra: "+ex.getMessage());
            }
    }
    close();
    return lista;
    }
    
}
