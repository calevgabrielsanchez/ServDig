package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model;

import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.NONE_PREVIOUS;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.INSUFICIENCIA_RENAL;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.RETINOPATIA;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.NEUROPATIA;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.INSUFICIENCIA_CIRCULAR_PERIFERICA;
import mx.gob.imss.digital.modelo.cuestionario.Opcion;
import mx.gob.imss.digital.modelo.cuestionario.Respuesta;
import mx.gob.imss.digital.modelo.cuestionario.RespuestasCuestionario;
import mx.gob.imss.digital.modelo.seguros.CuestionarioSeguroReporte;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;

/**
 * The Class CuestionarioVO.
 * 
 * @author Dj Leo 28/11/2014 The Class CuestionarioVO.
 */
public class CuestionarioVO {

    /** The titular. */
    private String titular;

    /** The esposa. */
    private String esposa;

    /** The hijo. */
    private String hijo;

    /** The benef padre. */
    private String benefPadre;

    /** The benef madre. */
    private String benefMadre;

    /** The ap paterno. */
    private String apPaterno;

    /** The ap materno. */
    private String apMaterno;

    /** The nombres. */
    private String nombres;

    /** The nss. */
    private String nss;

    /** The agreg medico. */
    private String agregMedico;

    /** The calle io manzana. */
    private String calleIOManzana;

    /** The numero. */
    private String numero;

    /** The colonia. */
    private String colonia;

    /** The curp. */
    private String curp;

    /** The poblacion. */
    private String poblacion;

    /** The estado. */
    private String estado;

    /** The cod postal. */
    private String codPostal;

    /** The telefono. */
    private String telefono;

    /** The lug nacimiento. */
    private String lugNacimiento;

    /** The fecha nacimiento. */
    private String fechaNacimiento;

    /** The edad. */
    private String edad;

    /** The sex fem. */
    private String sexFem;

    /** The sex mas. */
    private String sexMas;

    /** The est civil. */
    private String estCivil;

    /** The cve deleg. */
    private String cveDeleg;

    /** The ocupacion. */
    private String ocupacion;

    /** The primaria. */
    private String primaria;

    /** The secundaria. */
    private String secundaria;

    /** The preparatoria. */
    private String preparatoria;

    /** The escuela tec. */
    private String escuelaTec;

    /** The profesional. */
    private String profesional;

    /** The unidad med fam. */
    private String unidadMedFam;

    /** The mod34. */
    private String mod34;

    /** The mod35. */
    private String mod35;

    /** The mod36. */
    private String mod36;

    /** The mod38. */
    private String mod38;

    /** The mod42. */
    private String mod42;

    /** The mod43. */
    private String mod43;

    /** The mod44. */
    private String mod44;

    // Inicio preguntas primer cuestionario historia de habitos persoles
    /** The activ fisica no. */
    private String activFisicaNo;

    /** The activ fisica si. */
    private String activFisicaSi;

    /** The briago si. */
    private String briagoSi;

    /** The briago no. */
    private String briagoNo;

    /** The num cop. */
    private String numCop;

    /** The tiempo briagues. */
    private String tiempoBriagues;

    /** The fuma cigarillos si. */
    private String fumaCigarillosSi;

    /** The fuma cigarillos no. */
    private String fumaCigarillosNo;

    /** The num cigarrillos. */
    private String numCigarrillos;

    /** The tiempo fumador. */
    private String tiempoFumador;

    /** The automedica si. */
    private String automedicaSi;

    /** The automedica no. */
    private String automedicaNo;

    /** The estatura. */
    private String estatura;

    /** The peso. */
    private String peso;

    // Inicio de preguntas Historia de enfermedades
    /** The pregunta una. */
    private String preguntaUna;

    /** The pregunta dos. */
    private String preguntaDos;

    /** The pregunta tres. */
    private String preguntaTres;

    /** The pregunta cuarto a. */
    private String preguntaCuartoA;

    /** The pregunta cuarto b. */
    private String preguntaCuartoB;

    /** The pregunta cuarto c. */
    private String preguntaCuartoC;

    /** The pregunta cuarto d. */
    private String preguntaCuartoD;

    /** The pregunta cuarto no ne. */
    private String preguntaCuartoNoNe;

    /** The pregunta cinco. */
    private String preguntaCinco;

    /** The pregunta seis. */
    private String preguntaSeis;

    /** The pregunta siete. */
    private String preguntaSiete;

    /** The pregunta ocho. */
    private String preguntaOcho;

    /** The pregunta nueve. */
    private String preguntaNueve;

    /** The pregunta diez. */
    private String preguntaDiez;

    /** The pregunta once. */
    private String preguntaOnce;

    /** The pregunta doce. */
    private String preguntaDoce;

    /** The pregunta trece. */
    private String preguntaTrece;

    /** The pregunta catorce. */
    private String preguntaCatorce;

    /** The pregunta quince. */
    private String preguntaQuince;

    /** The pregunta dieciseis. */
    private String preguntaDieciseis;

    /** The pregunta diecisiete. */
    private String preguntaDiecisiete;

    /** The pregunta dieciocho. */
    private String preguntaDieciocho;

    /** The pregunta diecinueve. */
    private String preguntaDiecinueve;

    // Seccion cadena original
    /** The cad original. */
    private String cadOriginal;

    /** The signature. */
    private String signature;

    /** The secuencia. */
    private String secuencia;

    /** The num serie. */
    private String numSerie;

    final static String RESP_SI = "SI";

    final static String RESP_NO = "NO";

    /**
     * Instantiates a new cuestionario vo.
     */
    public CuestionarioVO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the titular.
     * 
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Sets the titular.
     * 
     * @param titular
     *            the new titular
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * Gets the esposa.
     * 
     * @return the esposa
     */
    public String getEsposa() {
        return esposa;
    }

    /**
     * Sets the esposa.
     * 
     * @param esposa
     *            the new esposa
     */
    public void setEsposa(String esposa) {
        this.esposa = esposa;
    }

    /**
     * Gets the hijo.
     * 
     * @return the hijo
     */
    public String getHijo() {
        return hijo;
    }

    /**
     * Sets the hijo.
     * 
     * @param hijo
     *            the new hijo
     */
    public void setHijo(String hijo) {
        this.hijo = hijo;
    }

    /**
     * Gets the benef padre.
     * 
     * @return the benef padre
     */
    public String getBenefPadre() {
        return benefPadre;
    }

    /**
     * Sets the benef padre.
     * 
     * @param benefPadre
     *            the new benef padre
     */
    public void setBenefPadre(String benefPadre) {
        this.benefPadre = benefPadre;
    }

    /**
     * Gets the benef madre.
     * 
     * @return the benef madre
     */
    public String getBenefMadre() {
        return benefMadre;
    }

    /**
     * Sets the benef madre.
     * 
     * @param benefMadre
     *            the new benef madre
     */
    public void setBenefMadre(String benefMadre) {
        this.benefMadre = benefMadre;
    }

    /**
     * Gets the ap paterno.
     * 
     * @return the ap paterno
     */
    public String getApPaterno() {
        return apPaterno;
    }

    /**
     * Sets the ap paterno.
     * 
     * @param apPaterno
     *            the new ap paterno
     */
    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    /**
     * Gets the ap materno.
     * 
     * @return the ap materno
     */
    public String getApMaterno() {
        return apMaterno;
    }

    /**
     * Sets the ap materno.
     * 
     * @param apMaterno
     *            the new ap materno
     */
    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    /**
     * Gets the nombres.
     * 
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the nombres.
     * 
     * @param nombres
     *            the new nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Gets the nss.
     * 
     * @return the nss
     */
    public String getNss() {
        return nss;
    }

    /**
     * Sets the nss.
     * 
     * @param nss
     *            the new nss
     */
    public void setNss(String nss) {
        this.nss = nss;
    }

    /**
     * Gets the agreg medico.
     * 
     * @return the agreg medico
     */
    public String getAgregMedico() {
        return agregMedico;
    }

    /**
     * Sets the agreg medico.
     * 
     * @param agregMedico
     *            the new agreg medico
     */
    public void setAgregMedico(String agregMedico) {
        this.agregMedico = agregMedico;
    }

    /**
     * Gets the calle io manzana.
     * 
     * @return the calle io manzana
     */
    public String getCalleIOManzana() {
        return calleIOManzana;
    }

    /**
     * Sets the calle io manzana.
     * 
     * @param calleIOManzana
     *            the new calle io manzana
     */
    public void setCalleIOManzana(String calleIOManzana) {
        this.calleIOManzana = calleIOManzana;
    }

    /**
     * Gets the numero.
     * 
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the numero.
     * 
     * @param numero
     *            the new numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Gets the colonia.
     * 
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * Sets the colonia.
     * 
     * @param colonia
     *            the new colonia
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * Gets the curp.
     * 
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * Sets the curp.
     * 
     * @param curp
     *            the new curp
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * Gets the poblacion.
     * 
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Sets the poblacion.
     * 
     * @param poblacion
     *            the new poblacion
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Gets the estado.
     * 
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     * 
     * @param estado
     *            the new estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the cod postal.
     * 
     * @return the cod postal
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * Sets the cod postal.
     * 
     * @param codPostal
     *            the new cod postal
     */
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    /**
     * Gets the telefono.
     * 
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the telefono.
     * 
     * @param telefono
     *            the new telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Gets the lug nacimiento.
     * 
     * @return the lug nacimiento
     */
    public String getLugNacimiento() {
        return lugNacimiento;
    }

    /**
     * Sets the lug nacimiento.
     * 
     * @param lugNacimiento
     *            the new lug nacimiento
     */
    public void setLugNacimiento(String lugNacimiento) {
        this.lugNacimiento = lugNacimiento;
    }

    /**
     * Gets the fecha nacimiento.
     * 
     * @return the fecha nacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the fecha nacimiento.
     * 
     * @param fechaNacimiento
     *            the new fecha nacimiento
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Gets the edad.
     * 
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * Sets the edad.
     * 
     * @param edad
     *            the new edad
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * Gets the sex fem.
     * 
     * @return the sex fem
     */
    public String getSexFem() {
        return sexFem;
    }

    /**
     * Sets the sex fem.
     * 
     * @param sexFem
     *            the new sex fem
     */
    public void setSexFem(String sexFem) {
        this.sexFem = sexFem;
    }

    /**
     * Gets the sex mas.
     * 
     * @return the sex mas
     */
    public String getSexMas() {
        return sexMas;
    }

    /**
     * Sets the sex mas.
     * 
     * @param sexMas
     *            the new sex mas
     */
    public void setSexMas(String sexMas) {
        this.sexMas = sexMas;
    }

    /**
     * Gets the est civil.
     * 
     * @return the est civil
     */
    public String getEstCivil() {
        return estCivil;
    }

    /**
     * Sets the est civil.
     * 
     * @param estCivil
     *            the new est civil
     */
    public void setEstCivil(String estCivil) {
        this.estCivil = estCivil;
    }

    /**
     * Gets the cve deleg.
     * 
     * @return the cve deleg
     */
    public String getCveDeleg() {
        return cveDeleg;
    }

    /**
     * Sets the cve deleg.
     * 
     * @param cveDeleg
     *            the new cve deleg
     */
    public void setCveDeleg(String cveDeleg) {
        this.cveDeleg = cveDeleg;
    }

    /**
     * Gets the ocupacion.
     * 
     * @return the ocupacion
     */
    public String getOcupacion() {
        return ocupacion;
    }

    /**
     * Sets the ocupacion.
     * 
     * @param ocupacion
     *            the new ocupacion
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    /**
     * Gets the primaria.
     * 
     * @return the primaria
     */
    public String getPrimaria() {
        return primaria;
    }

    /**
     * Sets the primaria.
     * 
     * @param primaria
     *            the new primaria
     */
    public void setPrimaria(String primaria) {
        this.primaria = primaria;
    }

    /**
     * Gets the secundaria.
     * 
     * @return the secundaria
     */
    public String getSecundaria() {
        return secundaria;
    }

    /**
     * Sets the secundaria.
     * 
     * @param secundaria
     *            the new secundaria
     */
    public void setSecundaria(String secundaria) {
        this.secundaria = secundaria;
    }

    /**
     * Gets the preparatoria.
     * 
     * @return the preparatoria
     */
    public String getPreparatoria() {
        return preparatoria;
    }

    /**
     * Sets the preparatoria.
     * 
     * @param preparatoria
     *            the new preparatoria
     */
    public void setPreparatoria(String preparatoria) {
        this.preparatoria = preparatoria;
    }

    /**
     * Gets the escuela tec.
     * 
     * @return the escuela tec
     */
    public String getEscuelaTec() {
        return escuelaTec;
    }

    /**
     * Sets the escuela tec.
     * 
     * @param escuelaTec
     *            the new escuela tec
     */
    public void setEscuelaTec(String escuelaTec) {
        this.escuelaTec = escuelaTec;
    }

    /**
     * Gets the profesional.
     * 
     * @return the profesional
     */
    public String getProfesional() {
        return profesional;
    }

    /**
     * Sets the profesional.
     * 
     * @param profesional
     *            the new profesional
     */
    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    /**
     * Gets the unidad med fam.
     * 
     * @return the unidad med fam
     */
    public String getUnidadMedFam() {
        return unidadMedFam;
    }

    /**
     * Sets the unidad med fam.
     * 
     * @param unidadMedFam
     *            the new unidad med fam
     */
    public void setUnidadMedFam(String unidadMedFam) {
        this.unidadMedFam = unidadMedFam;
    }

    /**
     * Gets the mod34.
     * 
     * @return the mod34
     */
    public String getMod34() {
        return mod34;
    }

    /**
     * Sets the mod34.
     * 
     * @param mod34
     *            the new mod34
     */
    public void setMod34(String mod34) {
        this.mod34 = mod34;
    }

    /**
     * Gets the mod35.
     * 
     * @return the mod35
     */
    public String getMod35() {
        return mod35;
    }

    /**
     * Sets the mod35.
     * 
     * @param mod35
     *            the new mod35
     */
    public void setMod35(String mod35) {
        this.mod35 = mod35;
    }

    /**
     * Gets the mod36.
     * 
     * @return the mod36
     */
    public String getMod36() {
        return mod36;
    }

    /**
     * Sets the mod36.
     * 
     * @param mod36
     *            the new mod36
     */
    public void setMod36(String mod36) {
        this.mod36 = mod36;
    }

    /**
     * Gets the mod38.
     * 
     * @return the mod38
     */
    public String getMod38() {
        return mod38;
    }

    /**
     * Sets the mod38.
     * 
     * @param mod38
     *            the new mod38
     */
    public void setMod38(String mod38) {
        this.mod38 = mod38;
    }

    /**
     * Gets the mod42.
     * 
     * @return the mod42
     */
    public String getMod42() {
        return mod42;
    }

    /**
     * Sets the mod42.
     * 
     * @param mod42
     *            the new mod42
     */
    public void setMod42(String mod42) {
        this.mod42 = mod42;
    }

    /**
     * Gets the mod43.
     * 
     * @return the mod43
     */
    public String getMod43() {
        return mod43;
    }

    /**
     * Sets the mod43.
     * 
     * @param mod43
     *            the new mod43
     */
    public void setMod43(String mod43) {
        this.mod43 = mod43;
    }

    /**
     * Gets the mod44.
     * 
     * @return the mod44
     */
    public String getMod44() {
        return mod44;
    }

    /**
     * Sets the mod44.
     * 
     * @param mod44
     *            the new mod44
     */
    public void setMod44(String mod44) {
        this.mod44 = mod44;
    }

    /**
     * Gets the activ fisica no.
     * 
     * @return the activ fisica no
     */
    public String getActivFisicaNo() {
        return activFisicaNo;
    }

    /**
     * Sets the activ fisica no.
     * 
     * @param activFisicaNo
     *            the new activ fisica no
     */
    public void setActivFisicaNo(String activFisicaNo) {
        this.activFisicaNo = activFisicaNo;
    }

    /**
     * Gets the activ fisica si.
     * 
     * @return the activ fisica si
     */
    public String getActivFisicaSi() {
        return activFisicaSi;
    }

    /**
     * Sets the activ fisica si.
     * 
     * @param activFisicaSi
     *            the new activ fisica si
     */
    public void setActivFisicaSi(String activFisicaSi) {
        this.activFisicaSi = activFisicaSi;
    }

    /**
     * Gets the briago si.
     * 
     * @return the briago si
     */
    public String getBriagoSi() {
        return briagoSi;
    }

    /**
     * Sets the briago si.
     * 
     * @param briagoSi
     *            the new briago si
     */
    public void setBriagoSi(String briagoSi) {
        this.briagoSi = briagoSi;
    }

    /**
     * Gets the briago no.
     * 
     * @return the briago no
     */
    public String getBriagoNo() {
        return briagoNo;
    }

    /**
     * Sets the briago no.
     * 
     * @param briagoNo
     *            the new briago no
     */
    public void setBriagoNo(String briagoNo) {
        this.briagoNo = briagoNo;
    }

    /**
     * Gets the num cop.
     * 
     * @return the num cop
     */
    public String getNumCop() {
        return numCop;
    }

    /**
     * Sets the num cop.
     * 
     * @param numCop
     *            the new num cop
     */
    public void setNumCop(String numCop) {
        this.numCop = numCop;
    }

    /**
     * Gets the tiempo briagues.
     * 
     * @return the tiempo briagues
     */
    public String getTiempoBriagues() {
        return tiempoBriagues;
    }

    /**
     * Sets the tiempo briagues.
     * 
     * @param tiempoBriagues
     *            the new tiempo briagues
     */
    public void setTiempoBriagues(String tiempoBriagues) {
        this.tiempoBriagues = tiempoBriagues;
    }

    /**
     * Gets the fuma cigarillos si.
     * 
     * @return the fuma cigarillos si
     */
    public String getFumaCigarillosSi() {
        return fumaCigarillosSi;
    }

    /**
     * Sets the fuma cigarillos si.
     * 
     * @param fumaCigarillosSi
     *            the new fuma cigarillos si
     */
    public void setFumaCigarillosSi(String fumaCigarillosSi) {
        this.fumaCigarillosSi = fumaCigarillosSi;
    }

    /**
     * Gets the fuma cigarillos no.
     * 
     * @return the fuma cigarillos no
     */
    public String getFumaCigarillosNo() {
        return fumaCigarillosNo;
    }

    /**
     * Sets the fuma cigarillos no.
     * 
     * @param fumaCigarillosNo
     *            the new fuma cigarillos no
     */
    public void setFumaCigarillosNo(String fumaCigarillosNo) {
        this.fumaCigarillosNo = fumaCigarillosNo;
    }

    /**
     * Gets the num cigarrillos.
     * 
     * @return the num cigarrillos
     */
    public String getNumCigarrillos() {
        return numCigarrillos;
    }

    /**
     * Sets the num cigarrillos.
     * 
     * @param numCigarrillos
     *            the new num cigarrillos
     */
    public void setNumCigarrillos(String numCigarrillos) {
        this.numCigarrillos = numCigarrillos;
    }

    /**
     * Gets the tiempo fumador.
     * 
     * @return the tiempo fumador
     */
    public String getTiempoFumador() {
        return tiempoFumador;
    }

    /**
     * Sets the tiempo fumador.
     * 
     * @param tiempoFumador
     *            the new tiempo fumador
     */
    public void setTiempoFumador(String tiempoFumador) {
        this.tiempoFumador = tiempoFumador;
    }

    /**
     * Gets the automedica si.
     * 
     * @return the automedica si
     */
    public String getAutomedicaSi() {
        return automedicaSi;
    }

    /**
     * Sets the automedica si.
     * 
     * @param automedicaSi
     *            the new automedica si
     */
    public void setAutomedicaSi(String automedicaSi) {
        this.automedicaSi = automedicaSi;
    }

    /**
     * Gets the automedica no.
     * 
     * @return the automedica no
     */
    public String getAutomedicaNo() {
        return automedicaNo;
    }

    /**
     * Sets the automedica no.
     * 
     * @param automedicaNo
     *            the new automedica no
     */
    public void setAutomedicaNo(String automedicaNo) {
        this.automedicaNo = automedicaNo;
    }

    /**
     * Gets the estatura.
     * 
     * @return the estatura
     */
    public String getEstatura() {
        return estatura;
    }

    /**
     * Sets the estatura.
     * 
     * @param estatura
     *            the new estatura
     */
    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    /**
     * Gets the peso.
     * 
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * Sets the peso.
     * 
     * @param peso
     *            the new peso
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * Gets the pregunta una.
     * 
     * @return the pregunta una
     */
    public String getPreguntaUna() {
        return preguntaUna;
    }

    /**
     * Sets the pregunta una.
     * 
     * @param preguntaUna
     *            the new pregunta una
     */
    public void setPreguntaUna(String preguntaUna) {
        this.preguntaUna = preguntaUna;
    }

    /**
     * Gets the pregunta dos.
     * 
     * @return the pregunta dos
     */
    public String getPreguntaDos() {
        return preguntaDos;
    }

    /**
     * Sets the pregunta dos.
     * 
     * @param preguntaDos
     *            the new pregunta dos
     */
    public void setPreguntaDos(String preguntaDos) {
        this.preguntaDos = preguntaDos;
    }

    /**
     * Gets the pregunta tres.
     * 
     * @return the pregunta tres
     */
    public String getPreguntaTres() {
        return preguntaTres;
    }

    /**
     * Sets the pregunta tres.
     * 
     * @param preguntaTres
     *            the new pregunta tres
     */
    public void setPreguntaTres(String preguntaTres) {
        this.preguntaTres = preguntaTres;
    }

    /**
     * Gets the pregunta cuarto a.
     * 
     * @return the pregunta cuarto a
     */
    public String getPreguntaCuartoA() {
        return preguntaCuartoA;
    }

    /**
     * Sets the pregunta cuarto a.
     * 
     * @param preguntaCuartoA
     *            the new pregunta cuarto a
     */
    public void setPreguntaCuartoA(String preguntaCuartoA) {
        this.preguntaCuartoA = preguntaCuartoA;
    }

    /**
     * Gets the pregunta cuarto b.
     * 
     * @return the pregunta cuarto b
     */
    public String getPreguntaCuartoB() {
        return preguntaCuartoB;
    }

    /**
     * Sets the pregunta cuarto b.
     * 
     * @param preguntaCuartoB
     *            the new pregunta cuarto b
     */
    public void setPreguntaCuartoB(String preguntaCuartoB) {
        this.preguntaCuartoB = preguntaCuartoB;
    }

    /**
     * Gets the pregunta cuarto c.
     * 
     * @return the pregunta cuarto c
     */
    public String getPreguntaCuartoC() {
        return preguntaCuartoC;
    }

    /**
     * Sets the pregunta cuarto c.
     * 
     * @param preguntaCuartoC
     *            the new pregunta cuarto c
     */
    public void setPreguntaCuartoC(String preguntaCuartoC) {
        this.preguntaCuartoC = preguntaCuartoC;
    }

    /**
     * Gets the pregunta cuarto d.
     * 
     * @return the pregunta cuarto d
     */
    public String getPreguntaCuartoD() {
        return preguntaCuartoD;
    }

    /**
     * Sets the pregunta cuarto d.
     * 
     * @param preguntaCuartoD
     *            the new pregunta cuarto d
     */
    public void setPreguntaCuartoD(String preguntaCuartoD) {
        this.preguntaCuartoD = preguntaCuartoD;
    }

    /**
     * Gets the pregunta cuarto no ne.
     * 
     * @return the pregunta cuarto no ne
     */
    public String getPreguntaCuartoNoNe() {
        return preguntaCuartoNoNe;
    }

    /**
     * Sets the pregunta cuarto no ne.
     * 
     * @param preguntaCuartoNoNe
     *            the new pregunta cuarto no ne
     */
    public void setPreguntaCuartoNoNe(String preguntaCuartoNoNe) {
        this.preguntaCuartoNoNe = preguntaCuartoNoNe;
    }

    /**
     * Gets the pregunta cinco.
     * 
     * @return the pregunta cinco
     */
    public String getPreguntaCinco() {
        return preguntaCinco;
    }

    /**
     * Sets the pregunta cinco.
     * 
     * @param preguntaCinco
     *            the new pregunta cinco
     */
    public void setPreguntaCinco(String preguntaCinco) {
        this.preguntaCinco = preguntaCinco;
    }

    /**
     * Gets the pregunta seis.
     * 
     * @return the pregunta seis
     */
    public String getPreguntaSeis() {
        return preguntaSeis;
    }

    /**
     * Sets the pregunta seis.
     * 
     * @param preguntaSeis
     *            the new pregunta seis
     */
    public void setPreguntaSeis(String preguntaSeis) {
        this.preguntaSeis = preguntaSeis;
    }

    /**
     * Gets the pregunta siete.
     * 
     * @return the pregunta siete
     */
    public String getPreguntaSiete() {
        return preguntaSiete;
    }

    /**
     * Sets the pregunta siete.
     * 
     * @param preguntaSiete
     *            the new pregunta siete
     */
    public void setPreguntaSiete(String preguntaSiete) {
        this.preguntaSiete = preguntaSiete;
    }

    /**
     * Gets the pregunta ocho.
     * 
     * @return the pregunta ocho
     */
    public String getPreguntaOcho() {
        return preguntaOcho;
    }

    /**
     * Sets the pregunta ocho.
     * 
     * @param preguntaOcho
     *            the new pregunta ocho
     */
    public void setPreguntaOcho(String preguntaOcho) {
        this.preguntaOcho = preguntaOcho;
    }

    /**
     * Gets the pregunta nueve.
     * 
     * @return the pregunta nueve
     */
    public String getPreguntaNueve() {
        return preguntaNueve;
    }

    /**
     * Sets the pregunta nueve.
     * 
     * @param preguntaNueve
     *            the new pregunta nueve
     */
    public void setPreguntaNueve(String preguntaNueve) {
        this.preguntaNueve = preguntaNueve;
    }

    /**
     * Gets the pregunta diez.
     * 
     * @return the pregunta diez
     */
    public String getPreguntaDiez() {
        return preguntaDiez;
    }

    /**
     * Sets the pregunta diez.
     * 
     * @param preguntaDiez
     *            the new pregunta diez
     */
    public void setPreguntaDiez(String preguntaDiez) {
        this.preguntaDiez = preguntaDiez;
    }

    /**
     * Gets the pregunta once.
     * 
     * @return the pregunta once
     */
    public String getPreguntaOnce() {
        return preguntaOnce;
    }

    /**
     * Sets the pregunta once.
     * 
     * @param preguntaOnce
     *            the new pregunta once
     */
    public void setPreguntaOnce(String preguntaOnce) {
        this.preguntaOnce = preguntaOnce;
    }

    /**
     * Gets the pregunta doce.
     * 
     * @return the pregunta doce
     */
    public String getPreguntaDoce() {
        return preguntaDoce;
    }

    /**
     * Sets the pregunta doce.
     * 
     * @param preguntaDoce
     *            the new pregunta doce
     */
    public void setPreguntaDoce(String preguntaDoce) {
        this.preguntaDoce = preguntaDoce;
    }

    /**
     * Gets the pregunta trece.
     * 
     * @return the pregunta trece
     */
    public String getPreguntaTrece() {
        return preguntaTrece;
    }

    /**
     * Sets the pregunta trece.
     * 
     * @param preguntaTrece
     *            the new pregunta trece
     */
    public void setPreguntaTrece(String preguntaTrece) {
        this.preguntaTrece = preguntaTrece;
    }

    /**
     * Gets the pregunta catorce.
     * 
     * @return the pregunta catorce
     */
    public String getPreguntaCatorce() {
        return preguntaCatorce;
    }

    /**
     * Sets the pregunta catorce.
     * 
     * @param preguntaCatorce
     *            the new pregunta catorce
     */
    public void setPreguntaCatorce(String preguntaCatorce) {
        this.preguntaCatorce = preguntaCatorce;
    }

    /**
     * Gets the pregunta quince.
     * 
     * @return the pregunta quince
     */
    public String getPreguntaQuince() {
        return preguntaQuince;
    }

    /**
     * Sets the pregunta quince.
     * 
     * @param preguntaQuince
     *            the new pregunta quince
     */
    public void setPreguntaQuince(String preguntaQuince) {
        this.preguntaQuince = preguntaQuince;
    }

    /**
     * Gets the pregunta dieciseis.
     * 
     * @return the pregunta dieciseis
     */
    public String getPreguntaDieciseis() {
        return preguntaDieciseis;
    }

    /**
     * Sets the pregunta dieciseis.
     * 
     * @param preguntaDieciseis
     *            the new pregunta dieciseis
     */
    public void setPreguntaDieciseis(String preguntaDieciseis) {
        this.preguntaDieciseis = preguntaDieciseis;
    }

    /**
     * Gets the pregunta diecisiete.
     * 
     * @return the pregunta diecisiete
     */
    public String getPreguntaDiecisiete() {
        return preguntaDiecisiete;
    }

    /**
     * Sets the pregunta diecisiete.
     * 
     * @param preguntaDiecisiete
     *            the new pregunta diecisiete
     */
    public void setPreguntaDiecisiete(String preguntaDiecisiete) {
        this.preguntaDiecisiete = preguntaDiecisiete;
    }

    /**
     * Gets the pregunta dieciocho.
     * 
     * @return the pregunta dieciocho
     */
    public String getPreguntaDieciocho() {
        return preguntaDieciocho;
    }

    /**
     * Sets the pregunta dieciocho.
     * 
     * @param preguntaDieciocho
     *            the new pregunta dieciocho
     */
    public void setPreguntaDieciocho(String preguntaDieciocho) {
        this.preguntaDieciocho = preguntaDieciocho;
    }

    /**
     * Gets the pregunta diecinueve.
     * 
     * @return the pregunta diecinueve
     */
    public String getPreguntaDiecinueve() {
        return preguntaDiecinueve;
    }

    /**
     * Sets the pregunta diecinueve.
     * 
     * @param preguntaDiecinueve
     *            the new pregunta diecinueve
     */
    public void setPreguntaDiecinueve(String preguntaDiecinueve) {
        this.preguntaDiecinueve = preguntaDiecinueve;
    }

    /**
     * Gets the cad original.
     * 
     * @return the cad original
     */
    public String getCadOriginal() {
        return cadOriginal;
    }

    /**
     * Sets the cad original.
     * 
     * @param cadOriginal
     *            the new cad original
     */
    public void setCadOriginal(String cadOriginal) {
        this.cadOriginal = cadOriginal;
    }

    /**
     * Gets the signature.
     * 
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature.
     * 
     * @param signature
     *            the new signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Gets the secuencia.
     * 
     * @return the secuencia
     */
    public String getSecuencia() {
        return secuencia;
    }

    /**
     * Sets the secuencia.
     * 
     * @param secuencia
     *            the new secuencia
     */
    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    /**
     * Gets the num serie.
     * 
     * @return the num serie
     */
    public String getNumSerie() {
        return numSerie;
    }

    /**
     * Sets the num serie.
     * 
     * @param numSerie
     *            the new num serie
     */
    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public CuestionarioVO fill(CuestionarioSeguroReporte cuestIVROReceived) {
        CuestionarioVO cuestVO = new CuestionarioVO();
        // Seteamos datos basicos del titular
        cuestVO.setTitular("X");
        cuestVO.setBenefMadre("");
        cuestVO.setBenefPadre("");
        cuestVO.setEsposa("");
        cuestVO.setHijo("");
        cuestVO.setApPaterno(StringUtils.isNotEmpty(cuestIVROReceived.getApPaterno()) ? cuestIVROReceived
                .getApPaterno() : "");
        cuestVO.setApMaterno(StringUtils.isNotEmpty(cuestIVROReceived.getApMaterno()) ? cuestIVROReceived
                .getApMaterno() : "");
        cuestVO.setNombres(StringUtils.isNotEmpty(cuestIVROReceived.getNombres()) ? cuestIVROReceived
                .getNombres() : "");
        cuestVO.setCalleIOManzana(StringUtils.isNotEmpty(cuestIVROReceived.getCalleIOManzana()) ? cuestIVROReceived
                .getCalleIOManzana() : "");
        cuestVO.setNss(StringUtils.isNotEmpty(cuestIVROReceived.getNss()) ? cuestIVROReceived
                .getNss() : "");
        cuestVO.setAgregMedico("");
        cuestVO.setNumero(StringUtils.isNotEmpty(cuestIVROReceived.getNumero()) ? cuestIVROReceived
                .getNumero() : "");
        cuestVO.setColonia(StringUtils.isNotEmpty(cuestIVROReceived.getColonia()) ? cuestIVROReceived
                .getColonia() : "");
        cuestVO.setCurp(StringUtils.isNotEmpty(cuestIVROReceived.getCurp()) ? cuestIVROReceived
                .getCurp() : "");
        cuestVO.setEstado(StringUtils.isNotEmpty(cuestIVROReceived.getEstado()) ? cuestIVROReceived
                .getEstado() : "");
        cuestVO.setCodPostal(StringUtils.isNotEmpty(cuestIVROReceived.getCodPostal()) ? cuestIVROReceived
                .getCodPostal() : "");
        cuestVO.setTelefono(StringUtils.isNotEmpty(cuestIVROReceived.getTelefono()) ? cuestIVROReceived
                .getTelefono() : "");
        cuestVO.setLugNacimiento(StringUtils.isNotEmpty(cuestIVROReceived.getLugNacimiento()) ? cuestIVROReceived
                .getLugNacimiento() : "");
        cuestVO.setFechaNacimiento(StringUtils.isNotEmpty(cuestIVROReceived.getFechaNacimiento()) ? cuestIVROReceived
                .getFechaNacimiento() : "");
        cuestVO.setEdad(StringUtils.isNotEmpty(cuestIVROReceived.getEdad()) ? cuestIVROReceived
                .getEdad() : "");
        cuestVO.setPoblacion(StringUtils.isNotEmpty(cuestIVROReceived.getPoblacion()) ? cuestIVROReceived
                .getPoblacion() : "");
        // Setea el sexo
        if (StringUtils.isNotEmpty(cuestIVROReceived.getSexFem())) {
            cuestVO.setSexFem("X");
            cuestVO.setSexMas("");
        } else if (StringUtils.isNotEmpty(cuestIVROReceived.getSexMas())) {
            cuestVO.setSexFem("");
            cuestVO.setSexMas("X");
        }
        cuestVO.setEstCivil(StringUtils.isNotEmpty(cuestIVROReceived.getEstCivil()) ? cuestIVROReceived
                .getEstCivil() : "");
        cuestVO.setCveDeleg(StringUtils.isNotEmpty(cuestIVROReceived.getCveDeleg()) ? cuestIVROReceived
                .getCveDeleg() : "");
        cuestVO.setUnidadMedFam(StringUtils.isNotEmpty(cuestIVROReceived.getUnidadMedFam()) ? cuestIVROReceived
                .getUnidadMedFam() : "");
        cuestVO.setOcupacion("");

        // Escolaridad
        cuestVO.setEscuelaTec("");
        cuestVO.setPreparatoria("");
        cuestVO.setSecundaria("");
        cuestVO.setProfesional("");
        cuestVO.setPrimaria("");

        // Setear la modadlidad
        cuestVO.setMod34(StringUtils.isNotEmpty(cuestIVROReceived.getMod34()) ? cuestIVROReceived
                .getMod34() : "");
        cuestVO.setMod35(StringUtils.isNotEmpty(cuestIVROReceived.getMod35()) ? cuestIVROReceived
                .getMod35() : "");
        cuestVO.setMod36("");
        cuestVO.setMod38("");
        cuestVO.setMod42("");
        cuestVO.setMod43(StringUtils.isNotEmpty(cuestIVROReceived.getMod43()) ? cuestIVROReceived
                .getMod43() : "");
        cuestVO.setMod44(StringUtils.isNotEmpty(cuestIVROReceived.getMod44()) ? cuestIVROReceived
                .getMod44() : "");

        // Empezamos a obtener las respuestas del primer cuestionario
        RespuestasCuestionario respCuest = cuestIVROReceived.getRespuestasCuestionario();
        Respuesta[] respuestas = respCuest.getRespuestas();
        int i = 0; // Respuesta Cuestionario 1 Pregunta 1
        Respuesta respuesta = respuestas[i];
        Opcion opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setActivFisicaNo("X");
            cuestVO.setActivFisicaSi("");
        } else {
            cuestVO.setActivFisicaSi("X");
            cuestVO.setActivFisicaNo("");
        }

        i++; // Respuesta Cuestionario 1 Pregunta 2
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setBriagoSi("");
            cuestVO.setBriagoNo("X");
            cuestVO.setNumCop("");
            cuestVO.setTiempoBriagues("");
        } else {
            cuestVO.setBriagoNo("");
            cuestVO.setBriagoSi("X");
            // La respuesta fue afirmativa incrementamos la posicion una vez
            // para obtener el numero de copas
            i++;
            respuesta = respuestas[i];
            opcionP = respuesta.getValores()[0];
            cuestVO.setNumCop(opcionP.getDescripcion());
            // La respuesta fue afirmativa incrementamos la posicion una vez
            // para obtener el tiempo que lleva de briago
            i++;
            respuesta = respuestas[i];
            opcionP = respuesta.getValores()[0];
            cuestVO.setTiempoBriagues(opcionP.getDescripcion());
        }

        i++; // Respuesta Cuestionario 1 Pregunta 3
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setFumaCigarillosSi("");
            cuestVO.setFumaCigarillosNo("X");
            cuestVO.setNumCigarrillos("");
            cuestVO.setTiempoFumador("");
        } else {
            cuestVO.setFumaCigarillosSi("X");
            cuestVO.setFumaCigarillosNo("");
            // La respuesta fue afirmativa incrementamos la posicion una vez
            // para obtener el numero de cigarrillos
            i++;
            respuesta = respuestas[i];
            opcionP = respuesta.getValores()[0];
            cuestVO.setNumCigarrillos(opcionP.getDescripcion());
            // La respuesta fue afirmativa incrementamos la posicion una vez
            // para obtener el tiempo que lleva de tabacalero
            i++;
            respuesta = respuestas[i];
            opcionP = respuesta.getValores()[0];
            cuestVO.setTiempoFumador(opcionP.getDescripcion());
        }

        i++;// Respuesta Cuestionario 1 Pregunta 4
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setAutomedicaSi("");
            cuestVO.setAutomedicaNo("X");
        } else {
            cuestVO.setAutomedicaSi("X");
            cuestVO.setAutomedicaNo("");
        }

        i++; // Respuesta Cuestionario 1 Pregunta 5
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion() != null) {
            if (opcionP.getDescripcion().trim().toUpperCase().equals(RESP_NO)) {
                cuestVO.setEstatura("");
            } else {
                cuestVO.setEstatura(opcionP.getDescripcion());
            }

        } else {
            cuestVO.setEstatura("");
        }

        i++;// Respuesta Cuestionario 1 Pregunta 6
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion() != null) {
            if (opcionP.getDescripcion().trim().toUpperCase().equals(RESP_NO)) {
                cuestVO.setPeso("");
            } else {
                cuestVO.setPeso(opcionP.getDescripcion());
            }
        } else {
            cuestVO.setPeso("");
        }

        // Empezamos a obtener las respuestas del segundo cuestionario
        i++; // Respuesta Cuestionario 2 Pregunta 1
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaUna("");
        } else {
            cuestVO.setPreguntaUna("X");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 2
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDos("");
        } else {
            cuestVO.setPreguntaUna("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 3
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaTres("");
        } else {
            cuestVO.setPreguntaTres("X");
        }
        i++; // Respuesta Cuestionario 2 Pregunta 4
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        Log.info("SECCION DIABETES CLAVE : " + opcionP.getClave() + "  --I=" + i);
        if (opcionP.getClave() == NONE_PREVIOUS) {
            cuestVO.setPreguntaCuartoNoNe("X");
            cuestVO.setPreguntaCuartoA("");
            cuestVO.setPreguntaCuartoB("");
            cuestVO.setPreguntaCuartoC("");
            cuestVO.setPreguntaCuartoD("");
        } else if (opcionP.getClave() == INSUFICIENCIA_RENAL) {
            cuestVO.setPreguntaCuartoA("X");
            cuestVO.setPreguntaCuartoNoNe("");
            cuestVO.setPreguntaCuartoB("");
            cuestVO.setPreguntaCuartoC("");
            cuestVO.setPreguntaCuartoD("");
        } else if (opcionP.getClave() == RETINOPATIA) {
            cuestVO.setPreguntaCuartoB("X");
            cuestVO.setPreguntaCuartoA("");
            cuestVO.setPreguntaCuartoC("");
            cuestVO.setPreguntaCuartoD("");
            cuestVO.setPreguntaCuartoNoNe("");
        } else if (opcionP.getClave() == NEUROPATIA) {
            cuestVO.setPreguntaCuartoC("X");
            cuestVO.setPreguntaCuartoA("");
            cuestVO.setPreguntaCuartoB("");
            cuestVO.setPreguntaCuartoD("");
            cuestVO.setPreguntaCuartoNoNe("");
        } else if (opcionP.getClave() == INSUFICIENCIA_CIRCULAR_PERIFERICA) {
            cuestVO.setPreguntaCuartoD("X");
            cuestVO.setPreguntaCuartoA("");
            cuestVO.setPreguntaCuartoB("");
            cuestVO.setPreguntaCuartoC("");
            cuestVO.setPreguntaCuartoNoNe("");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 5
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaCinco("");
        } else {
            cuestVO.setPreguntaCinco("X");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 6
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaSeis("");
        } else {
            cuestVO.setPreguntaSeis("");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 7
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaSiete("");
        } else {
            cuestVO.setPreguntaSiete("X");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 8
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaOcho("");
        } else {
            cuestVO.setPreguntaOcho("X");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 9
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaNueve("");
        } else {
            cuestVO.setPreguntaNueve("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 10
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDiez("");
        } else {
            cuestVO.setPreguntaDiez("X");
        }

        i++; // Respuesta Cuestionario 2 Pregunta 11
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaOnce("");
        } else {
            cuestVO.setPreguntaOnce("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 12
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDoce("");
        } else {
            cuestVO.setPreguntaDoce("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 13
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaTrece("");
        } else {
            cuestVO.setPreguntaTrece("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 14
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaCatorce("");
        } else {

            cuestVO.setPreguntaCatorce("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 15
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaQuince("");
        } else {
            cuestVO.setPreguntaQuince("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 16
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDieciseis("");
        } else {
            cuestVO.setPreguntaDieciseis("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 17
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDiecisiete("");
        } else {
            cuestVO.setPreguntaDiecisiete("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 18
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDieciocho("");
        } else {
            cuestVO.setPreguntaDieciocho("X");
        }

        i++;// Respuesta Cuestionario 2 Pregunta 19
        respuesta = respuestas[i];
        opcionP = respuesta.getValores()[0];
        if (opcionP.getDescripcion().toUpperCase().equals(RESP_NO)) {
            cuestVO.setPreguntaDiecinueve("");
        } else {
            cuestVO.setPreguntaDiecinueve("X");
        }
        cuestVO.setCadOriginal("");
        cuestVO.setSignature("");
        cuestVO.setSecuencia("");
        cuestVO.setNumSerie("");

        return cuestVO;
    }

}
