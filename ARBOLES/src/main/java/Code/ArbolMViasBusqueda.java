/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author vivia
 * @param <K>
 * @param <V>
 */
public class ArbolMViasBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V> {
  
    NodoMVias<K,V> raiz;
    protected int orden;
    protected static final int POSICION_NO_VALIDA=-1;
    protected static final int ORDEN_MINIMO=3;
    
    @Override
    public NodoMVias<K,V> getRaiz(){
        return this.raiz;
    }
    public ArbolMViasBusqueda(){
        this.orden=ArbolMViasBusqueda.ORDEN_MINIMO;
    }
    
    public ArbolMViasBusqueda(int orden) throws ExceptionOrdenInvalido {
        if(orden<ArbolMViasBusqueda.ORDEN_MINIMO){
            throw new ExceptionOrdenInvalido();
        }
        this.orden=orden;
    }
    
    @Override
    public void Insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if (claveAInsertar==null){
            throw new NullPointerException("No se permite datos nulos");
        }
        if (valorAInsertar==null){
            throw new NullPointerException("No se permite datos nulos");
        }
    
        //si el arbol es vacio
        // creamos un nuevo nodo y la raiz lo apuntamos
        // al nuevo nodo con los datos a insertar
    
        if(this.esArbolVacio()){
            this.raiz=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
            return;
        }
    
        NodoMVias<K,V> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            int posicionClaveInsertar=this.obtenerPosicionDeClave(nodoActual,claveAInsertar);
            //  si posicionClaveInsertar es diferente de -1 o POSICION_NO_VALIDA
            //     quiere decir que la clave ya ha sido insertada en el nodo actual y 
            //    posicion clave insertar indica la posicion a insertar
            // si posicionClaveInsertar es -1 entonces todavia no se inserto la clave en el nodo actual(raiz)
            if(posicionClaveInsertar!=ArbolMViasBusqueda.POSICION_NO_VALIDA){
                nodoActual.setValor(posicionClaveInsertar, valorAInsertar);
                return;
            }
      
      // como todavia no se inserto la clave, es decir no existe la clave en el arbol(si el nodo actual es la raiz)
      // o todavia no se insertar la clave en el nodo actual, entonces:
      // preguntamos si hay si es hoja
      //    caso true: 
      //      1-quiere decir que hay espacio en el nodo actual para insertar la clave
      //      2-si no hay espacio entonces el nodo actual tendra un nuevo hijo que apuntara
      //        a un nuevo nodo con los datos a insertar
            if(nodoActual.esHoja()){
                if(nodoActual.estanClavesLlenas()){
                    NodoMVias<K,V> nuevoHijo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                    int posiciondeEnlace=this.getPosicionPorDondeBajar(nodoActual,claveAInsertar);
                    nodoActual.setHijo(posiciondeEnlace, nuevoHijo);
                }else{
                    this.insertarClaveyValor(nodoActual, claveAInsertar, valorAInsertar);
                }
                return;
            }
      
      // si ya no es hoja el nodo actual entonces quiere decir que tiene hijos 
      // entonces debemos buscar el nodo hijo por donde debe bajar para insertar la clave
      // para eso usamos el metodo getPosicionPorDondeBajar();
            int posicionPorDondeBajar=this.getPosicionPorDondeBajar(nodoActual, claveAInsertar);
      
      // 1) si el nodo hijo es null o no existe tal nodo 
      //    entonces apuntamos con un nuevo nodo con los datos a insertar
      // 2) si el nodo no es null, entonces el nodo hijo ya fue creado entonces actualizamos 
      //    el nodo actual con el nodo hijo por donde bajar, luego se repite el proceso para
      //     insertar la clave
            if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
                NodoMVias<K,V> nuevoHijo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                return;
            }
            nodoActual=nodoActual.getHijo(posicionPorDondeBajar);
        }
    }
    
    
    /**
    * Inserta la clave de forma ordenada ascedente 
    *  respetando las otras claves, es decir moviendolo
    */
    private void insertarClaveyValor(NodoMVias<K,V> nodoActual,K claveaInsertar,V valoraInsertar){
        int j=0;
        boolean claveaInsertadoEnNodoActual=false;
        while(j<nodoActual.cantidadClavesNoVacios() && claveaInsertadoEnNodoActual==false){
            K claveActual=nodoActual.getClave(j);
            if(claveaInsertar.compareTo(claveActual)<0){
                // recoremos las claves del nodo actual, para insertar una nueva clave 
                for(int i=nodoActual.cantidadClavesNoVacios();i>0 && i>j;i--){
                    nodoActual.setClave(i, nodoActual.getClave(i-1));
                    nodoActual.setValor(i, nodoActual.getValor(i-1));
                }
                //insertar la nueva clave en la posicion correspondiente
                nodoActual.setClave(j, claveaInsertar);
                nodoActual.setValor(j, valoraInsertar);
                // cambiamos de estado si la clave se inserto
                claveaInsertadoEnNodoActual=true;
            }
            // si no es menor entonces incrementa
            j++;
        }
        // si todavia no se inserto entonces debemos insertar en la ultima posicion del nodoActual
        if(claveaInsertadoEnNodoActual==false){
            nodoActual.setClave(j, claveaInsertar);
            nodoActual.setValor(j, valoraInsertar);
        }
    }
    
    private int getPosicionPorDondeBajar(NodoMVias<K,V> nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            K claveActual=nodoActual.getClave(i);
            if(claveABuscar.compareTo(claveActual)<0){
                return i;
            }
        }
        return this.orden-1;
    }
    
    private int obtenerPosicionDeClave(NodoMVias<K,V> nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            K claveActual=nodoActual.getClave(i);
            if(claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        return ArbolMViasBusqueda.POSICION_NO_VALIDA; 
    }
    
    @Override
    public void InsertarR(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V eliminar(K claveAEliminar) throws ExceptionClaveNoExiste {
        if(claveAEliminar==null){
            throw new NullPointerException("No se puede eliminar clave nula");
        }
        //verificamos si la clave existe en el arbol
        V valorAEliminar=this.buscar(claveAEliminar);
        if(valorAEliminar==null){
            throw new ExceptionClaveNoExiste();
        }
        this.raiz=eliminar(this.raiz,claveAEliminar);
        return valorAEliminar;
    }
    
    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual, K claveAEliminar){
        //recorremos en el nodoActual solo clave no vacias
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            K claveActual=nodoActual.getClave(i);
            // verificamos si es la clave que buscamos
            if(claveAEliminar.compareTo(claveActual)==0){
                // Existe dos casos:
                //  1.- nodoActual es Hoja
                //  2.- nodoActual no es Hoja
                
                if(nodoActual.esHoja()){    // caso 1
                    // elimina clave y valor de este nodo, dandole su indice
                    //  1.- si es que hay mas clave posterior, hay que recorrer hacia atras 
                    //      y el ultima clave y valor poner null
                    this.eliminarClaveValorDePosicion(nodoActual,i);
                    //  2.- si en caso de que este nodo solo tenia una clave y valor
                    //      entonces debemos eliminar el nodo completo
                    if(nodoActual.cantidadClavesNoVacios()==0){
                        return NodoMVias.nodoVacio();
                    }
                    // si en caso tuviera clave y valor 
                    //  entonces devolvemos el nodoActual al nodoPadre
                    return nodoActual;
                }
                // caso 2 o caso 3
                K claveDeReemplazo;
                if(this.hayHijosMasAdelanteDeLaPosicion(nodoActual,i)){
                    //caso2
                    claveDeReemplazo=this.obtenerClaveSucesorInOrden(nodoActual, claveAEliminar);
                }else{
                    // caso 3
                    claveDeReemplazo=this.obtenerClavePredecesorInOrden(nodoActual, claveAEliminar);
                }
                V valorAsociadoAClaveDeReemplazo=this.buscar(claveDeReemplazo);
                
                nodoActual=eliminar(nodoActual,claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorAsociadoAClaveDeReemplazo);
                
                return nodoActual;
                
            }
            if(claveAEliminar.compareTo(claveActual)<0){
                // cambiar de nodo actual
                // mandar a eliminar la clave
                // podria ser vacio o un hijo
                NodoMVias<K,V> supuestoNuevoHijo=eliminar(nodoActual.getHijo(i),claveAEliminar);
                // actulaizar el hijo
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
            
        }
        // el ultimo hijo del nodo
        NodoMVias<K,V> supuestoNuevoHijo=eliminar(nodoActual.getHijo(orden-1),claveAEliminar);
        // actulaizar el hijo
        nodoActual.setHijo(orden-1, supuestoNuevoHijo);
        return nodoActual;
    }
    private K obtenerClavePredecesorInOrden(NodoMVias<K,V> nodoActual,K claveAEliminar){
        int posicionClaveActual=obtenerPosicionClave(nodoActual,claveAEliminar);
        if(!nodoActual.esHijoVacio(posicionClaveActual)){
            NodoMVias<K,V> nodoPredecesor=obtenerUltimoNodoPredecesor(nodoActual.getHijo(posicionClaveActual));
            return nodoPredecesor.getClave(nodoPredecesor.cantidadClavesNoVacios()-1);
        }else{
            return nodoActual.getClave(posicionClaveActual-1);
        }
    }
    
    private NodoMVias<K,V> obtenerUltimoNodoPredecesor(NodoMVias<K,V> nodoPredecesorActual){
        if(nodoPredecesorActual.esHoja()){
           return nodoPredecesorActual; 
        }else{  // tiene hijos 
            //preguntamos si el ultimo hijo es vacio
            //      si es entonces devolvemos el mismo nodo porque contiene
            //      la clave predecesor
            if(nodoPredecesorActual.esHijoVacio(orden-1)){  
                return nodoPredecesorActual;
            }else{
                NodoMVias<K,V> supuestoNodoPredecesor=obtenerUltimoNodoPredecesor(nodoPredecesorActual.getHijo(orden-1));
                return supuestoNodoPredecesor;
            }
        }
    }
    
    private int obtenerPosicionClave(NodoMVias<K,V> nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            K claveActual=nodoActual.getClave(i);
            if(claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        return 0;
    }
    
    private K obtenerClaveSucesorInOrden(NodoMVias<K,V> nodoActual,K claveAEliminar){
        
        while(!NodoMVias.esNodoVacio(nodoActual)){
            boolean huboCambioDeNodoActual=false;
            for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
                if(nodoActual.getClave(i).compareTo(claveAEliminar)>0){
                    if(!nodoActual.esHijoVacio(i)){
                        nodoActual=nodoActual.getHijo(i);
                        huboCambioDeNodoActual=true;
                    }else{
                        return nodoActual.getClave(i);
                    }
                }
            }
            if(!huboCambioDeNodoActual){
                nodoActual=nodoActual.getHijo(orden-1);
            }
                    
        }
        return null;
    }

    private boolean hayHijosMasAdelanteDeLaPosicion(NodoMVias<K,V> nodoActual,int posicion){
        for(int i=posicion+1;i<=orden-1;i++){
            if(!nodoActual.esHijoVacio(i)){
                return true;
            }
        }
        return false;
    }
    
    private void eliminarClaveValorDePosicion(NodoMVias<K,V> nodoActual, int posicionClaveAEliminar){
        // si la clave esta en el ultima posicion
        if(posicionClaveAEliminar==orden-2){
            nodoActual.setClave(posicionClaveAEliminar, null);
            nodoActual.setValor(posicionClaveAEliminar, null);
        }else{
            for(int i=posicionClaveAEliminar;i<nodoActual.cantidadClavesNoVacios()-1;i++){
                nodoActual.setClave(i,nodoActual.getClave(i+1));
                nodoActual.setValor(i,nodoActual.getValor(i+1));
            }
            nodoActual.setClave(nodoActual.cantidadClavesNoVacios()-1, null);
            nodoActual.setValor(nodoActual.cantidadClavesNoVacios()-1, null);
        }
    }
    
    @Override
    public V buscar(K claveABuscar) throws NullPointerException{
        if(claveABuscar==null){
            throw new NullPointerException("Clave a buscar no puede ser nula");
        }
        NodoMVias<K,V> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            boolean huboCambioDeNodoActual=false;
            
            for(int i=0;i<nodoActual.cantidadClavesNoVacios() &&
                    !huboCambioDeNodoActual;i++){
                K claveDeNodoActual=nodoActual.getClave(i);
                
                if(claveABuscar.compareTo(claveDeNodoActual)==0){
                    return nodoActual.getValor(i);
                }
                
                if(claveABuscar.compareTo(claveDeNodoActual)<0){
                    nodoActual=nodoActual.getHijo(i);
                    huboCambioDeNodoActual=true;
                }    
            }
            
            if(!huboCambioDeNodoActual){
                nodoActual=nodoActual.getHijo(orden-1);
            }
        }
        return null;
    };
    
    @Override
    public boolean contiene(K claveAVuscar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        int cantidad=0;
        Queue<NodoMVias<K,V>> colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()){
            NodoMVias<K,V> nodoActual=colaDeNodos.poll();
            cantidad++;
            for(int i=0;i<orden;i++){
                if(!nodoActual.esHijoVacio(i)){
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
        }
        return cantidad;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    public int altura(NodoMVias<K,V> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayorEntreLosHijos=0;
        for(int i=0;i<orden;i++){
            int alturaDeHijoActual=altura(nodoActual.getHijo(i));
            if(alturaDeHijoActual>alturaMayorEntreLosHijos){
                alturaMayorEntreLosHijos=alturaDeHijoActual;
            }
        }
        return alturaMayorEntreLosHijos+1;
    }
    

    @Override
    public int cantHojas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void vaciarArbol(){
        this.raiz=NodoMVias.nodoVacio();
    };
    
    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public K minimo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K maximo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantHijosDerechosNivel(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarNodosConHijosDerecho() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarNodosConHijosDerechoR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarNodosConHijosIzquierdoR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarNodosConHijosIzquierdo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido=new ArrayList<>();
        if(!this.esArbolVacio()){
            Queue <NodoMVias<K,V>> colaDeNodos=new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                NodoMVias<K,V> nodoActual=colaDeNodos.poll();
                //  recorrer las claves todas con(orden -1) o
                //  solo recorrer la cantidad de claves NO VACIAS
                for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
                    recorrido.add(nodoActual.getClave(i));
                    
                    if(!nodoActual.esHijoVacio(i)){
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                   
                }
                
                if(!nodoActual.esHijoVacio(orden-1)){
                    colaDeNodos.offer(nodoActual.getHijo(orden-1));
                }
                
            }
        }
        return recorrido;
    }
    
    @Override
    public List<K> recorridoEnInOrden(){
        List<K> recorrido=new ArrayList<>();
        recorridoEnInOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnInOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){   //caso base
           return;
        }
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(orden-1),recorrido);
    };
    
    @Override
    public List<K> recorridoEnPreOrden(){
        List<K> recorrido=new ArrayList<>();
        recorridoEnPreOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){   //caso base
           return;
        }
        // el nodoActual.cantidadClaves puede tambein
        // ser por orden si queremos recorrer por los hijos si o si
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            recorrido.add(nodoActual.getClave(i));
            recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
            
        }
        recorridoEnInOrden(nodoActual.getHijo(orden-1),recorrido);
    };
    
    @Override
    public List<K> recorridoEnPostOrden(){
        List<K> recorrido=new ArrayList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){   //caso base
           return;
        }
        recorridoEnInOrden(nodoActual.getHijo(0),recorrido);
        for(int i=0;i<nodoActual.cantidadClavesNoVacios();i++){
            recorridoEnInOrden(nodoActual.getHijo(i+1),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    };
}
