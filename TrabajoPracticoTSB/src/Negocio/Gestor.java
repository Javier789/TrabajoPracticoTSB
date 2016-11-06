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
import Datos.*;
import javax.swing.SwingWorker;
/**
 *
 * @author Sebastián
 */
public class Gestor extends SwingWorker<Void,Integer>{

    private List<String> listaRutas;
    private Vocabulario vocabulario;
    
    public Gestor() {
        this.listaRutas = new ArrayList<String>();
        vocabulario = new Vocabulario();
        CargarPalabras();
    }
    
    public void CargarPalabras()
    {
        vocabulario.setListaPalabra(VocabularioDao.CargarPalabras());
    }
    
    public void TomarRuta(String ruta)
    {
        listaRutas.add(ruta);
    }
    
    public void ProcesarArchivo(String ruta)
    {
            
       
    }
    
    public List ConsultarVocabulario()
    {
        return vocabulario.conocerVocabulario();
    }
          
    public List ConsultarPorFiltro(String filtro)
    {
        return vocabulario.BuquedaPorFiltro(filtro);
    }
            
    private List<String> DevolverListaDeArchivo(String ruta)
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
    
     
    private List<String> DevolverListaDePalabras(List<String> listaLineas)
    {
        List<String> listaPalabras = new ArrayList<String>();
        
        for (String linea : listaLineas)
        {
            String [] arregloPalabras = linea.split(" ");
            for (int i=0; i< arregloPalabras.length; i++)
            {
                
                String palabra = arregloPalabras[i].toLowerCase();
                palabra = SacarSignos(palabra);
                if (!palabra.equals("")) {
                    listaPalabras.add(palabra);
                }
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
    
   private String SacarSignos(String palabra)
   {
        String aRemplazar=palabra;
        Pattern p= Pattern.compile("[-!$%&/?()\"¿¡,;.:-_{}*+1234567890#ºª@|]");
	Matcher m= p.matcher(aRemplazar);
        String   remplazado=palabra;
	if(m.find())
        {
            remplazado=m.replaceAll("");
        }
        
        return remplazado;
   }
   
   private String DeterminarNombreDocumento(String ruta)
   {
     String rutaCortada[] = ruta.split("\\\\");
     String nombreLargo = rutaCortada[rutaCortada.length - 1];
     return nombreLargo;
   }
   
   private String EliminarExtension(String nombreLargo)
   {
       char [] arreglo = nombreLargo.toCharArray();
       String nombreCorto = nombreLargo.substring(0, arreglo.length -4);
       return nombreCorto;
   }

    @Override
    protected Void doInBackground() throws Exception {
        int cant = (100/(listaRutas.size()*3)),i=0;
        setProgress(0);
        for (String ruta : listaRutas)
        {
         long time_start, time_end;
               
            String nombreDocumento = DeterminarNombreDocumento(ruta);
            List<String> listaLineasArchivo = DevolverListaDeArchivo(ruta);
            i++;
            setProgress(cant*i);
            List<String> listaPalabras = DevolverListaDePalabras(listaLineasArchivo);
            i++;
            setProgress(cant*i);
           /* time_start = System.currentTimeMillis();
            vocabulario.addRange(listaPalabras,  nombreDocumento);
            time_end = System.currentTimeMillis();
            System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");*/
            time_start = System.currentTimeMillis();
            vocabulario.addRangeOptimo(listaPalabras, nombreDocumento);
            time_end = System.currentTimeMillis();
            System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
            i++;
            setProgress(cant*i);
      
        }
        VocabularioDao.GuardarLista(vocabulario.getListaPalabra());
        setProgress(100);
        
        return null;
    }

   
    
   
}
