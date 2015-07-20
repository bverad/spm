package cl.adexus.constraints	

class Modulo11Constraint {
	static name = "mod11"
	static defaultMessageCode = "default.not.mod11.message"
	static defaultMessage = "Modulo 11 no valido"

	def supports = { type ->
		return type!= null && String.class.isAssignableFrom(type);
	}

	def validate = { run ->
		try{
			def num=run.substring(0,run.length()-1)
			def dv=run.substring(run.length()-1) 
			int M=0,S=1,T=Integer.parseInt(num);for(;T!=0;T/=10)S=(S+T%10*(9-M++%6))%11;
			return dv==((char)(S!=0?S+47:75));
		} catch (Exception e) {
			return false
		}
	}
}
