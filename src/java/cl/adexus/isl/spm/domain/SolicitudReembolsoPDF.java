package cl.adexus.isl.spm.domain;


public class SolicitudReembolsoPDF {

	String idSiniestro;
	String descTipoSiniestro;
	String idSiniestro_2;
	String descTipoSiniestro_2;
	
	//Identificacion del Beneficio
	String esTraslado;
	String esMedicamento;
	String esHospitalizacion;
	String esAlojamiento;
	
	//Trabajador
	String nombreTrab;
	String runTrab;
	String dirTrab;
	String comunaTrab; 
	String telefonoTrab;
	String celularTrab;
	String emailTrab;

	//Solicitante
	String descTipoSolicitante;
	String nombreSolicitante;
	String rutSolicitante;
	String relacionSolicitante;
	String nombreSolicitante_2;
	String rutSolicitante_2;
	
	//Pago
	String montoSolicitado; 
	String rutPago; 
	String nombreCobrador; 
	String descTipoPago; 
	String descTipoCuenta; 
	String numCuenta; 
	String banco; 
	
	//otros
	String observaciones;
	
	String fechaRecepcion;

	/**
	 * @return the idSiniestro
	 */
	public String getIdSiniestro() {
		return idSiniestro;
	}

	/**
	 * @param idSiniestro the idSiniestro to set
	 */
	public void setIdSiniestro(String idSiniestro) {
		this.idSiniestro = idSiniestro;
		this.idSiniestro_2 = idSiniestro;
	}

	/**
	 * @return the descSiniestro
	 */
	public String getDescTipoSiniestro() {
		return descTipoSiniestro;
	}

	/**
	 * @param descSiniestro the descSiniestro to set
	 */
	public void setDescTipoSiniestro(String descTipoSiniestro) {
		this.descTipoSiniestro = descTipoSiniestro;
		this.descTipoSiniestro_2 = descTipoSiniestro;
	}

	/**
	 * @return the esTraslado
	 */
	public String getEsTraslado() {
		return esTraslado;
	}

	/**
	 * @param esTraslado the esTraslado to set
	 */
	public void setEsTraslado(String esTraslado) {
		this.esTraslado = esTraslado;
	}

	/**
	 * @return the esMedicamento
	 */
	public String getEsMedicamento() {
		return esMedicamento;
	}

	/**
	 * @param esMedicamento the esMedicamento to set
	 */
	public void setEsMedicamento(String esMedicamento) {
		this.esMedicamento = esMedicamento;
	}

	/**
	 * @return the esHospitalizacion
	 */
	public String getEsHospitalizacion() {
		return esHospitalizacion;
	}

	/**
	 * @param esHospitalizacion the esHospitalizacion to set
	 */
	public void setEsHospitalizacion(String esHospitalizacion) {
		this.esHospitalizacion = esHospitalizacion;
	}

	/**
	 * @return the esAlojamiento
	 */
	public String getEsAlojamiento() {
		return esAlojamiento;
	}

	/**
	 * @param esAlojamiento the esAlojamiento to set
	 */
	public void setEsAlojamiento(String esAlojamiento) {
		this.esAlojamiento = esAlojamiento;
	}

	/**
	 * @return the nombreTrab
	 */
	public String getNombreTrab() {
		return nombreTrab;
	}

	/**
	 * @param nombreTrab the nombreTrab to set
	 */
	public void setNombreTrab(String nombreTrab) {
		this.nombreTrab = nombreTrab;
	}

	/**
	 * @return the runTrab
	 */
	public String getRunTrab() {
		return runTrab;
	}

	/**
	 * @param runTrab the runTrab to set
	 */
	public void setRunTrab(String runTrab) {
		this.runTrab = runTrab;
	}

	/**
	 * @return the dirTrab
	 */
	public String getDirTrab() {
		return dirTrab;
	}

	/**
	 * @param dirTrab the dirTrab to set
	 */
	public void setDirTrab(String dirTrab) {
		this.dirTrab = dirTrab;
	}

	/**
	 * @return the comunaTrab
	 */
	public String getComunaTrab() {
		return comunaTrab;
	}

	/**
	 * @param comunaTrab the comunaTrab to set
	 */
	public void setComunaTrab(String comunaTrab) {
		this.comunaTrab = comunaTrab;
	}

	/**
	 * @return the telefonoTrab
	 */
	public String getTelefonoTrab() {
		return telefonoTrab;
	}

	/**
	 * @param telefonoTrab the telefonoTrab to set
	 */
	public void setTelefonoTrab(String telefonoTrab) {
		this.telefonoTrab = telefonoTrab;
	}

	/**
	 * @return the celularTrab
	 */
	public String getCelularTrab() {
		return celularTrab;
	}

	/**
	 * @param celularTrab the celularTrab to set
	 */
	public void setCelularTrab(String celularTrab) {
		this.celularTrab = celularTrab;
	}

	/**
	 * @return the emailTrab
	 */
	public String getEmailTrab() {
		return emailTrab;
	}

	/**
	 * @param emailTrab the emailTrab to set
	 */
	public void setEmailTrab(String emailTrab) {
		this.emailTrab = emailTrab;
	}

	/**
	 * @return the descTipoSolicitante
	 */
	public String getDescTipoSolicitante() {
		return descTipoSolicitante;
	}

	/**
	 * @param tipoSolicitante the descTipoSolicitante to set
	 */
	public void setDescTipoSolicitante(String descTipoSolicitante) {
		this.descTipoSolicitante = descTipoSolicitante;
	}

	/**
	 * @return the nombreSolicitante
	 */
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	/**
	 * @param nombreSolicitante the nombreSolicitante to set
	 */
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
		this.nombreSolicitante_2 = nombreSolicitante;
	}

	/**
	 * @return the rutSolicitante
	 */
	public String getRutSolicitante() {
		return rutSolicitante;
	}

	/**
	 * @param rutSolicitante the rutSolicitante to set
	 */
	public void setRutSolicitante(String rutSolicitante) {
		this.rutSolicitante = rutSolicitante;
		this.rutSolicitante_2 = rutSolicitante;
	}

	/**
	 * @return the relacionSolicitante
	 */
	public String getRelacionSolicitante() {
		return relacionSolicitante;
	}

	/**
	 * @param relacionSolicitante the relacionSolicitante to set
	 */
	public void setRelacionSolicitante(String relacionSolicitante) {
		this.relacionSolicitante = relacionSolicitante;
	}

	/**
	 * @return the montoSolicitado
	 */
	public String getMontoSolicitado() {
		return montoSolicitado;
	}

	/**
	 * @param montoSolicitado the montoSolicitado to set
	 */
	public void setMontoSolicitado(String montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	/**
	 * @return the rutPago
	 */
	public String getRutPago() {
		return rutPago;
	}

	/**
	 * @param rutPago the rutPago to set
	 */
	public void setRutPago(String rutPago) {
		this.rutPago = rutPago;
	}

	/**
	 * @return the nombreCobrador
	 */
	public String getNombreCobrador() {
		return nombreCobrador;
	}

	/**
	 * @param nombreCobrador the nombreCobrador to set
	 */
	public void setNombreCobrador(String nombreCobrador) {
		this.nombreCobrador = nombreCobrador;
	}

	/**
	 * @return the descTipoPago
	 */
	public String getDescTipoPago() {
		return descTipoPago;
	}

	/**
	 * @param descTipoPago the descTipoPago to set
	 */
	public void setDescTipoPago(String descTipoPago) {
		this.descTipoPago = descTipoPago;
	}

	/**
	 * @return the descTipoCuenta
	 */
	public String getDescTipoCuenta() {
		return descTipoCuenta;
	}

	/**
	 * @param descTipoCuenta the descTipoCuenta to set
	 */
	public void setDescTipoCuenta(String descTipoCuenta) {
		this.descTipoCuenta = descTipoCuenta;
	}

	/**
	 * @return the numCuenta
	 */
	public String getNumCuenta() {
		return numCuenta;
	}

	/**
	 * @param numCuenta the numCuenta to set
	 */
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}

	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the fechaRecepcion
	 */
	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * @return the idSiniestro_2
	 */
	public String getIdSiniestro_2() {
		return idSiniestro_2;
	}

	/**
	 * @param idSiniestro_2 the idSiniestro_2 to set
	 */
	public void setIdSiniestro_2(String idSiniestro_2) {
		this.idSiniestro_2 = idSiniestro_2;
	}

	/**
	 * @return the descTipoSiniestro_2
	 */
	public String getDescTipoSiniestro_2() {
		return descTipoSiniestro_2;
	}

	/**
	 * @param descTipoSiniestro_2 the descTipoSiniestro_2 to set
	 */
	public void setDescTipoSiniestro_2(String descTipoSiniestro_2) {
		this.descTipoSiniestro_2 = descTipoSiniestro_2;
	}

	/**
	 * @return the nombreSolicitante_2
	 */
	public String getNombreSolicitante_2() {
		return nombreSolicitante_2;
	}

	/**
	 * @param nombreSolicitante_2 the nombreSolicitante_2 to set
	 */
	public void setNombreSolicitante_2(String nombreSolicitante_2) {
		this.nombreSolicitante_2 = nombreSolicitante_2;
	}

	/**
	 * @return the rutSolicitante_2
	 */
	public String getRutSolicitante_2() {
		return rutSolicitante_2;
	}

	/**
	 * @param rutSolicitante_2 the rutSolicitante_2 to set
	 */
	public void setRutSolicitante_2(String rutSolicitante_2) {
		this.rutSolicitante_2 = rutSolicitante_2;
	}
	
	
	
}
