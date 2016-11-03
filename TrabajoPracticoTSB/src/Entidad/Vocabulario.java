/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastián
 */
public class Vocabulario {
    
    public List<Palabra> listaPalabras;
   
    public Vocabulario() {
        this.listaPalabras = new ArrayList<Palabra>();
    }

    public List<Palabra> getListaPalabra() {
        return listaPalabras;
    }

    public void setListaPalabra(List<Palabra> listaPalabra) {
        this.listaPalabras = listaPalabra;
    }
    
//    public void addRange(List<Palabra> listaPalabrasNuevas)
//    {
//       boolean palabraNuevaBool = true;
//       
//       for (Palabra palabraNueva : listaPalabrasNuevas)
//       {
//           for (Palabra palabra : listaPalabras)
//           {
//               if(palabra.getPalabra().equals(palabraNueva.getPalabra()))
//               {
//                   palabra.setRepeticion(palabra.getRepeticion() + palabraNueva.getRepeticion());
//                   palabra.nuevoDocumento(palabraNueva.primerDocumento());
//                   palabraNuevaBool = false;
//               }
//           }
//           if(palabraNuevaBool)
//           {
//               Palabra nuevaPalabra = new Palabra(palabraNueva.getPalabra(),palabraNueva.getRepeticion(),palabraNueva.primerDocumento());
//               listaPalabras.add(nuevaPalabra);
//           }
//           palabraNuevaBool=true;
//       }
//    }
    public void addRange(List<String> listaCadena, String nombreDocumento)
    {
       
        
       //Recorrer toda Lista de entrada
       for (String cadena : listaCadena)
       {
           //Bandera Palabra nueva
            boolean palabraNueva = true;
           //Por cada palabra nueva buscamos en la lista de palabras
           for (Palabra palabra : listaPalabras)
           {
               //Pregunta si la palabra actual es igual a la cadena de entrada
               if(palabra.getPalabra().equals(cadena))
               {
                   
                   //Entonces si ya tenemos la palabra en la lista aumentamos las repeticiones
                   palabra.setRepeticion(palabra.getRepeticion()+1);
                   //No es una palabra nueva
                   palabraNueva = false;
                   //Ahora vamos a recorrer la lista de documentos
                   //bandera de si ya existe entre los documentos
                   boolean noExisteElArchivo = true;
                   for ( Documento nombreDoc : palabra.getConjuntoDocumento() )
                   {
                       if(nombreDoc.getNombre().equals(nombreDocumento))
                       {
                           noExisteElArchivo = false;
                           //Como el archivo si existia no seguimos recorriendo
                           break;
                       }
                   }
                   if(noExisteElArchivo)
                   {
                       Documento doc = new Documento(nombreDocumento);
                       palabra.nuevoDocumento(doc);
                   }
                   //Como la palabra ya estaba en la lista no seguimos
                   break;
               }
               
           }
           //Utilizamos la bandera para saber si es una palabra nueva
           if(palabraNueva)
           {
               //si lo es la gregamos
               Documento doc = new Documento(nombreDocumento);
               Palabra nuevaPalabra = new Palabra(cadena,1,doc);
               listaPalabras.add(nuevaPalabra);
               
           }
       }
       
    }
    
    public List BuquedaPorFiltro(String filtro)
    {
        List vocabularioFiltrado = new ArrayList<String>();
        for (Palabra palabra : listaPalabras)
        {
            if(palabra.getPalabra().startsWith(filtro))
            {
                List<String> PalFiltrada = new ArrayList<String>();
                PalFiltrada.add(palabra.getPalabra());
                PalFiltrada.add(""+palabra.getRepeticion());
                PalFiltrada.add(""+palabra.getConjuntoDocumento().size());
                vocabularioFiltrado.add(PalFiltrada);
            }
        }
        return vocabularioFiltrado;
    }
    
    public List conocerVocabulario(){
        
        List vocabulario = new ArrayList<String>();
        for(Palabra p : this.listaPalabras){
            List<String> palabra = new ArrayList<String>();
            palabra.add(p.getPalabra());
            palabra.add(""+p.getRepeticion());
            palabra.add(""+p.getConjuntoDocumento().size());
            vocabulario.add(palabra);
        }
        
        return vocabulario;
    }
    
    
}
