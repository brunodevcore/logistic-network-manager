package sistema;


import dominio.ABB;
import dominio.MercaderiaWrapper;
import interfaz.*;
import dominio.Mercaderia;


public class ImplementacionSistema implements Sistema  {

    private int maxCentros;
    private ABB<Mercaderia> abbPorId;
    private ABB<MercaderiaWrapper> abbPorCodigo;
    private ABB<Mercaderia>[] abbsPorCategoria;

    @Override
    public Retorno inicializarSistema(int maxCentros) {
        if(maxCentros <= 3){
            return Retorno.error1("maxCentros debe ser mayor a 3");
        }
        this.maxCentros = maxCentros;
        abbPorId = new ABB<>();
        abbPorCodigo = new ABB<>();
        abbsPorCategoria = new ABB[5];
        for(int i = 0; i < 5; i++){
            abbsPorCategoria[i] = new ABB<>();
        }
        return Retorno.ok();
    }

    @Override
    public Retorno registrarMercaderia(String id, String codigo, String descripcion, boolean fragil, Categoria categoria) {
        //Error 1
        if(id == null || id.isBlank() || codigo == null || codigo.isBlank() || descripcion == null || descripcion.isBlank() || categoria == null){
            return Retorno.error1("Parametros invalidos");
        }

        //Error 2 - Buscado, no sabia como era un formato valido.
        if (!codigo.matches("[a-zA-Z]{2}-\\d{3}-[a-zA-Z0-9]{6}")) {
            return Retorno.error2("Formato de código inválido");
        }

        //Error 3 - Ya existe una mercaderia con ese id
        int[] contador = {0};
        Mercaderia auxId = new Mercaderia(id, null, null, false, null);
        if(abbPorId.buscar(auxId, contador) != null){
            return Retorno.error3("Ya existe una mercaderia con ese id");
        }

        //Error 4 - Si ya existe una mercaderia registrada con ese codigo
        int[] contador2 = {0};
        MercaderiaWrapper auxCodigo = new MercaderiaWrapper(new Mercaderia(null, codigo, null, false, null));
        if(abbPorCodigo.buscar(auxCodigo, contador2) != null){
            return Retorno.error4("Ya existe una mercaderia con ese codigo");
        }

        Mercaderia nueva = new Mercaderia(id, codigo, descripcion, fragil, categoria);
        abbPorId.insertar(nueva);
        abbPorCodigo.insertar(new MercaderiaWrapper(nueva));
        abbsPorCategoria[categoria.getIndice()].insertar(nueva);

        return Retorno.ok();
    }

    @Override
    public Retorno buscarMercaderiaPorId(String id) {
        if(id == null || id.isBlank()){
            return Retorno.error1("Id invalido");
        }

        int[] contador = {0};
        Mercaderia aux = new Mercaderia(id, null, null, false, null);
        Mercaderia encontrada = abbPorId.buscar(aux, contador);

        if(encontrada == null){
            return Retorno.error2("No existe una mercaderia con ese id");
        }
        return Retorno.ok(contador[0], encontrada.toString());
    }

    @Override
    public Retorno listarMercaderiasPorIdAscendente() {
        return Retorno.ok(abbPorId.listarAscendente());
    }

    @Override
    public Retorno listarMercaderiasPorIdDescendente() {
        return Retorno.ok(abbPorCodigo.listarDescendente());
    }

    @Override
    public Retorno buscarMercaderiaPorCodigo(String codigo) {
        if(codigo == null || codigo.isBlank()){
            return Retorno.error1("Codigo invalido");
        }

        int[] contador = {0};
        MercaderiaWrapper aux = new MercaderiaWrapper(new Mercaderia(null, codigo,null, false, null));
        MercaderiaWrapper encontrado = abbPorCodigo.buscar(aux, contador);

        if(encontrado == null){
            return Retorno.error2("No existe una mercaderia con ese codigo");
        }

        return Retorno.ok(contador[0], encontrado.getMercaderia().toString());
    }

    @Override
    public Retorno listarMercaderiasPorCodigoAscendente() {
        return Retorno.ok(abbPorCodigo.listarAscendente());
    }

    @Override
    public Retorno listarMercaderiasPorCategoria(Categoria unaCategoria) {
        return Retorno.ok(abbsPorCategoria[unaCategoria.getIndice()].listarAscendente());
    }

    @Override
    public Retorno registrarCentroLogistico(String codigo, String nombre, String departamento, String direccion) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoOrigen, String codigoDestino, int distancia, int tiempo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno redCentrosPorCantidadDeConexiones(String codigoOrigen, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoDistancia(String codigoOrigen, String codigoDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoTiempo(String codigoOrigen, String codigoDestino) {
        return Retorno.noImplementada();
    }
}
