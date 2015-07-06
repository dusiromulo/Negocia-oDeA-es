package stockLogger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import StockMarket.StockExchange;
import StockMarket.StockExchangeHelper;

public class Main {
	public static void main(String []args) throws InvalidName, AdapterInactive, ServantNotActive, WrongPolicy, IOException, ServantAlreadyActive{
		Properties orbProps = new Properties();
		
		orbProps.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
  		orbProps.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");  

  		ORB orb = ORB.init(args, orbProps);
  		POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		
  		DisplayExchangePrinter printerImpl = new DisplayExchangePrinter();
  		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		String ior = reader.readLine();
		reader.close();
		
		poa.activate_object(printerImpl);
		
		try {
			// Obtém a referência para o objeto CORBA
			org.omg.CORBA.Object obj = orb.string_to_object(ior);
			StockExchange server = StockExchangeHelper.narrow(obj);
			
			server.connectPrinter(printerImpl._this());
	    }
	    catch (TRANSIENT e) {
	      System.out.println("O serviço encontra-se indisponível.");
	    }
	    catch (COMM_FAILURE e) {
	      System.out.println("Falha de comunicação com o serviço.");
	    }
		
		orb.run();
	}
}
