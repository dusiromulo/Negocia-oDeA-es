package stockSeller;

import StockMarket.StockInfo;

public class StockInfoImpl extends StockInfo{
	private static final long serialVersionUID = -6134454348001586121L;

	public StockInfoImpl(String name, Float value){
		this.name = name;
		this.value = value;
	}
	
	public StockInfoImpl(){
		// Only for factory
	}
	
	@Override
	public String _toString() {
		return this.name+": "+this.value;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setValue(Float value){
		this.value = value;
	}

}
