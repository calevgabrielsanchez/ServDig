package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.JaxbUtilT;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.util.List;

/**
 * Created by eduardo.serrano on 17/05/2017.
 */
public class ConsultaSeguroIvroServiceBusinessTest {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ConsultaSeguroIvroServiceBusinessTest.class);
    }

    @Test
    public void testBuscaUltimosSegurosIndividual() {
        try {
            Persona persona = new Persona();
            persona.setIdPersona(284377981L);
            SegurosIvro seguro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaUltimosSegurosIndividual(persona);
            System.out.println(BeanUtils.describe(seguro));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBuscaSegurosPorConcluir(){
        List<Long> list = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosPorConcluir();
        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testBuscaSegurosBajaMensualCvro(){
        List<Long> list = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosBajaMensualCvro();
        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testBuscaSegurosBajaPorMora(){
        List<Long> list = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosBajaPorMora();
        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testBuscaSegurosCvroLCAutomatica(){
        List<Long> list = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosCvroLCAutomatica();
        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testBuscaSegurosFamiliares() throws NamingException {
        Persona persona = new Persona();
        persona.setIdPersona(101727L);
        SegurosIvro segurosIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosFamiliares(persona);
        System.out.println("segurosIvro = " + segurosIvro);
    }

    @Test
    public void testBuscaSegurosCvroLineaCapturaAutomatica() throws Exception {
        SegurosIvro segurosIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosCvroLineaCapturaAutomatica();
        for (SeguroIvro seguroIvro : segurosIvro.getSeguroIvro()) {
            System.out.println(JaxbUtilT.marshaller(seguroIvro));
        }
    }

    @Test
    public void testBuscaSeguro() throws Exception {

        Long segIds[] = new Long[]{//101629L //};
                //,101628L,101613L,101611L,101608L,101592L,101588L,101587L,101572L,101567L,101553L,101552L,101550L,101534L,101531L,101529L,101528L,101507L,101490L,101488L,101477L,101475L,101474L,101473L,101472L,101470L,101468L,101448L,101431L,101412L,101393L,101392L,101370L,101367L,101358L,101354L,101349L,101327L,101287L,101267L,101252L,101251L,101247L,101237L,101236L,101229L,101228L,101207L,101196L,101194L,101192L,101191L,101190L,101187L,101167L,101148L,101147L,101138L,101137L,101135L,101127L,101110L,101109L,101108L,101093L,101089L,101073L,101072L,101071L,101069L,101068L,101034L,101033L,100988L,100947L,100909L,100867L,100749L,100747L,100727L,100648L,100608L,100591L,100568L,100567L,100552L,100551L,100509L,100507L,100451L,100450L,100449L,100428L,100409L,100407L,100388L,100347L,100329L,100269L,100228L,100210L,100192L,100167L,100048L,100016L,99947L,99887L,99850L,99849L,99827L,99820L,99815L,99792L,99776L,99770L,99750L,99748L,99709L,99687L,99668L,99648L,99553L,99551L,99527L,99455L,99431L,99427L,99374L,99370L,99368L,99367L,99351L,99348L,99307L,99301L,99269L,99267L,99230L,99201L,99153L,99150L,

                //99069L,//Error al convertir tramite

                //98973L,98888L,98795L,98727L,98663L,98657L,98652L,98629L,98587L,98571L,98489L,98472L,98447L,98428L,98427L,98407L,98373L,98371L,98350L,98311L,98309L,98267L,98249L,98195L,98192L,98128L,98107L,98054L,98030L,98009L,97954L,97948L,97810L,97773L,97771L,97574L,97555L,97553L,97529L,97468L,97450L,97373L,97371L,97370L,97352L,97351L,97348L,97307L,97288L,97151L,97150L,97133L,97068L,97027L,97007L,96968L,96838L,96835L,96829L,96790L,96751L,96748L,96747L,96729L,96714L,96713L,96712L,96709L,96707L,96676L,96648L,96608L,96587L,96571L,96570L,96548L,96509L,96508L,96469L,96411L,96389L,96351L,96348L,96314L,96313L,96308L,96287L,96152L,96150L,96149L,96131L,96129L,96113L,96112L,96089L,96007L,96003L,96001L,95999L,95993L,95983L,95982L,95978L,95958L,95954L,95938L,95935L,95828L,95731L,95729L,95728L,95687L,95649L,95648L,95567L,95550L,95531L,95427L,95312L,95310L,95268L,95173L,95117L,94972L,94938L,94892L,94891L,94889L,94868L,94855L,94851L,94770L,94751L,94708L,94648L,94550L,94549L,94470L,94368L,94190L,94113L,94091L,94088L,94054L,94053L,93989L,93969L,93967L,93950L,93834L,93832L,93788L,93727L,93669L,93587L,93567L,93549L,93547L,93348L,93327L,93288L,93287L,93167L,93129L,93128L,93098L,93095L,92967L,92864L,92849L,92828L,92827L,92788L,92787L,92768L,92727L,92599L,92530L,92528L,92392L,92391L,92370L,92333L,92307L,92268L,92211L,92210L,92209L,92149L,92133L,92128L,92009L,91927L,91908L,91849L,91847L,91827L,91748L,91727L,91570L,91568L,91533L,91468L,91448L,91427L,91328L,91267L,91247L,91230L,91187L,91109L,90967L,90908L,90787L,90770L,90750L,90749L,90748L,90727L,90628L,90613L,90494L,90428L,90367L,90290L,90207L,90127L,90073L,89787L,89771L,89770L,89730L,89711L,89630L,89588L,89567L,89548L,89448L,89387L,89252L,89230L,89207L,89149L

                //55895//Error al convertir tramite

                //57415L,56295L,54735L,55335L,56116L,57735L,57460L,57178L,56675L,55416L,55876L,55195L,56135L,57075L,57175L,56597L,57237L,55035L,58335L,54736L,57096L,57196L,57117L,56615L,56616L,57409L,57155L,61721L,61280L,61420L,58475L,60840L,59683L,61441L,59638L,61318L,59296L,61438L,59298L,60561L,60023L,59938L,58376L,60606L,60609L,61942L,58695L,60238L,60160L,60260L,60745L,60341L,60981L,61286L,60478L,60000L,60719L,60939L,61878L,62018L,59898L,58277L,61679L,58658L,61383L,61758L,60022L,59135L,58835L,59136L,62097L,58315L,60664L,60259L,59235L,59377L,61699L,58557L,61219L,61499L,59580L,59958L,59579L,61680L,61378L,61385L,62160L,60615L,60458L,62159L,58655L,61298L,62339L,61720L,61719L,59276L,60158L,60659L,58357L,58696L,59519L,59699L,60239L,61284L,61079L,59778L,61442L,59295L,58395L,59738L,60605L,58916L,59095L,60061L,61539L,60067L,60612L,59279L,59440L,62083L,62084L,60100L,60242L,61846L,61285L,61458L,61078L,58955L,60740L,62300L,59681L,61598L,60639L,58937L,61140L,60821L,60613L,59157L,61538L,60658L,60399L,60660L,62085L,61301L,59918L,59979L,60498L,61358L,58015L,58155L,59376L,58576L,61460L,61498L,60720L,59899L,59421L,61058L,58756L,60420L,61858L,61021L,60303L,58515L,61941L,60460L,62338L,60098L,61302L,58876L,59439L,60378L,61778L,60978L,60640L,60379L,60746L,61239L,61798L,59999L,59961L,59718L,59216L,59420L,60820L,61398L,59076L,62202L,61022L,58657L,60599L,59980L,62090L,60039L,60982L,61281L,60900L,61379L,62279L,60610L,58095L,59097L

                //107235L

                101727L
        };
        SeguroIvro seguroIvro1 = new SeguroIvro();

        for (Long segId : segIds) {
            seguroIvro1.setCveIdSeguroIvro(segId);
            seguroIvro1 = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSeguro(seguroIvro1);
            System.out.println("seguroIvro1 = " + seguroIvro1.getCveIdSeguroIvro());
        }

    }

    @Test
    public void testBuscaSegurosActivosMod40() throws Exception {

        SegurosIvro segurosIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSegurosActivosMod40();
        System.out.println("segurosIvro = " + segurosIvro);

    }
}
