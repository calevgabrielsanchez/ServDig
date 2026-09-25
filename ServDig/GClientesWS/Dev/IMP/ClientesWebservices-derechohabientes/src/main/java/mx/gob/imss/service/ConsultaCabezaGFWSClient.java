package mx.gob.imss.service;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class ConsultaCabezaGFWSClient {
	
	private String socketTimeoutException = "Ocurri� un error de comunicaci�n al intentar recuperar la cabeza de grupo familiar";
	
	public CabezaGrupoFamiliar obtieneInfoCabGpoFam(Long idAsignacionNSS, Boolean objetoCompleto)
			throws DerechohabientesWebSserviceException {
		try{
			WSConsInfoCabGpoFam_Service service = new WSConsInfoCabGpoFam_Service();
			WSConsInfoCabGpoFam ws = service.getWSConsInfoCabGpoFamPort();
			RespuestaWSConsInfoCabGpoFam respuesta = ws.obtieneInfoCabGpoFam( idAsignacionNSS.intValue() );
			CabezaGrupoFamiliar cgf = null;
			
			Boolean isPensionado = false;
			Long idPatron = null;
			String rp = null;
			String modalidad = null;
			Long idEstado = null;
			
			if (respuesta != null
					&& respuesta.getCodigoError() ==  0){
				cgf = new CabezaGrupoFamiliar();
				InfoCabezaGrupoFamiliarVO itemRespuesta = respuesta.getInfoCabezaGrupoFamiliarVO();
				idEstado = itemRespuesta.getCveEstadoDerechohabiente().getValue().longValue();
				
				cgf.setAsignacionNSS( itemRespuesta.getCveIdAsignacionNss().getValue().longValue() );
			
				Parentesco p = new Parentesco();
				Long idParentesco =itemRespuesta.getCveIdCalidadParentesco().getValue().longValue();
				isPensionado = idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
				p.setIdParentesco(idParentesco);
				cgf.setCalidadParentesco(p);
			
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date fecha = null;
				//Se valida fecha del ultimo movimiento que no venga nulo
				if(itemRespuesta.getFecUltimoMovto() != null) {
					fecha = formatter.parse( itemRespuesta.getFecUltimoMovto().getValue().toString() );
					cgf.setFechaUltimoMovAfiliacion( fecha );
				}
			
				if(itemRespuesta.getIndPatronImss() != null && itemRespuesta.getIndPatronImss().getValue() != null) {
					cgf.setPatronImss( itemRespuesta.getIndPatronImss().getValue().intValue() );
				}
				
				idPatron = itemRespuesta.getCveIdPatronGeneral() == null || itemRespuesta.getCveIdPatronGeneral().getValue() == null ? null : itemRespuesta.getCveIdPatronGeneral().getValue().longValue();
				rp = itemRespuesta.getRegPatron();
				modalidad = itemRespuesta.getCveModal();
				SujetoObligado patron = null;
				//Si es pensionado
				if(isPensionado) {
					//verificamos si la modalidad es nula
					if(modalidad != null ) {
						if(idPatron == null && (rp == null || rp.equals("00000000"))) {
							//si tiene modalidad pero no tiene patron mandamos una excepcion
							throw new DerechohabientesWebSserviceException("El pensionado cuenta con modalidad "+modalidad+", pero no cuenta con un patr&oacute;n v&aacute;lido("+this.getRp(idPatron, rp) + ").");
						} else {
							if(idPatron != null || modalidad.equals("35")) {
								patron = this.construirPatronSujetoObligado(idPatron, rp, modalidad);
							}
						}
					}//si la modalidad es nula el patron no se seteara y sera nulo
				} else {
					//si no es pensionado
					if(modalidad == null) {//Si la modalidad es nula
						//mandamos excepcion ya que siempre deberia mandar una modalidad aunque sea 00
						throw new DerechohabientesWebSserviceException("El asegurado no cuenta con ninguna modalidad relacionada.");
					} else { //Si la modalidad es diferente de nula
						if(idPatron == null && (rp == null || rp.equals("00000000"))) {//si viene el patron o tiene rp
							//Si el estado del asegurado es baja
							throw new DerechohabientesWebSserviceException("El asegurado no se encuentra relacionado a un patr&oacute;n v&aacute;lido("+
									this.getRp(idPatron, rp) + ") mod(" + modalidad+").");
						}
					}
					//Si no se mand� ninguna excepcion se construye el patron en base al id, el rp y la modalidad
					patron = this.construirPatronSujetoObligado(idPatron, rp, modalidad);
				}
				
				cgf.setPatronSujetoObligado(patron);
				
				if(itemRespuesta.getCveIdTipoMovimiento()!= null && itemRespuesta.getCveIdTipoMovimiento().getValue() !=null){
					TipoMovtoAsegurado movto = new TipoMovtoAsegurado();
					movto.setIdTipoMvtoAsegurado( itemRespuesta.getCveIdTipoMovimiento().getValue().longValue() );
					cgf.setTipoMovtoAsegurado(movto);
				}
			
				EstadoDerechohabiente estado = new EstadoDerechohabiente();
				estado.setIdEstadoDerechohabiente(idEstado);
				cgf.setEstadoDerechohabiente(estado);
					
				SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
				sub.setIdSubEstadoDerechohabiente( itemRespuesta.getCveSubestadoDerechohabiente().getValue().longValue() );
				cgf.setSubEstadoDerechohabiente(sub);
				
			
				if(itemRespuesta.getFecInicioVigencia() != null && itemRespuesta.getFecInicioVigencia().getValue() != null ){
					fecha = formatter.parse( itemRespuesta.getFecInicioVigencia().getValue().toString() );
					cgf.setFechaInicioVigencia( fecha );
				}
				
				if(itemRespuesta.getFecFinVigencia() != null && itemRespuesta.getFecFinVigencia().getValue() != null ){
					fecha = formatter.parse( itemRespuesta.getFecFinVigencia().getValue().toString() );
					cgf.setFechaFinVigencia( fecha );
				}
			
				if(itemRespuesta.getFecValidezConstancia() != null && itemRespuesta.getFecValidezConstancia().getValue() != null ){
						fecha = formatter.parse( itemRespuesta.getFecValidezConstancia().getValue().toString() );
						cgf.setFechaValidezConstancia( fecha );
				}
				if(itemRespuesta.getIndEstudiante() != null){
					cgf.setCveEstadoInconsistencia(itemRespuesta.getIndEstudiante());
					if(itemRespuesta.getIndEstudiante().intValue() == 1){
						cgf.setEsEstudiante(true);
						cgf.setCveEstadoInconsistencia(new Integer(EstadoInconsistenciaVigenciaEnum.ESTUDIANTES.getId()));
					}else{
						cgf.setEsEstudiante(false);
					}
				}else{
					cgf.setEsEstudiante(false);
					cgf.setCveEstadoInconsistencia(new Integer(0));
				}

				if(itemRespuesta.getConDerechoSm() != null ){
					cgf.setConDerechoSm(itemRespuesta.getConDerechoSm().getValue().toString());
				}
				
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}
			return cgf;
		} catch (Exception e) {
			System.out.println("Ocurrio un errro al consultar el ws de cabeza de grupo " + e );
			e.printStackTrace();
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la informaci�n del la Cabeza de Grupo Familiar");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}
	}
	
	private String getRp(Long idPatron, String rp) {
		String registrop = "";
		
		if(idPatron == null) {
			if(rp != null) {
				registrop = rp;
			} else {
				registrop = "s/p";
			}
		} 
		
		return registrop;
	}
	
	private SujetoObligado construirPatronSujetoObligado (Long idPatron, String rp, String modalidad) {
		SujetoObligado patron = new SujetoObligado();
		patron.setCveIdSujetoObligado( idPatron );
		patron.setNumeroRegistroPatronal(rp);
		patron.setModalidad(new Modalidad());
		patron.getModalidad().setNumModalidad(modalidad);
		
		return patron;
	}
}