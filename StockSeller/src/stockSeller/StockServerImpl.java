package stockSeller;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import StockMarket.StockInfo;
import StockMarket.StockServerPOA;
import StockMarket.UnknownSymbol;

public class StockServerImpl extends StockServerPOA{
	private ConcurrentHashMap<String, Float> stockSymbols;

	public StockServerImpl() {
		this.stockSymbols = new ConcurrentHashMap<String, Float>();
		
		Random randomGenerator = new Random();
		
		System.out.println("Acoes criadas");
		for(int i=0; i<20;i++){
			String symbolName = "";
			Float symbolValue = 0f;
			
			for(int j=0; j<5; j++){
				symbolName += new Character((char) ('a' + randomGenerator.nextInt(26)));
			}
			
			symbolValue = (randomGenerator.nextFloat() * 10.0f);
			this.stockSymbols.put(symbolName, symbolValue);
			System.out.println(symbolName + ": " + symbolValue);
		}
	}
	
	public void notifyExchange(String symbol) throws UnknownSymbol{
		if(this.stockSymbols.containsKey(symbol)){
			this.stockSymbols.put(symbol, this.stockSymbols.get(symbol)*1.1f);
		}
		else{
			throw new UnknownSymbol(symbol);
		}	
	}
	
	@Override
	public String[] getStockSymbols() {
		String []keysArray = new String[this.stockSymbols.keySet().size()];
		Iterator<String> iteratorKeys = this.stockSymbols.keySet().iterator();
		int index = 0;
		while(iteratorKeys.hasNext()){
			String currKey = iteratorKeys.next();
			keysArray[index] = currKey;
			index++;
		}
		return keysArray;
	}

	@Override
	public float getStockValue(String arg0) throws UnknownSymbol {
		if(this.stockSymbols.containsKey(arg0)){
			return this.stockSymbols.get(arg0);
		}
		else{
			throw new UnknownSymbol(arg0);
		}
	}

	@Override
	public StockInfo[] getStockInfos() {
		String[] stockSymbols = getStockSymbols();
		StockInfo[] stockInfoArray = new StockInfoImpl[stockSymbols.length];
		for(int i=0; i < stockSymbols.length; i++) {
			String currSymbol = stockSymbols[i];
			Float currValue = this.stockSymbols.get(currSymbol);
			stockInfoArray[i] = new StockInfoImpl(currSymbol, currValue);
		}
		return stockInfoArray;
	}
}
