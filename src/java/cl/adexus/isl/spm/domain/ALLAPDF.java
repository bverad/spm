package cl.adexus.isl.spm.domain;


public class ALLAPDF {

	String idAlla;
	String fechaEmision;
	String cun;//del siniestro
	String esTipoDiat;
	String esTipoDiep;
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
	String siEsAltaInmediata; 
	String noEsAltaInmediata; 
	String fechaAltaLaboral;
	String siTieneCondiciones;
	String noTieneCondiciones;
	String descCondiciones;
	String cantDias;
	String siContTratamiento;
	String noContTratamiento;
	String descTratamiento;
	
	//Medico Tratante
	String nombreMedico;
	String runMedico;

	String orgAdmin;

	/**
	 * @return the idAlla
	 */
	public String getIdAlla() {
		return idAlla;
	}

	/**
	 * @param idAlla the idAlla to set
	 */
	public void setIdAlla(String idAlla) {
		this.idAlla = idAlla;
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
	 * @return the siEsAltaInmediata
	 */
	public String getSiEsAltaInmediata() {
		return siEsAltaInmediata;
	}

	/**
	 * @param siEsAltaInmediata the siEsAltaInmediata to set
	 */
	public void setSiEsAltaInmediata(String siEsAltaInmediata) {
		this.siEsAltaInmediata = siEsAltaInmediata;
	}

	/**
	 * @return the noEsAltaInmediata
	 */
	public String getNoEsAltaInmediata() {
		return noEsAltaInmediata;
	}

	/**
	 * @param noEsAltaInmediata the noEsAltaInmediata to set
	 */
	public void setNoEsAltaInmediata(String noEsAltaInmediata) {
		this.noEsAltaInmediata = noEsAltaInmediata;
	}

	/**
	 * @return the fechaAltaLaboral
	 */
	public String getFechaAltaLaboral() {
		return fechaAltaLaboral;
	}

	/**
	 * @param fechaAltaLaboral the fechaAltaLaboral to set
	 */
	public void setFechaAltaLaboral(String fechaAltaLaboral) {
		this.fechaAltaLaboral = fechaAltaLaboral;
	}

	/**
	 * @return the siTieneCondiciones
	 */
	public String getSiTieneCondiciones() {
		return siTieneCondiciones;
	}

	/**
	 * @param siTieneCondiciones the siTieneCondiciones to set
	 */
	public void setSiTieneCondiciones(String siTieneCondiciones) {
		this.siTieneCondiciones = siTieneCondiciones;
	}

	/**
	 * @return the noTieneCondiciones
	 */
	public String getNoTieneCondiciones() {
		return noTieneCondiciones;
	}

	/**
	 * @param noTieneCondiciones the noTieneCondiciones to set
	 */
	public void setNoTieneCondiciones(String noTieneCondiciones) {
		this.noTieneCondiciones = noTieneCondiciones;
	}

	/**
	 * @return the descCondiciones
	 */
	public String getDescCondiciones() {
		return descCondiciones;
	}

	/**
	 * @param descCondiciones the descCondiciones to set
	 */
	public void setDescCondiciones(String descCondiciones) {
		this.descCondiciones = descCondiciones;
	}

	/**
	 * @return the cantDias
	 */
	public String getCantDias() {
		return cantDias;
	}

	/**
	 * @param cantDias the cantDias to set
	 */
	public void setCantDias(String cantDias) {
		this.cantDias = cantDias;
	}

	/**
	 * @return the siContTratamiento
	 */
	public String getSiContTratamiento() {
		return siContTratamiento;
	}

	/**
	 * @param siContTratamiento the siContTratamiento to set
	 */
	public void setSiContTratamiento(String siContTratamiento) {
		this.siContTratamiento = siContTratamiento;
	}

	/**
	 * @return the noContTratamiento
	 */
	public String getNoContTratamiento() {
		return noContTratamiento;
	}

	/**
	 * @param noContTratamiento the noContTratamiento to set
	 */
	public void setNoContTratamiento(String noContTratamiento) {
		this.noContTratamiento = noContTratamiento;
	}

	/**
	 * @return the descTratamiento
	 */
	public String getDescTratamiento() {
		return descTratamiento;
	}

	/**
	 * @param descTratamiento the descTratamiento to set
	 */
	public void setDescTratamiento(String descTratamiento) {
		this.descTratamiento = descTratamiento;
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
