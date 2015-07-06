package client;

import java.io.Serializable;

import org.omg.CORBA.portable.ValueFactory;
import org.omg.CORBA_2_3.portable.InputStream;

public class StockInfoFactory implements ValueFactory {

	public Serializable read_value(InputStream is) {
		return is.read_value(new StockInfoImpl());
	}
}
