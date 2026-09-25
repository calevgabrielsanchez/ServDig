package mx.gob.imss.csdiss.sdroc.util.constants;

public enum EnumEstatusIncidendiaTipoObra {


		ACTUALIZACION(4),CANCELACION(1),REANUDACION(5),REPORTE_BIMESTRAL(6),SUSPENCION(2),TERMINACION(3);
		
		private int estatus;

		EnumEstatusIncidendiaTipoObra(int estatus) {
			this.estatus = estatus;
		}

		/**
		 * @return the estatus
		 */
		public int getEstatus() {
			return estatus;
		}
}
