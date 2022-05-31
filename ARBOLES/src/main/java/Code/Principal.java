/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Code;

/**
 *
 * @author jonatan
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExceptionOrdenInvalido {
        // TODO code application logic here
        /*ARBOL BINARIO BUSQUEDA*/
        /*
        IArbolBusqueda<Integer,String> arbol=new ArbolBinarioBusqueda<>();
        arbol.InsertarR(10,"Jon");
        arbol.InsertarR(20,"Jon2");
        arbol.InsertarR(30,"Jon");
        arbol.InsertarR(25,"Jon");
        arbol.InsertarR(28,"Jon");
        arbol.InsertarR(5,"Jon");
        arbol.InsertarR(8,"Jon");
        arbol.InsertarR(39,"Jon");
        System.out.println(arbol);
        System.out.println("RECORRIDO POR NIVELES: "+arbol.recorridoPorNiveles());
        //arbol.eliminar(20);
        System.out.println("RECORRIDO POR NIVELES: "+arbol.recorridoPorNiveles());
        System.out.println("CANTIDAD DE NODOS DER: "+arbol.contarNodosConHijosDerechoR());
        System.out.println("CANTIDAD DE NODOS DER: "+arbol.contarNodosConHijosDerecho());
        System.out.println("CANTIDAD DE NODOS IZQ: "+arbol.contarNodosConHijosIzquierdo());
        System.out.println("CANTIDAD DE NODOS IZQ: "+arbol.contarNodosConHijosIzquierdoR());
        System.out.println("ALTURA: "+arbol.altura());
        System.out.println("CANTIDAD DE HIJOS DER NIVEL 1: "+arbol.cantHijosDerechosNivel(1));
        System.out.println("CANTIDAD DE HIJOS DER NIVEL 0: "+arbol.cantHijosDerechosNivel(0));
        System.out.println("CANTIDAD DE HIJOS DER NIVEL 2: "+arbol.cantHijosDerechosNivel(2));
        */
        /*
        AVL
        */
        /*
        ArbolBinarioBusqueda<Integer,String> AB=new AVL<>();
        AB.Insertar(40, "JON");
        
        AB.Insertar(50, "JON");
        AB.Insertar(30, "JON");
        AB.Insertar(60, "JON");
        AB.Insertar(20, "JON");
        AB.Insertar(25, "JON");
        
        
        System.out.println("RECORRIDO POR NIVELES: "+AB.recorridoPorNiveles());
        System.out.println("ALTURA: "+AB.altura());
        AB.eliminar(30);
        System.out.println("RECORRIDO POR NIVELES: "+AB.recorridoPorNiveles());
        AB.eliminar(50);
        AB.eliminar(60);
        System.out.println("RECORRIDO POR NIVELES: "+AB.recorridoPorNiveles());
*/
        
        IArbolBusqueda<Integer,String> arbol=new ArbolMViasBusqueda<>(4);
        arbol.Insertar(300, "juan");
        arbol.Insertar(200, "juan");
        arbol.Insertar(400, "juan");
        arbol.Insertar(70, "juan");

        arbol.Insertar(90, "juan");     //1
        arbol.Insertar(220, "juan");    //2
        arbol.Insertar(240, "juan");    //3
        arbol.Insertar(290, "juan");    //4
        arbol.Insertar(205, "juan");    //5
        arbol.Insertar(500, "juan");    //6
        arbol.Insertar(600, "juan");    //7
        arbol.Insertar(700, "juan");    //8
        arbol.Insertar(509, "juan");    //9
        arbol.Insertar(520, "juan");    //10
        arbol.Insertar(550, "juan");    //11
        
        arbol.Insertar(515, "juan");    //12
        arbol.Insertar(519, "juan");    //13
        
        System.out.println(arbol.recorridoPorNiveles());
        /*
        System.out.println(arbol.getRaiz().estanClavesLlenas());
        System.out.println(arbol.getRaiz().listaDeClaves.size());
        System.out.println(arbol.getRaiz().listaDeValores.size());
        System.out.println(arbol.getRaiz().listaDeHijos.size());
*/
        //arbol.eliminar(550);    //16
        System.out.println(arbol.recorridoPorNiveles());
        System.out.println(arbol.size());
        System.out.println(arbol.altura());
        System.out.println(arbol.recorridoEnPostOrden());
    }
    
}
