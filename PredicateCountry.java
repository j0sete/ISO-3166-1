package pkgCountry;


import org.json.JSONException;

public interface PredicateCountry {
	String getISO(String country) throws InterruptedException, JSONException;
	String getISO() throws JSONException, InterruptedException;
}
