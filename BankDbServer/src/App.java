import java.sql.DriverManager;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import BankApp.Data;
import BankApp.DataHelper;

public class App {
	public static void main(String[] args) throws Exception {

		String argv[] = { "-ORBInitialPort", "900", "-ORBInitialHost",
				"192.168.43.77" };

		try {
			// Create and initialize the ORB
			ORB orb = ORB.init(argv, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// Create servant and register it with ORB
			DataObj dataObj = new DataObj();
			dataObj.setOrb(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(dataObj);
			Data href = DataHelper.narrow(ref);

			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			NameComponent path[] = ncRef.to_name("ABCDB");
			ncRef.rebind(path, href);

			System.out.println("Bank database server ready and waiting...");

			// Wait invocations from clients
			for (;;) {
				orb.run();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Database server exiting...");
	}
}
