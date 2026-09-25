package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.Date;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import java.math.BigDecimal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.util.MovimientoReingresoTypeBuilder;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReingresoServiceBusinessRemote;

public class ReingresoServiceBusinessTestIt {

    private ReingresoServiceBusinessRemote ejb = EjbLocator.getReingresoServiceBusiness();

    @Test
    public void testEncolarMovimientosReingresoEstudiantes() {

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 --> Verificar
                .withNumFolio(calcularFolio("16"))   //dia juliano + subdelegacion.clave
                .withArgumento(1)                    //0 o 1 no sabemos en base a que se define
                .withRegPatron("C674368010")         //8 caracteres RP + 2 modalidad
                .withDigVrPat(3)                     //
                .withFMovto(new Date())              // --> Verificar si es fecha actual
                .withFRecepMovi(new Date())          // --> Verificar si es fecha actual
                .withCveUnica("EICF930101MOCSLL05")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc(null)                 //
                .withDigVrNss(0)                     //Averiguar donde se guarda
                .withSalBase(BigDecimal.ZERO)        //Obtener
                .withUmf(93)                         //
                .withNombre("FLOR DE LIZ")           //
                .withPrimerApellido("ESPINA")        //
                .withSegundoApellido("CLARA")        //
                .withSexo(2)
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 --> Verificar
                .withNumFolio(calcularFolio("16"))   //dia juliano + subdelegacion.clave
                .withArgumento(1)                    //0 o 1 no sabemos en base a que se define
                .withRegPatron("T703927010")         //8 caracteres RP + 2 modalidad
                .withDigVrPat(3)                     //
                .withFMovto(new Date())              // --> Verificar si es fecha actual
                .withFRecepMovi(new Date())          // --> Verificar si es fecha actual
                .withCveUnica("MANV940101MVZRNR06")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc(null)                 //
                .withDigVrNss(0)                     //Averiguar donde se guarda
                .withSalBase(BigDecimal.ZERO)        //
                .withUmf(93)                         //
                .withNombre("VIRIDIANA")             //
                .withPrimerApellido("MARTINEZ")      //
                .withSegundoApellido("NANDEZ")       //
                .withSexo(2)
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 --> Verificar
                .withNumFolio(calcularFolio("16"))   //dia juliano + subdelegacion.clave
                .withArgumento(1)                    //0 o 1 no sabemos en base a que se define
                .withRegPatron("C674368010")         //8 caracteres RP + 2 modalidad
                .withDigVrPat(3)                     //
                .withFMovto(new Date())              // --> Verificar si es fecha actual
                .withFRecepMovi(new Date())          // --> Verificar si es fecha actual
                .withCveUnica("TOHA940101HVZVRL04")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc(null)                 //
                .withDigVrNss(0)                     //Averiguar donde se guarda
                .withSalBase(BigDecimal.ZERO)        //Obtener
                .withUmf(93)                         //
                .withNombre("ALDO GERARDO")          //
                .withPrimerApellido("TOVIAS")        //
                .withSegundoApellido("HERNANDEZ")    //
                .withSexo(1)
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 --> Verificar
                .withNumFolio(calcularFolio("16"))   //dia juliano + subdelegacion.clave
                .withArgumento(1)                    //0 o 1 no sabemos en base a que se define
                .withRegPatron("T703927010")         //8 caracteres RP + 2 modalidad
                .withDigVrPat(3)                     //
                .withFMovto(new Date())              // --> Verificar si es fecha actual
                .withFRecepMovi(new Date())          // --> Verificar si es fecha actual
                .withCveUnica("SOBE910101MVZLRF00")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc(null)                 //
                .withDigVrNss(0)                     //Averiguar donde se guarda
                .withSalBase(BigDecimal.ZERO)        //
                .withUmf(93)                         //
                .withNombre("EUFROCINA")             //
                .withPrimerApellido("SOLIS")         //
                .withSegundoApellido("BARRON")       //
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 --> Verificar
                .withNumFolio(calcularFolio("16"))   //dia juliano + subdelegacion.clave
                .withArgumento(1)                    //0 o 1 no sabemos en base a que se define
                .withRegPatron("T703927010")         //8 caracteres RP + 2 modalidad
                .withDigVrPat(3)                     //
                .withFMovto(new Date())              // --> Verificar si es fecha actual
                .withFRecepMovi(new Date())          // --> Verificar si es fecha actual
                .withCveUnica("JIGX940101MVZMNC04")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc(null)                 //
                .withDigVrNss(0)                     //Averiguar donde se guarda
                .withSalBase(BigDecimal.ZERO)        //
                .withUmf(93)                         //
                .withNombre("XOCHITL CITLALLI")      //
                .withPrimerApellido("JIMENEZ")       //
                .withSegundoApellido("GONZALEZ")      //
                .withSexo(2)
                .build());

    }
    
    @Test
    @Ignore
    public void testEncolarMovimientoReingreso() {

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                      //Norte D.F.
                .withSubOrig(16)                      // Polanco
                .withCveAplic(2)                      //2
                .withTpMovto(8)                       //8
                .withOrigenMov(1)                     //1 ???
                .withNumFolio(calcularFolio("16"))  //int
                .withArgumento(0)                     //int 0 o 1
                .withRegPatron("T703927010")          //Con modalidad
                .withDigVrPat(3)                      //int
                .withFMovto(new Date())               //Date
                .withFRecepMovi(new Date())           //Date
                .withCveUnica("HEOG681208MDFRRY08")   //Curp--Opcional
                //.withIdSubrServ()                   //Opcional
                .withIdEventual(0)                    //int 1/0
                .withNumSegSoc("1129701197")         //String
                .withDigVrNss(1)                      //Averiguar donde se guarda
                //.withIdExtemp()                     //Opcional
                //.withReducPago()                    //Opcional
                //.withExtODel()                      //Opcional
                .withSalBase(new BigDecimal(350d))    //obtener
                //.withSalInfonavit()                 //Opcional
                //.withTpSalario()                    //Opcional
                //.withSexo(1)                        //Opcional
                //.withMesNac(7)                      //Opcional
                //.withLugarNac()                     //Opcional
                .withUmf(93)                          //Obtener
                //.withAutPerm()                      //Opcional
                //.withDelDest()                      //Opcional
                //.withSubDest()                      //Opcional
                //.withTpDerech()                     //Opcional
                //.withAaNac()                        //Opcional
                //.withSituacion()                    //Opcional
                //.withTsalODel()                     //Opcional
                //.withMesNacAp()                     //Opcional
                //.withNssCorr()                      //Opcional
                //.withDigVrNssCorr()                 //Opcional
                //.withTpPens()                       //Opcional
                //.withAlfGuar()                      //Opcional
                //.withNumGuar()                      //Opcional
                //.withCondicion()                    //Opcional
                //.withLocMpio()                      //Opcional
                //.withTpProrroga()                   //Opcional
                //.withFecTerProrr(null)              //Se llena en el OSB con 17171000
                //.withIdPd()                         //Opcional
                .withNombre("GYSEL")                  //String
                .withPrimerApellido("HERRERA")        //String
                .withSegundoApellido("OROZCO")        //String
                //.withNombreAsegC()                  //Opcional
                //.withPrimerApellidoAsegC()          //Opcional
                //.withSegundoApellidoAsegC()         //Opcional
                //.withNombreDh()                     //Opcional
                //.withPrimerApellidoDh()             //Opcional
                //.withSegundoApellidoDh()            //Opcional
                .build());


        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                    //Norte D.F.
                .withSubOrig(16)                    // Polanco
                .withCveAplic(2)                    //2
                .withTpMovto(8)                     //8
                .withOrigenMov(1)                   //1 ???
                .withNumFolio(calcularFolio("16"))  //int
                .withArgumento(1)                   //int 0 o 1
                .withRegPatron("C674368010")        //Con modalidad
                .withDigVrPat(1)                    //int
                .withFMovto(new Date())             //Date
                .withFRecepMovi(new Date())         //Date
                .withCveUnica("")                   //Curp--Opcional
                .withIdEventual(0)                  //int 1/0
                .withNumSegSoc("1007302084")        //String
                .withDigVrNss(1)                    //Averiguar donde se guarda
                .withSalBase(new BigDecimal(350d))  //obtener
                .withUmf(93)                        //Obtener
                .withNombre("RUIZ")                 //String
                .withPrimerApellido("JUAREZ")       //String
                .withSegundoApellido("CARIDAD")     //String
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     // Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 ???
                .withNumFolio(calcularFolio("16"))   //int
                .withArgumento(1)                    //int 0 o 1
                .withRegPatron("T703927010")         //Con modalidad
                .withDigVrPat(3)                     //int
                .withFMovto(new Date())              //Date
                .withFRecepMovi(new Date())          //Date
                .withCveUnica("MAXB490504MDFRXR04")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc("1007302092")         //String
                .withDigVrNss(1)                     //Averiguar donde se guarda
                .withSalBase(new BigDecimal(350d))   //obtener
                .withUmf(93)                         //Obtener
                .withNombre("MARIA BERTHA")          //String
                .withPrimerApellido("MARTINEZ")      //String
                .withSegundoApellido(null)           //String
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     // Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 ???
                .withNumFolio(calcularFolio("16"))   //int
                .withArgumento(1)                    //int 0 o 1
                .withRegPatron("C674368010")         //Con modalidad
                .withDigVrPat(3)                     //int
                .withFMovto(new Date())              //Date
                .withFRecepMovi(new Date())          //Date
                .withCveUnica("LOOF490721HNENSR07")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc("1007302100")         //String
                .withDigVrNss(1)                     //Averiguar donde se guarda
                .withSalBase(new BigDecimal(350d))   //obtener
                .withUmf(93)                         //
                .withNombre("FERNANDO")              //String
                .withPrimerApellido("LONDO\u00D1O")  //String
                .withSegundoApellido("OSPINA")       //String
                .build());

        ejb.encolarMovimientoReingreso(new MovimientoReingresoTypeBuilder()
                .withDelOrig(39)                     //Norte D.F.
                .withSubOrig(16)                     //Polanco
                .withCveAplic(2)                     //2
                .withTpMovto(8)                      //8
                .withOrigenMov(1)                    //1 ???
                .withNumFolio(calcularFolio("16"))   //int
                .withArgumento(1)                    //int 0 o 1
                .withRegPatron("T703927010")         //Con modalidad
                .withDigVrPat(3)                     //int
                .withFMovto(new Date())              //Date
                .withFRecepMovi(new Date())          //Date
                .withCveUnica("GOGS480811MDFNMS02")  //Curp--Opcional
                .withIdEventual(0)                   //int 1/0
                .withNumSegSoc("1007302118")         //String
                .withDigVrNss(1)                     //Averiguar donde se guarda
                .withSalBase(new BigDecimal(350d))   //obtener
                .withUmf(93)                         //Obtener
                .withNombre("MARIA SUSANA RAQUEL")   //String
                .withPrimerApellido("GONZALEZ")      //String
                .withSegundoApellido("GOMEZ")        //String
                .build());

    }

    private Integer calcularFolio(String subdel) {
        String folio = String.format("%tj%s", System.currentTimeMillis(), subdel)
            .replaceFirst("^0+", "");
        return new Integer(folio);
    }

}

