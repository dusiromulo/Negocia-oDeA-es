package stockSeller;

import java.util.concurrent.ConcurrentLinkedQueue;

import StockMarket.ExchangePrinter;
import StockMarket.StockExchangePOA;
import StockMarket.UnknownSymbol;

public class StockExchangeImpl extends StockExchangePOA{
	private ConcurrentLinkedQueue<ExchangePrinter> printers;
	private StockServerImpl server;
	
	public StockExchangeImpl(StockServerImpl server) {
		this.printers = new ConcurrentLinkedQueue<ExchangePrinter>();
		this.server = server;
	}
	
	@Override
	public boolean buyStock(String arg0) throws UnknownSymbol {
		for(ExchangePrinter currPrinter: this.printers){
			currPrinter.print(arg0);
		}
		server.notifyExchange(arg0);
		return false;
	}

	@Override
	public boolean connectPrinter(ExchangePrinter arg0) {
		this.printers.add(arg0);
		return true;
	}
}
