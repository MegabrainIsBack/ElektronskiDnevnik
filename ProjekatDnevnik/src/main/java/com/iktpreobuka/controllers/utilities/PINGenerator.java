package com.iktpreobuka.controllers.utilities;

public class PINGenerator {
	public static String PGenerator(String korisnik) {
		Integer rndDio= (int) (Math.random()*100000);
		String rndDioS=rndDio.toString();	
		String pin="";
		
		if (korisnik.equalsIgnoreCase("nastavnik")){
			pin="n"+rndDioS;
		}
		
		if (korisnik.equalsIgnoreCase("roditelj")){
			pin="r"+rndDioS;
		}
		
		if (korisnik.equalsIgnoreCase("ucenik")){
			pin="u"+rndDioS;
		}
		
		if (korisnik.equalsIgnoreCase("admin")){
			pin="a"+rndDioS;
		}
		
		return pin;
	}

}
