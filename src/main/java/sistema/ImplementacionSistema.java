// Bruno Rivero - 263355
package sistema;

import dominio.*;
import interfaz.*;

public class ImplementacionSistema implements Sistema  {

    private int maxCentros;
    private ABB<Mercaderia> abbPorId;
    private ABB<MercaderiaWrapper> abbPorCodigo;
    private ABB<Mercaderia>[] abbsPorCategoria;
    private Grafo grafo;

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
        grafo = new Grafo(maxCentros);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarMercaderia(String id, String codigo, String descripcion, boolean fragil, Categoria categoria) {
        if(id == null || id.isBlank() || codigo == null || codigo.isBlank() || descripcion == null || descripcion.isBlank() || categoria == null){
            return Retorno.error1("Parametros invalidos");
        }

        if (!codigo.matches("[a-zA-Z]{2}-\\d{3}-[a-zA-Z0-9]{6}")) {
            return Retorno.error2("Formato de código inválido");
        }

        int[] contador = {0};
        Mercaderia auxId = new Mercaderia(id, null, null, false, null);
        if(abbPorId.buscar(auxId, contador) != null){
            return Retorno.error3("Ya existe una mercaderia con ese id");
        }

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
        return Retorno.ok(abbPorId.listarDescendente());
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
        if(codigo == null || codigo.isBlank() || nombre == null || nombre.isBlank() || departamento == null || departamento.isBlank() || direccion == null || direccion.isBlank()){
            return Retorno.error2("Parametros invalidos");
        }
        if(grafo.estaLleno()){
            return Retorno.error1("Se alcanzo el maximo de centros");
        }

        CentroLogistico centro = new CentroLogistico(codigo, nombre, departamento, direccion);
        if(!grafo.agregarCentro(centro)){
            return Retorno.error3("Ya existe un centro con ese codigo");
        }

        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoOrigen, String codigoDestino, int distancia, int tiempo) {
        if(codigoOrigen == null || codigoOrigen.isBlank() || codigoDestino == null || codigoDestino.isBlank()){
            return Retorno.error1("Parametros invalidos");
        }
        if(!grafo.existeCentro(codigoOrigen)){
            return Retorno.error2("No existe el centro origen");
        }

        if(!grafo.existeCentro(codigoDestino)){
            return Retorno.error3("No existe el centro destino");
        }

        if(distancia <= 0){
            return Retorno.error4("Distancia invalida");
        }

        if(tiempo <= 0){
            return Retorno.error5("Tiempo invalido");
        }

        if(grafo.existeConexion(codigoOrigen, codigoDestino)){
            return Retorno.error6("Ya existe una conexion entre esos centros");
        }

        grafo.agregarConexion(codigoOrigen, codigoDestino, distancia, tiempo);
        return Retorno.ok();
    }

    @Override
    public Retorno redCentrosPorCantidadDeConexiones(String codigoOrigen, int cantidad) {
        if (cantidad < 0) {
            return Retorno.error1("Cantidad invalida");
        }
        if (codigoOrigen == null || codigoOrigen.isBlank()) {
            return Retorno.error2("Codigo invalido");
        }
        if (!grafo.existeCentro(codigoOrigen)) {
            return Retorno.error3("El centro logistico no existe");
        }

        String resultado = grafo.redCentrosPorCantidadDeConexiones(codigoOrigen, cantidad);
        return Retorno.ok(resultado);
    }

    @Override
    public Retorno viajeCostoMinimoDistancia(String codigoOrigen, String codigoDestino) {
        if (codigoOrigen == null || codigoOrigen.isBlank() ||
                codigoDestino == null || codigoDestino.isBlank()) {
            return Retorno.error1("Parametros invalidos");
        }
        if (!grafo.existeCentro(codigoOrigen)) {
            return Retorno.error2("No existe el centro origen");
        }
        if (!grafo.existeCentro(codigoDestino)) {
            return Retorno.error3("No existe el centro destino");
        }

        String camino = grafo.getCaminoMinimo(codigoOrigen, codigoDestino, true);
        if (camino == null) {
            return Retorno.error4("No hay camino entre origen y destino");
        }

        int costo = grafo.getCostoMinimo(codigoOrigen, codigoDestino, true);
        return Retorno.ok(costo, camino);
    }

    @Override
    public Retorno viajeCostoMinimoTiempo(String codigoOrigen, String codigoDestino) {
        if (codigoOrigen == null || codigoOrigen.isBlank() ||
                codigoDestino == null || codigoDestino.isBlank()) {
            return Retorno.error1("Parametros invalidos");
        }
        if (!grafo.existeCentro(codigoOrigen)) {
            return Retorno.error2("No existe el centro origen");
        }
        if (!grafo.existeCentro(codigoDestino)) {
            return Retorno.error3("No existe el centro destino");
        }

        String camino = grafo.getCaminoMinimo(codigoOrigen, codigoDestino, false);
        if (camino == null) {
            return Retorno.error4("No hay camino entre origen y destino");
        }

        int costo = grafo.getCostoMinimo(codigoOrigen, codigoDestino, false);
        return Retorno.ok(costo, camino);
    }


}
