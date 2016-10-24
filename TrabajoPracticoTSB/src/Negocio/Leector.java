/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Entidad.*;
/**
 *
 * @author Sebastián
 */
public class Leector {

    public Leector() {
    }
    
    
    
     public List<String> DevolverListaDeArchivo(String ruta)
    {
        List<String> listaCadena = new ArrayList<String>();
        File f = new File(ruta);
        try(FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr)) 
        {
            
            String ln = br.readLine();
            while(ln!=null)     
            {
                String cadena = ln;
                listaCadena.add(cadena);
                ln = br.readLine();
            } 
        }       
        catch(IOException e) 
        {     
            System.out.println("Error al procesar el archivo de entrada...");
        }  

        return listaCadena;
    }
    
     
   public List<String> DevolverListaDePalabras(List<String> listaLineas)
    {
        List<String> listaPalabras = new ArrayList<String>();
        
        for (String linea : listaLineas)
        {
            String [] arregloPalabras = linea.split(" ");
            for (int i=0; i< arregloPalabras.length; i++)
            {
                
                String palabra = arregloPalabras[i].toLowerCase();
                palabra = SacarSignos(palabra);
                listaPalabras.add(palabra);
            }
        }
        
        return listaPalabras;
    }
   
//   public List<Palabra> DevolverListaPalabras (List<String> listaCadena, String nombreDocumento)
//   {
//       List<Palabra> listaPalabras = new ArrayList<Palabra>();
//       boolean palabraNueva = true;
//       for (String cadena : listaCadena)
//       {
//           for (Palabra palabra : listaPalabras)
//           {
//               if(palabra.getPalabra().equals(cadena))
//               {
//                   palabra.setRepeticion(palabra.getRepeticion()+1);
//                   palabraNueva = false;
//               }
//           }
//           if(palabraNueva)
//           {
//               Palabra nuevaPalabra = new Palabra(cadena,1,nombreDocumento);
//               listaPalabras.add(nuevaPalabra);
//           }
//           palabraNueva=true;
//       }
//       return listaPalabras;
//   }
    
   public String SacarSignos(String palabra)
   {
        String aRemplazar=palabra;
        Pattern p= Pattern.compile("[-!$%&/?()¿¡,;.:-_{}*+123456789#ºª@|]");
	Matcher m= p.matcher(aRemplazar);
        String   remplazado=palabra;
	if(m.find())
        {
            remplazado=m.replaceAll("");
        }
        
        return remplazado;
   }
}
