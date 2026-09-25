/**
 * Servicios de localizacion de personas.
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.publicos;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

@Remote
public interface ServiciosPersonaBusinessRemote {

    /**
El servicio nos permite localizar los datos de una persona en el IMSS, RENAPO o SAT. El servicio podr� ser
 ejecutado bajo cualquiera de las 3 secciones de parametros:<br>
1.- CURP: sCurp<br>
2.- RFC: sRfc<br>
3.- Datos B�sicos: sNombre, sPrimerApellido, sSegundoApellido, fechaNacimeinto, lugarNacimeinto, sexo<br>
<br>
Seccion CURP<br>
1.- El servicio busca a la persona por CURP en el IMSS. Si la persona fue encontrada en el IMSS
 se regresa el idPersona y los datos basicos<br>
2.- Si la persona no fue encontrada en el IMSS se busca por CURP en RENPAO. Se regresan los metadatos de CURP y la calificacion
"Verificado por RENAPO"<br>
<br>

Seccion RFC
1.- El servicio busca a la persona por RFC en el IMSS. Si la persona fue encontrada en el IMSS
 se regresa el idPersona y los datos basicos<br>
2.- Si la persona no fue encontrada en el IMSS se busca por RFC en SAT. Se regresan los metadatos del SAT y la calificacion
"Verificado por SAT"<br>
<br>

Seccion Datos Basicos.<br>
1.- El servicio busca a la persona por datos basicos en el IMSS. Si la persona fue encontrada en el IMSS
 regresa el idPersona.<br>
2.- Si la persona no se encontro en el IMSS busca por datos basicos en el servicio de RENAPO<br>
a.1) Si la persona fue encontrada el servicio regresa:<br>
<ul>
<li>el objeto Calificacion.idCalificacion = 1   "Validado por RENAPO" </li>
<li>el atributo CURP encontrado en RENAPO</li>
</ul>
a.2) Si la persona no fue encontrada encontrada en RENAPO el servicio regresa en Calificacion.idCalificacion = 4   "No Validado"<br>
<br>

Notas.<br>
-Si al encontrar mas de una persona en el IMSS el servicio regresa una lista con el resultado de la base de datos, siendo
1000 la cantidad m�xima de registros que se pueden recuperar.<br>
     */
	
	List<Fisica> localizarPersonaFisica(String sCurp,
			String sRfc,			
			String sNombre,
			String sPrimerApellido,
			String sSegundoApellido,
			Date fechaNacimiento,
			EntidadFederativa lugarNacimiento,
			Sexo sexo) throws ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException;
	
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus respectivos documentos probatorios, los domicilios y medios de contacto; finalmente
	 * los concentra en el mismo objeto
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Fisica buscarPersonaFisicayDPyDyMCEnIMSS(Long idPersona)
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 * @throws NssRelacionadoVariasPersonasException
	 * @throws PersonasNoLocalizadasException
	 */
	Fisica buscarPersonaFisicayDPyDyMCEnIMSSbyNSS(String nss)
			throws PersonaFisicaNoEncontradaException,
			PersonasNoLocalizadasException, NssRelacionadoVariasPersonasException;
	
	/**
	 * Metodo que complementa la informaci�n de una persona Fisica en documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * @param  objeto Fisica fIMSS
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Fisica complementaPersonaFisicaDPyDyMCEnIMSS(Fisica  pfIMSS) 
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * Este metodo busca solo la informacion de la persona fisica, sin recuperar nada mas, que los datos personales
	 * @param idPersona
	 * @return
	 */
	Fisica buscarPersonaFisicaWidget(Long idPersona) throws PersonaFisicaNoEncontradaException, PersonaNoEncontradaException;
	
	
	Fisica buscarPersonaWidget(Long idPersona) throws PersonaNoEncontradaException;
	
	/**
	 * Este metodo busca una persona moral en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 */
	Moral buscarPersonaMoralyDPyDyMCEnIMSS(Long idPersona);
	
	/**
	 * Servicio que busca una persona fisica en el IMSS y obtiene su domicilio
	 * fiscal. Este servicio es especial para la modificacion manual, ya que
	 * para la modificacion manual solo se necesita obtener el domicilio fiscal,
	 * las dem�s relaciones se obtienen de manera as�ncrona.
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Fisica buscarPersonaFisicaParaModificacionManual(Long idPersona)
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * Servicio que busca una persona moral en el IMSS y obtiene su domicilio
	 * fiscal. Este servicio es especial para la modificacion manual, ya que
	 * para la modificacion manual solo se necesita obtener el domicilio fiscal,
	 * las dem�s relaciones se obtienen de manera as�ncrona.
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Moral buscarPersonaMoralParaModificacionManual(Long idPersona);

	boolean validarExistenciaCorreoElectronicoPersonaFisica(Long idPersona);
	
	
	String getCorreoRegistro(Long cveIdPerson);
	
	
	/**
	 * Este metodo busca una persona moral con sus datos basicos
	 * 
	 * @param idPersona
	 * @return
	 */
	Moral buscarPMyDPyDyMCEnIMSS(Long idPersona);

	/**
	 * Este metodo busca una persona moral en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 */
	Moral buscarPMyDPyDyMCEnIMSS_AP(Long idPersona);

}
