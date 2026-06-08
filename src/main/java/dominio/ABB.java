package dominio;

public class ABB<T extends Comparable<T>> {

    private NodoABB<T> raiz;

    public ABB(){
        this.raiz = null;
    }

    public T buscar (T dato, int[] contador){
        return buscarRec(raiz, dato, contador);
    }

    public void insertar(T dato){
        raiz = insertarRec(raiz, dato);
    }

    private T buscarRec(NodoABB<T> nodo, T dato, int[] contador){
        if(nodo == null){
            return null;
        }
        contador[0]++;

        int cmpTo = dato.compareTo(nodo.getDato());

        if(cmpTo == 0){
            return nodo.getDato();
        } else if(cmpTo < 0){
            return buscarRec(nodo.getIzquierda(), dato, contador);
        } else {
            return buscarRec(nodo.getDerecha(), dato, contador);
        }
    }

    private NodoABB<T> insertarRec(NodoABB<T> nodo, T dato){
        if(nodo == null){
            return new NodoABB<>(dato);
        }
        if(dato.compareTo(nodo.getDato()) < 0){
            nodo.setIzquierda(insertarRec(nodo.getIzquierda(), dato));
        }
        else if(dato.compareTo(nodo.getDato()) > 0){
            nodo.setDerecha(insertarRec(nodo.getDerecha(), dato));
        }
        return nodo;
    }



    public String listarAscendente(){
        StringBuilder sb = new StringBuilder();
        listarAscendenteRec(raiz, sb);
        return sb.toString();
    }

    public String listarDescendente(){
        StringBuilder sb = new StringBuilder();
        listarDescendenteRec(raiz, sb);
        return sb.toString();
    }

    private void listarAscendenteRec(NodoABB<T> nodo, StringBuilder sb){
        if(nodo == null){
            return;
        }
        listarAscendenteRec(nodo.getIzquierda(), sb);
        if(sb.length() > 0){
            sb.append("|");
        }
        sb.append(nodo.getDato().toString());
        listarAscendenteRec(nodo.getDerecha(), sb);

    }

    private void listarDescendenteRec(NodoABB<T> nodo, StringBuilder sb){
        if(nodo == null){
            return;
        }
        listarDescendenteRec(nodo.getDerecha(), sb);
        if(sb.length() > 0){
            sb.append("|");
        }
        sb.append(nodo.getDato().toString());
        listarDescendenteRec(nodo.getIzquierda(), sb);

    }

}
