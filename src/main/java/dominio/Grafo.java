package dominio;

import dominio.ABB;
import dominio.Cola;
import dominio.Tupla;

public class Grafo {

    private CentroLogistico[] vertices;
    private int cantMaxVertices;
    private int cantVertices;

    private Conexion[][] aristas;

    public Grafo(int cantMaxVertices){
        this.cantMaxVertices = cantMaxVertices;
        this.cantVertices = 0;
        this.vertices = new CentroLogistico[cantMaxVertices];
        aristas = new Conexion[cantMaxVertices][cantMaxVertices];
        for(int i = 0; i < cantMaxVertices; i++) {
            for (int j = 0; j < cantMaxVertices; j++) {
                aristas[i][j] = new Conexion();
            }
        }
    }

    private int obtenerPosicionVertice(String codigo) {
        for(int i = 0; i < cantMaxVertices; i++){
            if(vertices[i] != null && vertices[i].getCodigo().equals(codigo)){
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosicionLibre() {
        for(int i = 0; i < cantMaxVertices; i++){
            if(vertices[i] == null){
                return i;
            }
        }
        return -1;
    }

    public boolean agregarCentro(CentroLogistico centro) {
        if(cantVertices >= cantMaxVertices){
            return false;
        }
        if(obtenerPosicionVertice(centro.getCodigo()) != -1){
            return false;
        }
        int pos = obtenerPosicionLibre();
        vertices[pos] = centro;
        cantVertices++;
        return true;
    }

    public boolean estaLleno(){
        return cantVertices >= cantMaxVertices;
    }

    public boolean agregarConexion(String codigoOrigen, String codigoDestino, int distancia, int tiempo) {
        int posOrigen = obtenerPosicionVertice(codigoOrigen);
        int posDestino = obtenerPosicionVertice(codigoDestino);

        if(posOrigen == -1 || posDestino == -1){
            return false;
        }

        if(aristas[posOrigen][posDestino].isExiste()){
            return false;
        }

        aristas[posOrigen][posDestino].setDatos(distancia, tiempo);
        return true;
    }

    public boolean existeCentro(String codigo) {
        if(obtenerPosicionVertice(codigo) != -1){
            return true;
        }
        return false;
    }

    public boolean existeConexion(String codigoOrigen, String codigoDestino) {
        int posOrigen = obtenerPosicionVertice(codigoOrigen);
        int posDestino = obtenerPosicionVertice(codigoDestino);
        if(posOrigen == -1 || posDestino == -1) return false;
        return aristas[posOrigen][posDestino].isExiste();
    }

    public String redCentrosPorCantidadDeConexiones(String codigoOrigen, int cantidad) {
        int posOrigen = obtenerPosicionVertice(codigoOrigen);

        Cola<Tupla> cola = new Cola<>();
        boolean[] visitados = new boolean[cantMaxVertices];
        ABB<CentroLogistico> resultado = new ABB<>();

        visitados[posOrigen] = true;
        cola.encolar(new Tupla(posOrigen, 0));

        while (!cola.estaVacia()) {
            Tupla tupla = cola.desencolar();
            int pos = tupla.getPosicion();
            int saltos = tupla.getSaltos();

            if (saltos <= cantidad) {
                resultado.insertar(vertices[pos]);

                for (int i = 0; i < cantMaxVertices; i++) {
                    if (aristas[pos][i].isExiste() && !visitados[i]) {
                        visitados[i] = true;
                        cola.encolar(new Tupla(i, saltos + 1));
                    }
                }
            }
        }

        return resultado.listarAscendente();
    }


    private int obtenerVerticeNoVisitadoDeMenorCosto(boolean[] visitados, int[] costos) {
        int pos = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < cantMaxVertices; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                pos = i;
            }
        }
        return pos;
    }

    private void dijkstra(String codigoOrigen, boolean usarDistancia,boolean[] visitados, int[] costos, CentroLogistico[] vengo) {
        int posOrigen = obtenerPosicionVertice(codigoOrigen);

        for (int n = 0; n < cantMaxVertices; n++) {
            visitados[n] = false;
            costos[n] = Integer.MAX_VALUE;
            vengo[n] = null;
        }

        costos[posOrigen] = 0;

        for (int v = 0; v < cantVertices; v++) {
            int posV = obtenerVerticeNoVisitadoDeMenorCosto(visitados, costos);
            if (posV == -1) break;

            visitados[posV] = true;

            for (int j = 0; j < cantMaxVertices; j++) {
                if (aristas[posV][j].isExiste() && !visitados[j]) {
                    int peso = usarDistancia
                            ? aristas[posV][j].getDistancia()
                            : aristas[posV][j].getTiempo();

                    if (costos[j] > costos[posV] + peso) {
                        costos[j] = costos[posV] + peso;
                        vengo[j] = vertices[posV];
                    }
                }
            }
        }
    }

    public int getCostoMinimo(String codigoOrigen, String codigoDestino, boolean usarDistancia) {
        boolean[] visitados = new boolean[cantMaxVertices];
        int[] costos = new int[cantMaxVertices];
        CentroLogistico[] vengo = new CentroLogistico[cantMaxVertices];
        dijkstra(codigoOrigen, usarDistancia, visitados, costos, vengo);
        int posDestino = obtenerPosicionVertice(codigoDestino);
        return costos[posDestino];
    }

    public String getCaminoMinimo(String codigoOrigen, String codigoDestino, boolean usarDistancia) {
        boolean[] visitados = new boolean[cantMaxVertices];
        int[] costos = new int[cantMaxVertices];
        CentroLogistico[] vengo = new CentroLogistico[cantMaxVertices];
        dijkstra(codigoOrigen, usarDistancia, visitados, costos, vengo);
        int posDestino = obtenerPosicionVertice(codigoDestino);
        if (!visitados[posDestino]) return null;

        String camino = vertices[posDestino].toString();
        int auxPos = posDestino;
        while (vengo[auxPos] != null) {
            auxPos = obtenerPosicionVertice(vengo[auxPos].getCodigo());
            camino = vertices[auxPos].toString() + "|" + camino;
        }
        return camino;
    }

}
