/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Entidad.*;
import java.util.List;

/**
 *
 * @author Sebastián
 */
public class VocabularioDao {
    
    public static void GuardarLista (List<Palabra> listaPalabras)
    {
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
       
    }
    
    public static List<Palabra> CargarPalabras()
    {
        //Deberia cargar todos los datos.
        return null;
    }
    
    private static int GuardarDocumento(String documentoNuevo)
    {
        return 1; //Deberia guardar el nombre del documento y devolver el id
    }
    
    private static void GuardarPalabraNueva(Palabra nuevaPalabra)
    {
        //Insert Into para guardar la palabra  en la tabla palabra
        List<Documento> listaDoc = nuevaPalabra.getConjuntoDocumento();
        for(Documento doc : listaDoc )
        {
            int idDocumento= GuardarDocumento(doc.getNombre());
            //Insert Into para guardar en la tabla PalabraXDocumento
        }
    }
    
    private static void ActualizarPalabra(Palabra palabraExistente)
    {
        //Update a la tabla Partida
        List<Documento> listaDoc = palabraExistente.getConjuntoDocumento();
        for(Documento doc : listaDoc )
        {
            if(doc.getIdDocumento()!=0)//Quiere decir que el documento es nuevo
            {
                int idDocumento= GuardarDocumento(doc.getNombre());
                //Insert Into para guardar en la tabla PalabraXDocumento
            }
            
        }
    }
    
    
    
}
