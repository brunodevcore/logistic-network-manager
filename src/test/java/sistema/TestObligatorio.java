package sistema;

import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestObligatorio {
    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(10);
    }

    //  ── OP 1 ──
    @Test
    void inicializarSistemaOk() {
        Sistema s2 = new ImplementacionSistema();
        retorno = s2.inicializarSistema(10);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void inicializarSistemaError1() {
        Sistema s2 = new ImplementacionSistema();
        retorno = s2.inicializarSistema(3);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s2.inicializarSistema(1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s2.inicializarSistema(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s2.inicializarSistema(-1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    // ── OP 2 ──
    @Test
    void registrarMercaderiaOk() {
        retorno = s.registrarMercaderia("COD01", "AB-001-ABC123", "Descripcion 1", false, Categoria.ALIMENTOS);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarMercaderiaError1() {
        retorno = s.registrarMercaderia("COD01", "XX-001-XXX123", " ", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarMercaderia("", "XX-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarMercaderia("COD01", "", "Descripcion 1", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarMercaderiaError2() {
        retorno = s.registrarMercaderia("COD12", "11-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarMercaderiaError3() {
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        retorno = s.registrarMercaderia("COD01", "XX-001-XXX234", "Descripcion 2", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void registrarMercaderiaError4() {
        s.registrarMercaderia("COD01", "AB-001-ABC123", "Descripcion 1", false, Categoria.ALIMENTOS);
        retorno = s.registrarMercaderia("COD02", "AB-001-ABC123", "Descripcion 2", false, Categoria.OTROS);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    // ── OP 3 ──
    @Test
    void buscarMercaderiaPorIdOk() {
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion valida", false, Categoria.OTROS);
        retorno = s.buscarMercaderiaPorId("COD01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void buscarMercaderiaPorIdError1() {
        retorno = s.buscarMercaderiaPorId(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarMercaderiaPorId("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarMercaderiaPorId("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void buscarMercaderiaPorIdError2() {
        retorno = s.buscarMercaderiaPorId("COD01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    // ── OP 4 ──
    @Test
    void listarMercaderiasPorIdAscendenteOk() {
        s.registrarMercaderia("COD02", "XX-002-XXX123", "Descripcion 2", false, Categoria.OTROS);
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        s.registrarMercaderia("COD03", "XX-003-XXX123", "Descripcion 3", false, Categoria.OTROS);
        retorno = s.listarMercaderiasPorIdAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("COD01;XX-001-XXX123;Descripcion 1;false;Otros|COD02;XX-002-XXX123;Descripcion 2;false;Otros|COD03;XX-003-XXX123;Descripcion 3;false;Otros", retorno.getValorString());
    }

    // ── OP 5 ──
    @Test
    void listarMercaderiasPorIdDescendenteOk() {
        s.registrarMercaderia("COD02", "XX-002-XXX123", "Descripcion 2", false, Categoria.OTROS);
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        s.registrarMercaderia("COD03", "XX-003-XXX123", "Descripcion 3", false, Categoria.OTROS);
        retorno = s.listarMercaderiasPorIdDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("COD03;XX-003-XXX123;Descripcion 3;false;Otros|COD02;XX-002-XXX123;Descripcion 2;false;Otros|COD01;XX-001-XXX123;Descripcion 1;false;Otros", retorno.getValorString());
    }

    // ── OP 6 ──
    @Test
    void buscarMercaderiaPorCodigoOk() {
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion 1", false, Categoria.OTROS);
        retorno = s.buscarMercaderiaPorCodigo("XX-001-XXX123");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void buscarMercaderiaPorCodigoError1() {
        retorno = s.buscarMercaderiaPorCodigo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarMercaderiaPorCodigo("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarMercaderiaPorCodigo("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void buscarMercaderiaPorCodigoError2() {
        retorno = s.buscarMercaderiaPorCodigo("XX-001-XXX123");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    // ── OP 7 ──
    @Test
    void listarMercaderiasPorCodigoAscendenteOk() {
        s.registrarMercaderia("COD01", "XX-003-XXX123", "Descripcion 1", false, Categoria.OTROS);
        s.registrarMercaderia("COD02", "XX-001-XXX123", "Descripcion 2", false, Categoria.OTROS);
        s.registrarMercaderia("COD03", "XX-002-XXX123", "Descripcion 3", false, Categoria.OTROS);
        retorno = s.listarMercaderiasPorCodigoAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("COD02;XX-001-XXX123;Descripcion 2;false;Otros|COD03;XX-002-XXX123;Descripcion 3;false;Otros|COD01;XX-003-XXX123;Descripcion 1;false;Otros", retorno.getValorString());
    }

    // ── OP 8 ──
    @Test
    void listarMercaderiasPorCategoriaOk() {
        s.registrarMercaderia("COD01", "XX-001-XXX123", "Descripcion 1", false, Categoria.TEXTIL);
        s.registrarMercaderia("COD02", "XX-002-XXX123", "Descripcion 2", false, Categoria.OTROS);
        s.registrarMercaderia("COD03", "XX-003-XXX123", "Descripcion 3", false, Categoria.TEXTIL);
        retorno = s.listarMercaderiasPorCategoria(Categoria.TEXTIL);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("COD01;XX-001-XXX123;Descripcion 1;false;Textil|COD03;XX-003-XXX123;Descripcion 3;false;Textil", retorno.getValorString());
    }

    @Test
    void listarMercaderiasPorCategoriaVaciaOk() {
        retorno = s.listarMercaderiasPorCategoria(Categoria.ELECTRONICA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    // ── OP 9 ──
    @Test
    void registrarCentroLogisticoOk() {
        retorno = s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError1() {
        Sistema s2 = new ImplementacionSistema();
        s2.inicializarSistema(4);
        s2.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s2.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        s2.registrarCentroLogistico("CL03", "Centro 3", "Montevideo", "Direccion 3");
        s2.registrarCentroLogistico("CL04", "Centro 4", "Montevideo", "Direccion 4");
        retorno = s2.registrarCentroLogistico("CL05", "Centro 5", "Montevideo", "Direccion 5");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError2() {
        retorno = s.registrarCentroLogistico(null, "Centro 1", "Montevideo", "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("", "Centro 1", "Montevideo", "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", null, "Montevideo", "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", "", "Montevideo", "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", "Centro 1", null, "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", "Centro 1", "", "Direccion 1");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", null);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError3() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        retorno = s.registrarCentroLogistico("CL01", "Centro 2", "Montevideo", "Direccion 2");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    // ── OP 10 ──
    @Test
    void registrarConexionOk() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        retorno = s.registrarConexion("CL01", "CL02", 100, 60);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarConexionError1() {
        retorno = s.registrarConexion(null, "CL02", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarConexion("CL01", null, 100, 60);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarConexionError2() {
        retorno = s.registrarConexion("CL01", "CL02", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarConexionError3() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        retorno = s.registrarConexion("CL01", "CL02", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void registrarConexionError4() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        retorno = s.registrarConexion("CL01", "CL02", 0, 60);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    void registrarConexionError5() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        retorno = s.registrarConexion("CL01", "CL02", 100, 0);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }

    @Test
    void registrarConexionError6() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        s.registrarConexion("CL01", "CL02", 100, 60);
        retorno = s.registrarConexion("CL01", "CL02", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());
    }

    // ── OP 11 ──
    @Test
    void redCentrosPorCantidadDeConexionesOk() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        s.registrarCentroLogistico("CL03", "Centro 3", "Montevideo", "Direccion 3");
        s.registrarConexion("CL01", "CL02", 100, 60);
        s.registrarConexion("CL02", "CL03", 100, 60);
        retorno = s.redCentrosPorCantidadDeConexiones("CL01", 2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError1() {
        retorno = s.redCentrosPorCantidadDeConexiones("CL01", -1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError2() {
        retorno = s.redCentrosPorCantidadDeConexiones(null, 2);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.redCentrosPorCantidadDeConexiones("", 2);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError3() {
        retorno = s.redCentrosPorCantidadDeConexiones("CL01", 2);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    // ── OP 12 ──
    @Test
    void viajeCostoMinimoDistanciaOk() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        s.registrarCentroLogistico("CL03", "Centro 3", "Montevideo", "Direccion 3");
        s.registrarConexion("CL01", "CL02", 100, 60);
        s.registrarConexion("CL02", "CL03", 50, 30);
        retorno = s.viajeCostoMinimoDistancia("CL01", "CL03");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(150, retorno.getValorInteger());
    }

    @Test
    void viajeCostoMinimoDistanciaError1() {
        retorno = s.viajeCostoMinimoDistancia(null, "CL02");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoDistancia("CL01", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError2() {
        retorno = s.viajeCostoMinimoDistancia("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError3() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        retorno = s.viajeCostoMinimoDistancia("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError4() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        retorno = s.viajeCostoMinimoDistancia("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    // ── OP 13 ──
    @Test
    void viajeCostoMinimoTiempoOk() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        s.registrarCentroLogistico("CL03", "Centro 3", "Montevideo", "Direccion 3");
        s.registrarConexion("CL01", "CL02", 100, 60);
        s.registrarConexion("CL02", "CL03", 50, 30);
        retorno = s.viajeCostoMinimoTiempo("CL01", "CL03");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(90, retorno.getValorInteger());
    }

    @Test
    void viajeCostoMinimoTiempoError1() {
        retorno = s.viajeCostoMinimoTiempo(null, "CL02");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoTiempo("CL01", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoTiempoError2() {
        retorno = s.viajeCostoMinimoTiempo("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoTiempoError3() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        retorno = s.viajeCostoMinimoTiempo("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoTiempoError4() {
        s.registrarCentroLogistico("CL01", "Centro 1", "Montevideo", "Direccion 1");
        s.registrarCentroLogistico("CL02", "Centro 2", "Montevideo", "Direccion 2");
        retorno = s.viajeCostoMinimoTiempo("CL01", "CL02");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }


}
