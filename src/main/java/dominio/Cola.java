package dominio;

public class Cola<T> {

    private NodoCola<T> frente;
    private NodoCola<T> fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public void encolar(T dato) {
        NodoCola<T> nuevo = new NodoCola<>(dato);
        if (fin == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public T desencolar() {
        if (frente == null) {
            return null;
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
