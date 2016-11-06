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
    private int idPalabra;
    private String palabra;
    private int repeticion;
    private List<Documento> conjuntoDocumento = new ArrayList<Documento>();

    public Palabra(String palabra, int repeticion, Documento documento) {
        this.palabra = palabra;
        this.repeticion = repeticion;
        this.conjuntoDocumento.add(documento);
        idPalabra = 0;
    }

    public Palabra(int idPalabra, String palabra, int repeticion, List<Documento> conjuntoDocumento) {
        this.idPalabra = idPalabra;
        this.palabra = palabra;
        this.repeticion = repeticion;
        this.conjuntoDocumento = conjuntoDocumento;
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

    public List<Documento> getConjuntoDocumento() {
        return conjuntoDocumento;
    }

    public void setConjuntoDocumento(List<Documento> conjuntoDocumento) {
        this.conjuntoDocumento = conjuntoDocumento;
    }

    public void nuevoDocumento(Documento documento)
    {
        conjuntoDocumento.add(documento);
    }
    public Documento primerDocumento()
    {
        return conjuntoDocumento.get(0);
    }

    public int getIdPalabra() {
        return idPalabra;
    }

    public void setIdPalabra(int idPalabra) {
        this.idPalabra = idPalabra;
    }
    
    @Override
    public String toString()
    {
     return palabra + " " +repeticion;   
    }
    
}
