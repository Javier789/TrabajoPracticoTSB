/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

/**
 *
 * @author Sebastián
 */
public class Documento {
    private int idDocumento;
    private String nombre;

    public Documento(String nombre) {
        this.idDocumento = 0;
        this.nombre = nombre;
    }

    public Documento(int idDocumento, String nombre) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
