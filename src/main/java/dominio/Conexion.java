package dominio;

public class Conexion {

    private int distancia;
    private int tiempo;
    private boolean existe;

    public Conexion(){
        this.existe = false;
    }

    public Conexion(int distancia, int tiempo) {
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.existe = true;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean isExiste() {
        return existe;
    }

    public void borrar(){
        this.existe = false;
    }

    public void setDatos(int distancia, int tiempo) {
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.existe = true;
    }

}
