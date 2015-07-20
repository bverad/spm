package cl.adexus.isl.spm.domain;


public class RECAPDF {

	String idReca;
	String fechaResol;
	String cun;//del siniestro
	String fechaAccidente;//del siniestro
	
	//Trabajador
	String nombreTrab;
	String runTrab;
	String dirTrab; //de la DIAT o DIEP(if esEP)
	String comunaTrab; //de la DIAT o DIEP(if esEP)
	String telefonoTrab; //de la DIAT o DIEP(if esEP)

	//Empleador
	String nombreEmpl;
	String runEmpl;


	//calificacion accidente
	String codCalif; //Codigo Tipo Calificacion
	
	//codificacion enfermedad profesional
	String indicacion;

	String orgAdmin;

	/**
	 * @return the idReca
	 */
	public String getIdReca() {
		return idReca;
	}

	/**
	 * @param idReca the idReca to set
	 */
	public void setIdReca(String idReca) {
		this.idReca = idReca;
	}

	/**
	 * @return the fechaResol
	 */
	public String getFechaResol() {
		return fechaResol;
	}

	/**
	 * @param fechaResol the fechaResol to set
	 */
	public void setFechaResol(String fechaResol) {
		this.fechaResol = fechaResol;
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
	 * @return the fechaAccidente
	 */
	public String getFechaAccidente() {
		return fechaAccidente;
	}

	/**
	 * @param fechaAccidente the fechaAccidente to set
	 */
	public void setFechaAccidente(String fechaAccidente) {
		this.fechaAccidente = fechaAccidente;
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
	 * @return the rutTrab
	 */
	public String getRunTrab() {
		return runTrab;
	}

	/**
	 * @param rutTrab the rutTrab to set
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
	public String getRunEmpl() {
		return runEmpl;
	}

	/**
	 * @param rutEmpl the rutEmpl to set
	 */
	public void setRunEmpl(String runEmpl) {
		this.runEmpl = runEmpl;
	}

	/**
	 * @return the codCalif
	 */
	public String getCodCalif() {
		return codCalif;
	}

	/**
	 * @param codCalif the codCalif to set
	 */
	public void setCodCalif(String codCalif) {
		this.codCalif = codCalif;
	}

	/**
	 * @return the indicacion
	 */
	public String getIndicacion() {
		return indicacion;
	}

	/**
	 * @param indicacion the indicacion to set
	 */
	public void setIndicacion(String indicacion) {
		this.indicacion = indicacion;
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
