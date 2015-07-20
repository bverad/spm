package cl.adexus.isl.spm.domain;


public class ALMEPDF {

	String idAlme;
	String fechaEmision;
	String cun;//del siniestro
	String esAccTrabajo;
	String esAccTrayecto;
	String esEnfProfesional;
	String fechaInscrip;//del siniestro
	
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

	//Alta Laboral
	String tipoAlta; 
	String fechaAlta;
	String siPresumeInvalidez;
	String noPresumeInvalidez;
	String descOtroMotivo;
	
	//Medico Tratante
	String nombreMedico;
	String runMedico;

	String orgAdmin;

	/**
	 * @return the idAlme
	 */
	public String getIdAlme() {
		return idAlme;
	}

	/**
	 * @param idAlme the idAlme to set
	 */
	public void setIdAlme(String idAlme) {
		this.idAlme = idAlme;
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
	 * @return the esAccTrabajo
	 */
	public String getEsAccTrabajo() {
		return esAccTrabajo;
	}

	/**
	 * @param esAccTrabajo the esAccTrabajo to set
	 */
	public void setEsAccTrabajo(String esAccTrabajo) {
		this.esAccTrabajo = esAccTrabajo;
	}

	/**
	 * @return the esAccTrayecto
	 */
	public String getEsAccTrayecto() {
		return esAccTrayecto;
	}

	/**
	 * @param esAccTrayecto the esAccTrayecto to set
	 */
	public void setEsAccTrayecto(String esAccTrayecto) {
		this.esAccTrayecto = esAccTrayecto;
	}

	/**
	 * @return the esEnfProfesional
	 */
	public String getEsEnfProfesional() {
		return esEnfProfesional;
	}

	/**
	 * @param esEnfProfesional the esEnfProfesional to set
	 */
	public void setEsEnfProfesional(String esEnfProfesional) {
		this.esEnfProfesional = esEnfProfesional;
	}

	/**
	 * @return the fechaInscrip
	 */
	public String getFechaInscrip() {
		return fechaInscrip;
	}

	/**
	 * @param fechaInscrip the fechaInscrip to set
	 */
	public void setFechaInscrip(String fechaInscrip) {
		this.fechaInscrip = fechaInscrip;
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
	 * @return the tipoAlta
	 */
	public String getTipoAlta() {
		return tipoAlta;
	}

	/**
	 * @param tipoAlta the tipoAlta to set
	 */
	public void setTipoAlta(String tipoAlta) {
		this.tipoAlta = tipoAlta;
	}

	/**
	 * @return the fechaAlta
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the siPresumeInvalidez
	 */
	public String getSiPresumeInvalidez() {
		return siPresumeInvalidez;
	}

	/**
	 * @param siPresumeInvalidez the siPresumeInvalidez to set
	 */
	public void setSiPresumeInvalidez(String siPresumeInvalidez) {
		this.siPresumeInvalidez = siPresumeInvalidez;
	}

	/**
	 * @return the noPresumeInvalidez
	 */
	public String getNoPresumeInvalidez() {
		return noPresumeInvalidez;
	}

	/**
	 * @param noPresumeInvalidez the noPresumeInvalidez to set
	 */
	public void setNoPresumeInvalidez(String noPresumeInvalidez) {
		this.noPresumeInvalidez = noPresumeInvalidez;
	}

	/**
	 * @return the descOtroMotivo
	 */
	public String getDescOtroMotivo() {
		return descOtroMotivo;
	}

	/**
	 * @param descOtroMotivo the descOtroMotivo to set
	 */
	public void setDescOtroMotivo(String descOtroMotivo) {
		this.descOtroMotivo = descOtroMotivo;
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
