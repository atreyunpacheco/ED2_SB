/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Code;

import java.util.Scanner;

/**
 *
 * @author gato
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws Code.ExceptionClaveNoExiste
     */
    public static void main(String[] args) throws ExceptionClaveNoExiste, ExceptionOrdenInvalido {
        // TODO code application logic here
        IArbolBusqueda<Integer,String> arbolPrueba;
        
        Scanner entrada=new Scanner(System.in);
        System.out.println("Elija un tipo de arbol(ABB, AVL, AMV)");
        String tipoArbol=entrada.next();
        tipoArbol=tipoArbol.toUpperCase();
        
        switch(tipoArbol){
            case "ABB":
                arbolPrueba=new ArbolBinarioBusqueda<>();
                break;
            case "AVL":
                arbolPrueba=new AVL();
                break;
            default:
                System.out.println("Tipo de Arbol invalido, eligiendo ArbolBinarioBusqueda\n");
                arbolPrueba=new ArbolBinarioBusqueda<>();
        }
        
        arbolPrueba.Insertar(50, "Azul");
        arbolPrueba.Insertar(78, "Naranja");
        arbolPrueba.Insertar(74, "Zapato");
        arbolPrueba.Insertar(30, "");
        arbolPrueba.Insertar(44, "");
        arbolPrueba.Insertar(20, "");
        arbolPrueba.Insertar(25, "");
        arbolPrueba.Insertar(23, "");
        arbolPrueba.Insertar(28, "");
        arbolPrueba.Insertar(50, "");
    }
    
}
