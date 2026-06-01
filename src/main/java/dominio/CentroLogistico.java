package dominio;

public class CentroLogistico implements Comparable<CentroLogistico>{

    private String codigo;
    private String nombre;
    private String departamento;
    private String direccion;

    public CentroLogistico(String codigo, String nombre, String departamento, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamento = departamento;
        this.direccion = direccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public int compareTo(CentroLogistico otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre + ";" + departamento + ";" + direccion;
    }

}
