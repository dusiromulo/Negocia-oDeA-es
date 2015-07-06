package stockLogger;

import StockMarket.ExchangePrinterPOA;

public class DisplayExchangePrinter extends ExchangePrinterPOA {

	@Override
	public void print(String arg0) {
		System.out.println(arg0);
	}
}
