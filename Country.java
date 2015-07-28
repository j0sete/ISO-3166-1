package pkgCountry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Country implements PredicateCountry {

	private JSONObject json_data;
	private String country;
	
	/*
	 * Dos constructores, para pasar del tiron el nombre del pais o
	 * por el contrario, pasarlo despues al llamar la funcion getISO(pais)
	 */
	public Country() {
		json_data = null;
	}
	
	public Country(String country) {
		json_data = null;
		this.country = country;
	}
	
	// Metodo para obtener el nombre ISO 3166-1 a traves de la API de google maps 
	public String getISO(String country) throws InterruptedException, JSONException {
		
		// Utilizamos una thread, ya que tenemos que establecer una conexion http de manera asincrona
		// lo hacemos con una hebra distinta a la de la clase main, depues esperamos con el join()
		Thread t = new Thread(new ISOName(country));
		t.start();
		t.join();
		
		return getShortName();
	}

	// Este metodo se utiliza cuando le pasamos al constructor de clase el pais de busqueda
	public String getISO() throws InterruptedException, JSONException {
		String iso_name = null;
		
		// Comprobamos que efectivamente se lo hemos pasado
		if (country != null)
			iso_name = getISO(this.country);
		else
			System.out.println("Bad initialization:\n\tDid you initialize the country on constructor?");
		
		return iso_name;
	}
	
	private String getShortName() throws JSONException {
		
		String short_name = "ZZ"; // Por defecto nos devuelve el pais "ZZ" por si algo ha salido mal
		
		// De la trama json que nos devuelve la api de google maps cogemos el dato que nos interesa
		if (json_data != null) {
			short_name = ( (JSONArray) ( (JSONArray) json_data.get("results") ).getJSONObject(0).get("address_components") )
							.getJSONObject(0).get("short_name").toString();
		}
		
		return short_name;
		
	}
	
	private class ISOName implements Runnable {

		private String country;
		
		public ISOName(String country) {
			this.country = country;
		}
		
		@Override
		public void run(){
			
			HttpURLConnection con = null;
			URL url;
			
			InputStreamReader in = null;
			BufferedReader buf_in = null;
			
			try {
				
				url = new URL("http://maps.google.com/maps/api/geocode/json?address="+this.country+"&sensor=false");
				
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				
				in     = new InputStreamReader(con.getInputStream());
				buf_in = new BufferedReader(in);
				
				String line;
				StringBuilder buf = new StringBuilder();
				while( (line = buf_in.readLine()) != null ){
					buf.append(line);
					buf.append('\r');
				}
				
				buf_in.close();
				in.close();
				
				json_data = new JSONObject(buf.toString());
				
			} catch (IOException | JSONException e) {
				// Do something if u want
			}
			
		}
		
	}

}
