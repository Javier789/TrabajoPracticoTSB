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
    static String url = "TrabajoPracticoTSB/Base de Datos/Prueba.sqlite";
    static Connection connect;
    
    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+url);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "FALLO ACCESO A BASE DE DATOS "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "FALLO ACCESO Metd ConectarDB2 "+ex.getMessage());
        }
    }
    public static void close(){
       try {
           connect.close();
       } catch (SQLException ex) {
           Logger.getLogger(VocabularioDao.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    public static void GuardarLista (List<Palabra> listaPalabras)
    {
       connect();
       ResultSet result = null;
        int id = 0;
        try {
            PreparedStatement st = connect.prepareStatement("select max(id) from Palabra");
            result = st.executeQuery();
            for (Palabra palabra : listaPalabras) 
            {
               if(palabra.getIdPalabra()==0)//Si se cumple esta condicion es por que la palabra es nueva
               {
                   GuardarPalabraNueva(palabra);
               }
               else//La palabra ya existe en la base de datos
               {
                   ActualizarPalabra(palabra);
               }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
       
       close();
       
    }
    
    public static List<Palabra> CargarPalabras()
    {
        //Deberia cargar todos los datos.
        List<Palabra> lista = null;
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Palabra");
            result = st.executeQuery();
            while (result.next()) {
               // Palabra p = new Palabra(result.getString("palabra"),Integer.parseInt(result.getString("repeticion")),);
               //lista.add(p);
            }
            PreparedStatement stt = connect.prepareStatement("select * from Documentos where");
            result = stt.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    private static int GuardarDocumento(Documento documentoNuevo)
    {
        try {
            PreparedStatement st = connect.prepareStatement("insert into Documentos (id, Nombre) values (?,?)");
            st.setString(1, ultimoID("Documentos") + "");
            st.setString(2, documentoNuevo.getNombre());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 1; //Deberia guardar el nombre del documento y devolver el id
    }
    
    private static void GuardarPalabraNueva(Palabra nuevaPalabra)
    {
        //Insert Into para guardar la palabra  en la tabla palabra
        try {
            connect();
            PreparedStatement st = connect.prepareStatement("insert into Palabra (id, palabra, repeticion) values (?,?,?)");
            st.setString(1, ultimoID("Palabra") + "");
            st.setString(2, nuevaPalabra.getPalabra());
            st.setString(2, nuevaPalabra.getRepeticion()+"");
            st.execute();
            List<Documento> listaDoc = nuevaPalabra.getConjuntoDocumento();
            for(Documento doc : listaDoc )
            {
                int idDocumento= GuardarDocumento(doc);
                //Insert Into para guardar en la tabla PalabraXDocumento
                PreparedStatement stt = connect.prepareStatement("insert into PalabraPorDocumento (id_Palabra,id_Documento) values (?,?)");
                stt.setString(1, "");
                stt.setString(2, idDocumento + "");
                stt.execute();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }finally{
            close();
        }
        
    }
    
    private static void ActualizarPalabra(Palabra palabraExistente)
    {
        //Update a la tabla Partida
        try {
            connect();
            PreparedStatement st = connect.prepareStatement("UPDATE Palabra SET Repeticion = ? where palabra like '?'");
            st.setString(1, palabraExistente.getRepeticion() + "");
            st.setString(2, palabraExistente.getPalabra());
            st.executeUpdate();
            List<Documento> listaDoc = palabraExistente.getConjuntoDocumento();
            for(Documento doc : listaDoc)
            {
                if(doc.getIdDocumento()!=0)//Quiere decir que el documento es nuevo
                {
                    int idDocumento= GuardarDocumento(doc);
                    //Insert Into para guardar en la tabla PalabraXDocumento
                    PreparedStatement stt = connect.prepareStatement("insert into PalabraPorDocumento (id_Palabra,id_Documento) values (?,?)");
                    stt.setString(1, "");
                    stt.setString(2, idDocumento + "");
                    stt.execute();
                }

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }finally{
            close();
        }
    }
    private static int ultimoID(String tabla){
        ResultSet result = null;
        int id = 0;
        try {
            PreparedStatement st = connect.prepareStatement("select max(id) from " + tabla);
            result = st.executeQuery();
            while (result.next()) {
                id = Integer.parseInt(result.getString("Palabra"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }
    
    
}
