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

    public Vocabulario(List<Palabra> listaPalabra) {
        this.listaPalabras = listaPalabra;
    }

    public List<Palabra> getListaPalabra() {
        return listaPalabras;
    }

    public void setListaPalabra(List<Palabra> listaPalabra) {
        this.listaPalabras = listaPalabra;
    }
    
    public void addRange(List<Palabra> listaPalabrasNuevas)
    {
       boolean palabraNuevaBool = true;
       
       for (Palabra palabraNueva : listaPalabrasNuevas)
       {
           for (Palabra palabra : listaPalabras)
           {
               if(palabra.getPalabra().equals(palabraNueva.getPalabra()))
               {
                   palabra.setRepeticion(palabra.getRepeticion() + palabraNueva.getRepeticion());
                   palabra.nuevoDocumento(palabraNueva.primerDocumento());
                   palabraNuevaBool = false;
               }
           }
           if(palabraNuevaBool)
           {
               Palabra nuevaPalabra = new Palabra(palabraNueva.getPalabra(),palabraNueva.getRepeticion(),palabraNueva.primerDocumento());
               listaPalabras.add(nuevaPalabra);
           }
           palabraNuevaBool=true;
       }
    }
}
