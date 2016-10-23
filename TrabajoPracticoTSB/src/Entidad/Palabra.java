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
public class Palabra {
    
    private String palabra;
    private int repeticion;
    private List<String> conjuntoDocumento = new ArrayList<String>();

    public Palabra(String palabra, int repeticion, String documento) {
        this.palabra = palabra;
        this.repeticion = repeticion;
        this.conjuntoDocumento.add(documento);
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public List<String> getConjuntoDocumento() {
        return conjuntoDocumento;
    }

    public void setConjuntoDocumento(List<String> conjuntoDocumento) {
        this.conjuntoDocumento = conjuntoDocumento;
    }

    public void nuevoDocumento(String documento)
    {
        conjuntoDocumento.add(documento);
    }
    
    
    
}
