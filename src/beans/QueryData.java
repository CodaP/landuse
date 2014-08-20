package beans;

public class QueryData {
	
	String query;
	boolean bayLakes;
	boolean daneCounty;
	boolean eauClaire;
	boolean ecwrpc;
	boolean madison;
	boolean ncwrpc;
	boolean sewrpc;
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
	public void setBayLakes(boolean bayLakes) {
		this.bayLakes = bayLakes;
	}
	
	public boolean isBayLakes() {
		return this.bayLakes;
	}
	
	public void setDaneCounty(boolean daneCounty) {
		this.daneCounty = daneCounty;
	}
	
	public boolean isDaneCounty() {
		return this.daneCounty;
	}
	
	public void setEauClaire(boolean eauClaire) {
		this.eauClaire = eauClaire;
	}
	
	public boolean isEauClaire() {
		return this.eauClaire;
	}
	
	public void setEcwrpc(boolean ecwrpc) {
		this.ecwrpc = ecwrpc;
	}
	
	public boolean isEcwrpc() {
		return this.ecwrpc;
	}
	
	public void setMadison(boolean madison) {
		this.madison = madison;
	}
	
	public boolean isMadison() {
		return this.madison;
	}
	
	public void setNcwrpc(boolean ncwrpc) {
		this.ncwrpc = ncwrpc;
	}
	
	public boolean isNcwrpc() {
		return this.ncwrpc;
	}
	
	public void setSewrpc(boolean sewrpc) {
		this.sewrpc = sewrpc;
	}
	
	public boolean isSewrpc() {
		return this.sewrpc;
	}
	
}