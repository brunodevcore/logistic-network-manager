package dominio;

public class MercaderiaWrapper implements Comparable<MercaderiaWrapper>{

    private final Mercaderia mercaderia;

    public MercaderiaWrapper (Mercaderia mercaderia){
        this.mercaderia = mercaderia;
    }

    public Mercaderia getMercaderia(){
        return mercaderia;
    }

    @Override
    public int compareTo(MercaderiaWrapper otra){
        return this.mercaderia.getCodigo().compareTo(otra.mercaderia.getCodigo());
    }

    @Override
    public String toString(){
        return mercaderia.toString();
    }
}
