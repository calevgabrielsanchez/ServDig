package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ProcesoSIMEServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoAsignacionSIMEBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;
import mx.gob.imss.ctirss.delta.model.enums.EstadoRegistroSIMEEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util.MovimientoAsignacionSIMETypeBuilder;

@Stateless(name = "procesoSIMEServiceBusiness", mappedName = "procesoSIMEServiceBusiness")
public class ProcesoSIMEServiceBusiness extends AbstractServiceBusiness
		implements ProcesoSIMEServiceBusinessRemote {

	@EJB
	private MovimientoAsignacionSIMEBusinessRemote movimientoAsignacionSIMEBusiness;

	@Override
	public byte[] generarContenidoArchivoSAIIA(Map<Integer, Empleado> registros) {

		Empleado empleado = null;
		MovimientoAsignacionSIMEType movimiento = null;
		List<MovimientoAsignacionSIMEType> movimientosSIME = new ArrayList<MovimientoAsignacionSIMEType>();
		
		StringBuffer guia = new StringBuffer();
		int salarioBase = 0;
		
		for (Entry<Integer, Empleado> entry : registros.entrySet()) {
			empleado = entry.getValue();
									
			/*
			 * Se checa si el registro fue procesado exitosamente, de ser así se
			 * debe incluir en el archivo
			 */
			if (empleado.getEstadoRegistro().getClave() == EstadoRegistroSIMEEnum.EXITO.getClave()) {
				
				guia.append(empleado.getCveSubdelegacionUsuario());
				guia.append("400");
				
				try {
					salarioBase = Integer.parseInt(empleado.getSalarioBase().replace(".", ""));
				} catch (NumberFormatException e) {
					this.log.error("Error al parsear el salario base del registro para SIME: " + e.getMessage());
					salarioBase = 0;
				}
				
				movimiento = new MovimientoAsignacionSIMETypeBuilder()
						.withRegistroPatronal(empleado.getCveRegPatron())
						.withNss(Long.valueOf(empleado.getNss()))
						.withPrimerApellido(empleado.getApellidoPaterno())
						.withSegundoApellido(empleado.getApellidoMaterno())
						.withNombre(empleado.getNombre())
						.withSalarioBase(salarioBase)
						.withCampoGenerico("000000")
						.withTipoTrabajor(1)
						.withTipoSalario(0)
						.withJornadaReducida(0)
						.withFechaMovimiento(empleado.getFecIngreso())
						.withUnidadMedica(empleado.getUmf().getNoEconomico().intValue())
						.withTipoMovimiento(8)
						.withGuia(Integer.parseInt(guia.toString()))
						.withClave("0000000000")
						.withCurp(empleado.getCurp())
						.withIdentificadorFormato(9)
						.build();
	
				movimientosSIME.add(movimiento);
				
				guia.delete(0, guia.length());
			}
		}

		List<String> tramasSIME = movimientoAsignacionSIMEBusiness
				.procesarMovimientosAsignacionSIME(movimientosSIME);

		StringBuffer cadena = new StringBuffer();

		for (String trama : tramasSIME) {
			cadena.append(trama).append("\n");
		}

		return cadena.toString().getBytes();
	}
}