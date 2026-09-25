package mx.gob.imss.cit.cda.service.business;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.base.Ambiente;
import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.MotivosAclaracionErroneosException;
import mx.gob.imss.cit.cda.service.interfaces.test.CuentaIndividualTestRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;

import org.junit.Ignore;
import org.junit.Test;

public class CuentaIndividualTest {

    @Test    
    public void buscarNssPorFolio() throws MotivosAclaracionErroneosException {
      
      /*
       * +--------------------------------+
       * |      DIT_DETALLE_NSS_CDA       |
       * +--------------------------------+
       * | CVE_ID_CORRECCION_DATOS_ASEG   |        15166        15166        15166 
       * | CVE_ID_DETALLE_NSS_CDA         |         9828         9829         9830
       * | NUM_NSS                        |  01564101580  01634441057  01441900030
       * +--------------------------------+
       * 
       * +------------------------+
       * | DIT_CTA_IND_NSS_CDA    |
       * +------------------------+
       * | CVE_REGISTRO_PATRONAL  |    
       * | CVE_ID_DETALLE_NSS_CDA |  
       * | COUNT                  |
       * +------------------------+
       * 9828	11710214179	16
       * 9828	B0116701100	2
       * 9828	C4114758103	1
       * 9828	D0899999002	1
       * 9828	Y5014652100	5
       * 9828	Y6047393100	1
       * 9829	01013949100	13
       * 9829	01082017102	1
       * 9829	A0148985102	1
       * 9829	A0199999002	1
       * 9829	Y4529632103	1
       * 9829	Y5221772105	3
       * 9830	C8919602101	2
       * 9830	C8999999161	8
       * 9830	Y5699999008	1

       * 
       * +------------------------------+
       * |  DIT_MOV_ACLARACION_NSS_CDA  |
       * +------------------------------+
       * | CVE_ID_DETALLE_NSS_CDA       |  9828  9829  9829  9830
       * | CVE_ID_MOV_CORRECCION        |  1680  1683  1681  1686
       * | CVE_ID_TIPO_TRAM_CORREC_NSS  |     7     3     7     5
       * +------------------------------+ ILOGICA HOMONIMIA  INVASI0N
       */
        
        CuentaIndividualRemote servicio = EjbLocator.find(CuentaIndividualRemote.class, Ambiente.LOCAL);       
        System.out.println( servicio.obtenerCuentaIndividual(15166L) );                        
        //System.out.println( servicio.obtenerListaCuentaIndividualRegistroPatronal(9828L) );
        
        
        CuentaIndividualRegistroPatronal registroPatronal = new CuentaIndividualRegistroPatronal();
        registroPatronal.setCveIdDetalleNssCda(9828L);
        registroPatronal.setNumeroRegistroPatronal("11710214179");
        registroPatronal.setNssDestino( new CuentaIndividualNss() );
        registroPatronal.getNssDestino().setNss("01441900030");
        registroPatronal.getNssDestino().setCveIdDetalleNssCda(9830L);
        registroPatronal.getNssDestino().setMovimientoAclaracionHomonimia(
              1686L);
        
        //System.out.println( servicio.registrarCorreccionNssRegistroPatronal(registroPatronal) );
        
        
        PageCuentaIndividualPeriodo page = servicio.obtenerPageCuentaIndividualPeriodo(registroPatronal, 1);
        
        System.out.println( page );
        
        //PageCuentaIndividualPeriodo page = servicio.obtenerPageCuentaIndividualCorreccion(registroPatronal, 2);
        
        System.out.println( "Periodos: " + page.getData().size() );
        System.out.println( page.getData() );
        
        System.out.println( "Incluidos: " + page.getIncluidos().size() );
        System.out.println( page.getIncluidos() );
        for( CuentaIndividualCorreccion correccion : page.getIncluidos() ){
          System.out.println( "  --Origen: " + correccion.getOrigen() );
          System.out.println( "  --Destino: " + correccion.getDestino() );
        }
        System.out.println( "Eliminados: " + page.getEliminados().size() );
        System.out.println( page.getEliminados());
        System.out.println( "Modificados: " + page.getModificados().size() );
        System.out.println( page.getModificados());
        System.out.println( "Nuevos: " + page.getNuevos().size() );
        System.out.println( page.getNuevos());
        
        page.setNuevos( new ArrayList<CuentaIndividualCorreccion>() );
        page.setEliminados( new ArrayList<CuentaIndividualCorreccion>() );
        page.setIncluidos( new ArrayList<CuentaIndividualCorreccion>() );
        
        System.out.println( servicio.registrarListaCuentaIndividualCorreccion(page) );
        
        /*System.out.println( servicio.obtenerPageCuentaIndividualPeriodo(registroPatronal, 1) );
        System.out.println( servicio.obtenerPageCuentaIndividualPeriodo(registroPatronal, 2) );

        System.out.println( servicio.obtenerPageCuentaIndividualCorreccion(registroPatronal, 1) );
        System.out.println( servicio.obtenerPageCuentaIndividualCorreccion(registroPatronal, 2) );*/
        
        
        
    }

    @Test
    @Ignore
    public void persistirPeriodos() {
        System.out.println("INICIA PERSISTENCIA");

        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        CuentaIndividualNss cuentaIndividualNss = new CuentaIndividualNss();
        cuentaIndividualNss.setNss("95119312213");
        cuentaIndividualNss.setCveIdDetalleNssCda(7101L);
        List<PeriodosRegistroPatronal> listaPeriodosRegistroPatronal = new ArrayList<PeriodosRegistroPatronal>();

        PeriodosRegistroPatronal periodosRegistroPatronal = new PeriodosRegistroPatronal();
        periodosRegistroPatronal.setClaveDelegacionOrigen(22);
        periodosRegistroPatronal.setClaveCiz(3);

        List<PeriodoCuentaIndividual> periodos = new ArrayList<PeriodoCuentaIndividual>();
        for (int i = 0; i <= 20; i++) {
            PeriodoCuentaIndividual periodoCuentaIndividual = new PeriodoCuentaIndividual();
            periodoCuentaIndividual.setEventual("0");
            periodoCuentaIndividual.setJornadaSemanal("0");
            periodoCuentaIndividual.setTipoSalario("0");
            periodoCuentaIndividual.setOrigenMovimientoInicial("0");
            periodoCuentaIndividual.setOrigenMovimientoFinal("0");
            periodoCuentaIndividual.setTipoMovimientoInicial(1);
            periodoCuentaIndividual.setTipoMovimientoFinal(2);
            periodoCuentaIndividual.setSubrogacionServicio("0");
            periodoCuentaIndividual.setHuelga("0");
            periodoCuentaIndividual.setExtemporaneoConvenioSuspension("0");
            periodos.add(periodoCuentaIndividual);
        }

        periodosRegistroPatronal.setPeriodos(periodos);
        listaPeriodosRegistroPatronal.add(periodosRegistroPatronal);
//        cuentaIndividualNss.setListaPeriodosRegistroPatronal(listaPeriodosRegistroPatronal);

        servicio.persistirPeriodo(cuentaIndividualNss);

        System.out.println("TERMINA PERSISTENCIA");

    }

    @Test
    @Ignore
    public void obtenerConsecutivo() {
        System.out.println("OBTENIENDO CONSECUTIVO");
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        Long consecutivo = servicio.obtenerConsecutivo(95119312239L);//Ya tiene registros
        System.out.println("EL consecutivo es:" + consecutivo);
    }

    @Test
    @Ignore
    public void consultarPeriodos() {
        System.out.println("-----INICIA PRUEBA DE CONSULTA PERIODOS");

        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);

        List<PeriodosRegistroPatronal> listaPeriodos = servicio.buscarPeriodosRegistroPatronalPorNss("62765412317");
        
        if (listaPeriodos != null) {
            for (PeriodosRegistroPatronal periodo : listaPeriodos){
                System.out.println(" --> Periodo registro patronal:  " + periodo.getNumeroRegistroPatronal() +
                        " | datos Delegacion: clave " + periodo.getClaveDelegacionOrigen() + " | nombre " + periodo.getNombreDelegacionOrigen() +
                        " | núm. periodos cuenta individual: " + periodo.getPeriodos().size());
//                        for (PeriodoCuentaIndividual perCtaInd: periodo.getPeriodos()) {
//                        }
            }
        }

        System.out.println("----FINALIZA PRUEBA DE CONSULTA PERIODOS");
    }
    
    
    @Ignore
    @Test
    public void consulta01() throws CuentaIndividualNoDisponibleException {
        System.out.println("-----INICIA PRUEBA DE CONSULTA PERIODOS");

        CuentaIndividualRemote servicio = EjbLocator.find(CuentaIndividualRemote.class, Ambiente.LOCAL);

        CuentaIndividualNss cuentaIndividualNss = servicio.findByFolioNss("153314087033274811256", "95119312213");
        
        assertNotNull( "La cuenta no es nula", cuentaIndividualNss  );
        
        System.out.println( cuentaIndividualNss );
        

        System.out.println("----FINALIZA PRUEBA DE CONSULTA PERIODOS");
    }
    
    
    @Test
    @Ignore
    public void persist01() {
        System.out.println("-----INICIA PRUEBA DE PERSISTENCIA 01");

        CuentaIndividualRemote servicio = EjbLocator.find(CuentaIndividualRemote.class, Ambiente.LOCAL);

        //CuentaIndividualNss cuentaIndividualNss = servicio.findByFolioNss("153314087033274811256", "95119312213");
        
        //assertNotNull( "La cuenta no es nula", cuentaIndividualNss  );
        
        //System.out.println( cuentaIndividualNss );
        
        CuentaIndividual cuentaIndividual = new CuentaIndividual();
/*        cuentaIndividual.setFolioSolicitud("153314087033274811256");
        
        cuentaIndividual.setListaCuentaIndividualNssCertificador( 
          new ArrayList<CuentaIndividualNss>() );
        cuentaIndividual.setListaCuentaIndividualNssAsociado(
          new ArrayList<CuentaIndividualNss>() );
        cuentaIndividual.setListaCuentaIndividualNssNoPertenece(
              new ArrayList<CuentaIndividualNss>() );
        
        */
        CuentaIndividualNss cuentaIndividualNss = new CuentaIndividualNss();
        
        cuentaIndividualNss.setCveIdDetalleNssCda(7169L);
        /*
        cuentaIndividualNss.setListaPeriodosRegistroPatronal(
          new ArrayList<PeriodosRegistroPatronal>() );
        cuentaIndividualNss.getListaPeriodosRegistroPatronal().add( new PeriodosRegistroPatronal() );
        
        cuentaIndividual.getListaCuentaIndividualNssCertificador().add( cuentaIndividualNss );
       
        
        PeriodosRegistroPatronal rp = cuentaIndividualNss.getListaPeriodosRegistroPatronal().get(0);
        
        rp.setPeriodosNuevos( new ArrayList<PeriodoCuentaIndividual>() );
        */
        /**Periodos Nuevos**/
        PeriodoCuentaIndividual nuevos = new PeriodoCuentaIndividual();
        nuevos.setClaveCiz(1);
        nuevos.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.AGREGAR);
        nuevos.setCveIdPeriodoCuentaIndividual(null);
        nuevos.setNss("74907376631");
        
        /**Periodos Modificados**/
        PeriodoCuentaIndividual modificados = new PeriodoCuentaIndividual();
        modificados.setClaveCiz(1);
        modificados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.MODIFICAR);
        modificados.setCveIdPeriodoCuentaIndividual(null);
        modificados.setCveIdPeriodoAnterior(24900L);
        modificados.setNss("74907376631");
        
        /**Periodos Eliminados**/
        PeriodoCuentaIndividual eliminados = new PeriodoCuentaIndividual();
        eliminados.setClaveCiz(1);
        eliminados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.ELIMINAR);
        eliminados.setCveIdPeriodoCuentaIndividual(null);
        eliminados.setCveIdPeriodoAnterior(24901L);
        
        /**Periodos Incluidos Movimiento NSS**/
        PeriodoCuentaIndividual incluidoNss = new PeriodoCuentaIndividual();
        incluidoNss.setClaveCiz(1);
        incluidoNss.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.INCLUIR);
        incluidoNss.setCveIdPeriodoAnterior(24900L);
        incluidoNss.setCveIdPeriodoCuentaIndividual(null);
        incluidoNss.setNss("04907391009");
        
        /**Periodos Incluidos Movimiento Modificados**/
        PeriodoCuentaIndividual incluidoModificados = new PeriodoCuentaIndividual();
        incluidoModificados.setClaveCiz(1);
        incluidoModificados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.MODIFICAR);
        incluidoModificados.setCveIdPeriodoCuentaIndividual(null);
        incluidoModificados.setCveIdPeriodoAnterior(24900L);
        incluidoModificados.setNss("04907391009");
        /*
        rp.getPeriodosNuevos().add( nuevos );
        rp.getPeriodosEliminados().add(eliminados);
        rp.getPeriodosIncluidos().add(incluidoNss);
        rp.getPeriodosIncluidos().add(incluidoModificados);
        rp.getPeriodosModificados().add(modificados);
        */
        System.out.println("-----INICIA PRUEBA DE PERSISTENCIA 01-a");
        cuentaIndividual = servicio.save(cuentaIndividual);
        
        System.out.println( cuentaIndividual );

        System.out.println("----FINALIZA PRUEBA DE CONSULTA PERIODOS");
    }
    
    @Test
    @Ignore
    public void consultarMovimientosCuentaIndividual() {
        System.out.println("Inicia");
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        servicio.obtenerMovimientosByFolio("153314087033274811256");
        System.out.println("Termina");
    }
    
    @Test
    @Ignore
    public void guardarAclaraciones() {
/*        System.out.println("Inicia guardado de movimientos de periodos");
        CuentaIndividualRemote servicioCuentaIndividual = EjbLocator.find(CuentaIndividualRemote.class, Ambiente.LOCAL);
        
        CuentaIndividual cuentaIndividual = new CuentaIndividual();
        cuentaIndividual.setFolioSolicitud("153783121062874819426");
        
        cuentaIndividual.setListaCuentaIndividualNssCertificador( new ArrayList<CuentaIndividualNss>() );
        cuentaIndividual.setListaCuentaIndividualNssAsociado(new ArrayList<CuentaIndividualNss>() );
        cuentaIndividual.setListaCuentaIndividualNssNoPertenece( new ArrayList<CuentaIndividualNss>() );
        
        //Certificador
        CuentaIndividualNss cuentaIndividualNssCertificador = new CuentaIndividualNss();
        cuentaIndividualNssCertificador.setCveIdDetalleNssCda(7327L);
        cuentaIndividualNssCertificador.setListaPeriodosRegistroPatronal(new ArrayList<PeriodosRegistroPatronal>() );
        cuentaIndividualNssCertificador.getListaPeriodosRegistroPatronal().add( new PeriodosRegistroPatronal() );
        cuentaIndividual.getListaCuentaIndividualNssCertificador().add( cuentaIndividualNssCertificador );
        
        //NSS Asociados
        CuentaIndividualNss cuentaIndividualNssAsociado = new CuentaIndividualNss();
        cuentaIndividualNssAsociado.setCveIdDetalleNssCda(7282L);
        cuentaIndividualNssAsociado.setListaPeriodosRegistroPatronal(new ArrayList<PeriodosRegistroPatronal>() );
        cuentaIndividualNssAsociado.getListaPeriodosRegistroPatronal().add( new PeriodosRegistroPatronal() );
        cuentaIndividual.getListaCuentaIndividualNssAsociado().add( cuentaIndividualNssAsociado );
        
        //No pertenecen
        CuentaIndividualNss cuentaIndividualNssNoPertenece = new CuentaIndividualNss();
        cuentaIndividualNssNoPertenece.setCveIdDetalleNssCda(7281L);
        cuentaIndividualNssNoPertenece.setListaPeriodosRegistroPatronal(new ArrayList<PeriodosRegistroPatronal>() );
        cuentaIndividualNssNoPertenece.getListaPeriodosRegistroPatronal().add( new PeriodosRegistroPatronal() );
        cuentaIndividual.getListaCuentaIndividualNssNoPertenece().add( cuentaIndividualNssNoPertenece );
        
        //****Periodos Certificador un sólo NSS**//*
        PeriodosRegistroPatronal rpCertificador = cuentaIndividualNssCertificador.getListaPeriodosRegistroPatronal().get(0);
        
        //**Periodos Nuevos**//*
        PeriodoCuentaIndividual nuevos = new PeriodoCuentaIndividual();
        nuevos.setClaveCiz(1);
        nuevos.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.AGREGAR);
        nuevos.setCvePeriodoCuentaIndividual(null);
        nuevos.setNss("13927001332");
        
        //**Periodos Modificados**//*
        PeriodoCuentaIndividual modificados = new PeriodoCuentaIndividual();
        modificados.setClaveCiz(1);
        modificados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.MODIFICAR);
        modificados.setCvePeriodoCuentaIndividual(null);
        modificados.setCveIdPeriodoAnterior(26906L);
        modificados.setNss("13927001332");
        
        //**Periodos Eliminados**//*
        PeriodoCuentaIndividual eliminados = new PeriodoCuentaIndividual();
        eliminados.setClaveCiz(1);
        eliminados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.ELIMINAR);
        eliminados.setCvePeriodoCuentaIndividual(null);
        eliminados.setCveIdPeriodoAnterior(26906L);//cambiar
        
        rpCertificador.getPeriodosNuevos().add( nuevos );
        rpCertificador.getPeriodosEliminados().add(eliminados);
        rpCertificador.getPeriodosModificados().add(modificados);
        
        //****Periodos Asociados un sólo NSS**//*
        PeriodosRegistroPatronal rpAsociados = cuentaIndividualNssAsociado.getListaPeriodosRegistroPatronal().get(0);
        
        //**Periodos Nuevos**//*
        PeriodoCuentaIndividual nuevosAsociados = new PeriodoCuentaIndividual();
        nuevos.setClaveCiz(1);
        nuevos.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.AGREGAR);
        nuevos.setCvePeriodoCuentaIndividual(null);
        nuevos.setNss("02947038648");
        
        rpCertificador.getPeriodosNuevos().add( nuevos );
        
        //****Periodos No pertenece un sólo NSS**//*
        PeriodosRegistroPatronal rpPertenece = cuentaIndividualNssNoPertenece.getListaPeriodosRegistroPatronal().get(0);
        
        //**Periodos Incluidos Movimiento NSS - Se mueve de No pertenece a Certificador**//* 
        PeriodoCuentaIndividual incluidoNss = new PeriodoCuentaIndividual();
        incluidoNss.setClaveCiz(1);
        incluidoNss.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.INCLUIR);
        incluidoNss.setCveIdPeriodoAnterior(24900L);
        incluidoNss.setCvePeriodoCuentaIndividual(null);
        incluidoNss.setNss("02947038648");
        
        //**Periodos Incluidos Movimiento Modificados - Se Mueve de Nss y despues se modifica**//*
        PeriodoCuentaIndividual incluidoModificados = new PeriodoCuentaIndividual();
        incluidoModificados.setClaveCiz(1);
        incluidoModificados.setTipoRegularizacionPeriodo(TipoRegularizacionPeriodoEnum.MODIFICAR);
        incluidoModificados.setCvePeriodoCuentaIndividual(null);
        incluidoModificados.setCveIdPeriodoAnterior(24900L);
        incluidoModificados.setNss("01029900022");
        
        rpPertenece.getPeriodosIncluidos().add(incluidoNss);
        rpPertenece.getPeriodosIncluidos().add(incluidoModificados);
        
        
        System.out.println("----- ENTRA Servicio de guardado movimientos y periodos");
        cuentaIndividual = servicioCuentaIndividual.save(cuentaIndividual);

        System.out.println("----FINALIZA Servicio de guardado movimientos y periodos");*/
        
        
        System.out.println("INICIA Guardado de aclaraciones");
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        servicio.guardarAclaraciones("153783121062874819426");
        
        System.out.println("TERMIAN Guardado de aclaraciones");
    }
    
    @Test
    @Ignore
    public void consulta02() throws CuentaIndividualNoDisponibleException {
        System.out.println("-----INICIA PRUEBA DE CONSULTA DE CUENTA INDIVIDUAL");

        CuentaIndividualRemote servicio = EjbLocator.find(CuentaIndividualRemote.class, Ambiente.LOCAL);

        CuentaIndividual cuentaIndividual = servicio.findByFolio("153314087033274811256");
        
        assertNotNull( "La cuenta no es nula", cuentaIndividual  );
        
        System.out.println( cuentaIndividual );
        

        System.out.println("----FINALIZA PRUEBA DE CONSULTA INDIVIDUAL");
    }
    
    @Test
    @Ignore
    public void consultaTipoTramite() {
        System.out.println("-----INICIA OBTENER TIPO TRAMITE");
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        servicio.obtenerTipoTramiteAclaracion("");
        System.out.println("-----TERMINA OBRTENER TIPO TRAMITE");
    }
    
    @Test
    @Ignore
    public void obtenerTramitesPorFolio(){
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        servicio.obtenerTramitesPorFolio("153824317580674821751");
    }
    
    @Test
    @Ignore
    public void obtenerTramitesPorFolioyNss(){
        CuentaIndividualTestRemote servicio = EjbLocator.find(CuentaIndividualTestRemote.class, Ambiente.LOCAL);
        List<String> nss = new ArrayList<String>();
        nss.add("43927477893"); 
        nss.add("03927482855");
        
        for(String elementNss: nss){
            System.out.println("NSS:" + elementNss);
            servicio.obtenerTramitesPorFolioyNss("153824317580674821751", elementNss);
            System.out.println("------");
        }
    }

}
