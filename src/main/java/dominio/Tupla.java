package dominio;

public class Tupla {

    private final int posicion;
    private final int saltos;

    public Tupla(int posicion, int saltos) {
        this.posicion = posicion;
        this.saltos = saltos;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getSaltos() {
        return saltos;
    }
}
