package stockSeller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

import org.omg.CORBA_2_3.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;


public class Main {
	
	public static void main(String []args) throws InvalidName, AdapterInactive, ServantNotActive, WrongPolicy, FileNotFoundException{
		Properties orbProps = new Properties();
		
		orbProps.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
  		orbProps.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");  

  		ORB orb = (ORB) ORB.init(args, orbProps);
  		POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		
  		StockServerImpl stockServer = new StockServerImpl();
  		org.omg.CORBA.Object stubStockServer = poa.servant_to_reference(stockServer);  		
  		
  		StockExchangeImpl stockExchange = new StockExchangeImpl(stockServer);
		org.omg.CORBA.Object stubStockExchange = poa.servant_to_reference(stockExchange);
		
		PrintWriter fileWriter = new PrintWriter(args[0]);
		fileWriter.println(orb.object_to_string(stubStockServer));
		fileWriter.close();
		
		fileWriter = new PrintWriter(args[1]);
		fileWriter.println(orb.object_to_string(stubStockExchange));
		fileWriter.close();

		orb.run();
	}
}
