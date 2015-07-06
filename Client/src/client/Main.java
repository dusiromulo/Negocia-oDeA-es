package client;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA_2_3.ORB;

import StockMarket.StockExchange;
import StockMarket.StockExchangeHelper;
import StockMarket.StockInfo;
import StockMarket.StockInfoHelper;
import StockMarket.StockServer;
import StockMarket.StockServerHelper;
import StockMarket.UnknownSymbol;

public class Main {
	public static void main(String[] args) throws IOException {
		// As propriedades que informam o uso do JacORB como ORB.
		Properties orbProps = new Properties();
		orbProps.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
		orbProps.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
		
		// Inicializa o ORB.
		ORB orb = (ORB) ORB.init(args, orbProps);
		orb.register_value_factory(StockInfoHelper.id(), new StockInfoFactory());
		
		// Lê o IOR do arquivo cujo nome é passado como parâmetro
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		String iorStockServer = reader.readLine();
		reader.close();
		
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));
		String iorStockExchange = reader.readLine();
		reader.close();
		
		// Obtém a referência para o objeto CORBA
		org.omg.CORBA.Object objStockServer = orb.string_to_object(iorStockServer);
		StockServer stockServer = StockServerHelper.narrow(objStockServer);
		
		org.omg.CORBA.Object objStockExchange = orb.string_to_object(iorStockExchange);
		StockExchange stockExchange = StockExchangeHelper.narrow(objStockExchange);
		
		while(true){
			try {
				StockInfo[] avaiableActions = stockServer.getStockInfos();
				for(StockInfo currStockInfo:avaiableActions){
					System.out.println(currStockInfo._toString());
				}
				
				Console console = System.console();
				String input = console.readLine("Digite a acao que deseja comprar:");
				boolean validValue = false;
				while(!validValue) {
					try {
						stockServer.getStockValue(input);
						stockExchange.buyStock(input);
						validValue = true;
					} catch (UnknownSymbol e) {
						input = console.readLine("Acao nao existe. Digite a acao que deseja comprar:");
					}
				}
				
		    }
			catch (TRANSIENT e) {
				System.out.println("O serviço encontra-se indisponível.");
				objStockServer = orb.string_to_object(iorStockServer);
				stockServer = StockServerHelper.narrow(objStockServer);
				objStockExchange = orb.string_to_object(iorStockExchange);
				stockExchange = StockExchangeHelper.narrow(objStockExchange);
		    }
		    catch (COMM_FAILURE e) {
				System.out.println("Falha de comunicação com o serviço.");
				objStockServer = orb.string_to_object(iorStockServer);
				stockServer = StockServerHelper.narrow(objStockServer);
				objStockExchange = orb.string_to_object(iorStockExchange);
				stockExchange = StockExchangeHelper.narrow(objStockExchange);
		    }
		}
	}
}

		
