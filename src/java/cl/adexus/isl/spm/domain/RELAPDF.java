package cl.adexus.isl.spm.domain;


public class RELAPDF {

	String idRela;
	String fechaEmision;
	String cun;//del siniestro
	String esTipoDiat;
	String esTipoDiep;
	String fechaInscripcion;//del siniestro
	
	//Trabajador
	String nombreTrab;
	String runTrab;
	String dirTrab; //de la DIAT o DIEP(if esEP)
	String comunaTrab; //de la DIAT o DIEP(if esEP)
	String telefonoTrab; //de la DIAT o DIEP(if esEP)

	//Empleador
	String nombreEmpl;
	String rutEmpl;
	String dirEmpl; //de la DIAT o DIEP(if esEP)
	String comunaEmpl; //de la DIAT o DIEP(if esEP)

	//Incapacidad Laboral
	String fechaDesde; 
	String fechaHasta;
	String cantDiasReposo;
	
	//Medico Tratante
	String nombreMedico;
	String runMedico;

	String orgAdmin;

	/**
	 * @return the idRela
	 */
	public String getIdRela() {
		return idRela;
	}

	/**
	 * @param idRela the idRela to set
	 */
	public void setIdRela(String idRela) {
		this.idRela = idRela;
	}

	/**
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	/**
	 * @return the cun
	 */
	public String getCun() {
		return cun;
	}

	/**
	 * @param cun the cun to set
	 */
	public void setCun(String cun) {
		this.cun = cun;
	}

	/**
	 * @return the esTipoDiat
	 */
	public String getEsTipoDiat() {
		return esTipoDiat;
	}

	/**
	 * @param esTipoDiat the esTipoDiat to set
	 */
	public void setEsTipoDiat(String esTipoDiat) {
		this.esTipoDiat = esTipoDiat;
	}

	/**
	 * @return the esTipoDiep
	 */
	public String getEsTipoDiep() {
		return esTipoDiep;
	}

	/**
	 * @param esTipoDiep the esTipoDiep to set
	 */
	public void setEsTipoDiep(String esTipoDiep) {
		this.esTipoDiep = esTipoDiep;
	}

	/**
	 * @return the fechaInscripcion
	 */
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}

	/**
	 * @param fechaInscripcion the fechaInscripcion to set
	 */
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
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
	 * @return the nombreEmpl
	 */
	public String getNombreEmpl() {
		return nombreEmpl;
	}

	/**
	 * @param nombreEmpl the nombreEmpl to set
	 */
	public void setNombreEmpl(String nombreEmpl) {
		this.nombreEmpl = nombreEmpl;
	}

	/**
	 * @return the rutEmpl
	 */
	public String getRutEmpl() {
		return rutEmpl;
	}

	/**
	 * @param rutEmpl the rutEmpl to set
	 */
	public void setRutEmpl(String rutEmpl) {
		this.rutEmpl = rutEmpl;
	}

	/**
	 * @return the dirEmpl
	 */
	public String getDirEmpl() {
		return dirEmpl;
	}

	/**
	 * @param dirEmpl the dirEmpl to set
	 */
	public void setDirEmpl(String dirEmpl) {
		this.dirEmpl = dirEmpl;
	}

	/**
	 * @return the comunaEmpl
	 */
	public String getComunaEmpl() {
		return comunaEmpl;
	}

	/**
	 * @param comunaEmpl the comunaEmpl to set
	 */
	public void setComunaEmpl(String comunaEmpl) {
		this.comunaEmpl = comunaEmpl;
	}

	/**
	 * @return the fechaDesde
	 */
	public String getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * @return the fechaHasta
	 */
	public String getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	/**
	 * @return the cantDias
	 */
	public String getCantDiasReposo() {
		return cantDiasReposo;
	}

	/**
	 * @param cantDias the cantDias to set
	 */
	public void setCantDiasReposo(String cantDiasReposo) {
		this.cantDiasReposo = cantDiasReposo;
	}

	/**
	 * @return the nombreMedico
	 */
	public String getNombreMedico() {
		return nombreMedico;
	}

	/**
	 * @param nombreMedico the nombreMedico to set
	 */
	public void setNombreMedico(String nombreMedico) {
		this.nombreMedico = nombreMedico;
	}

	/**
	 * @return the runMedico
	 */
	public String getRunMedico() {
		return runMedico;
	}

	/**
	 * @param runMedico the runMedico to set
	 */
	public void setRunMedico(String runMedico) {
		this.runMedico = runMedico;
	}

	/**
	 * @return the orgAdmin
	 */
	public String getOrgAdmin() {
		return orgAdmin;
	}

	/**
	 * @param orgAdmin the orgAdmin to set
	 */
	public void setOrgAdmin(String orgAdmin) {
		this.orgAdmin = orgAdmin;
	}

}
