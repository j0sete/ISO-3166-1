package pkgCountry;

import org.json.JSONException;

public class Demo {

	private final static String SPAIN  = "Spain";
	private final static String FRANCE = "France";
	private final static String ITALY  = "Finland";
	
	private final static String NAME_OF = "ISO3166-1 name of ";
	
	public static void main(String[] args) {
				
		// Ejemplo de uso:
		
		// Inicializamos con 2 paises
		Country c1 = new Country(SPAIN);
		Country c2 = new Country(FRANCE);
		Country c3 = new Country();
		
		try {
			System.out.println(NAME_OF + SPAIN  + " is " + c1.getISO());
			System.out.println(NAME_OF + FRANCE + " is " + c2.getISO());
			
			// Aqui llamamos al metodo con el pais que queremos tener
			System.out.println(NAME_OF + ITALY  + " is " + c3.getISO(ITALY));
		} catch (JSONException | InterruptedException e) {
			// Do something if u want
		}

	}

}
