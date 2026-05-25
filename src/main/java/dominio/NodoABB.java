package dominio;

public class NodoABB <T extends Comparable<T>>{

    private T dato;
    private NodoABB<T> izquierda;
    private NodoABB<T> derecha;

    public NodoABB(T dato){
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }


    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoABB<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoABB<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoABB<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoABB<T> derecha) {
        this.derecha = derecha;
    }


}
