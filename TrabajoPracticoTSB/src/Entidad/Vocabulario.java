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
                   palabra.setRepeticion(palabra.getRepeticion()+1);
                   palabraNueva = false;
               }
           }
           if(palabraNueva)
           {
               Palabra nuevaPalabra = new Palabra(cadena,1,nombreDocumento);
               listaPalabras.add(nuevaPalabra);
           }
           palabraNueva=true;
       }
    }
    
    public List<Palabra> BuquedaPorFiltro(String filtro)
    {
        List<Palabra> listaFiltrada = new ArrayList<Palabra>();
        for (Palabra palabra : listaPalabras)
        {
            if(palabra.getPalabra().startsWith(filtro))
            {
                listaFiltrada.add(palabra);
            }
        }
        return listaFiltrada;
    }
}
