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
       boolean palabraNueva = true;
       for (String cadena : listaCadena)
       {
           for (Palabra palabra : listaPalabras)
           {
               if(palabra.getPalabra().equals(cadena))
               {
                   boolean yaExiste = false;
                   palabra.setRepeticion(palabra.getRepeticion()+1);
                   palabraNueva = false;
                   for ( Documento nombreDoc : palabra.getConjuntoDocumento() )
                   {
                       if(nombreDoc.getNombre().equals(nombreDocumento))
                       {
                           yaExiste = true;
                       }
                   }
                   if(!yaExiste)
                   {
                       Documento doc = new Documento(nombreDocumento);
                       palabra.nuevoDocumento(doc);
                   }
               }
           }
           if(palabraNueva)
           {
               Documento doc = new Documento(nombreDocumento);
               Palabra nuevaPalabra = new Palabra(cadena,1,doc);
               listaPalabras.add(nuevaPalabra);
               
           }
           palabraNueva=true;
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
