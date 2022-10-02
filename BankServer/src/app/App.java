package app;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import BankApp.Bank;
import BankApp.BankHelper;
import BankApp.Data;
import BankApp.DataHelper;

public class App {
	public static void main(String[] args) throws Exception {

		// AccountObj acc = new AccountObj(args);
		// acc.create("Uss", 12345);

		String argv[] = { "-ORBInitialPort", "900", "-ORBInitialHost", "192.168.43.77" };
		String argdb[] = { "-ORBInitialPort", "900", "-ORBInitialHost", "192.168.43.77" };
		try {
			// Create and initialize the ORB
			ORB orb = ORB.init(argv, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// ORB for database=========================
			ORB orbdb = ORB.init(argdb, null);
			org.omg.CORBA.Object objRefdb = orbdb.resolve_initial_references("NameService");
			NamingContextExt ncRefdb = NamingContextExtHelper.narrow(objRefdb);
			String name = "ABCDB";
			Data data = DataHelper.narrow(ncRefdb.resolve_str(name));

			// System.out.println(data.getPass(100));
			// System.out.println(data.getBalance(1001));
			// data.update(200, 1001);

			// ======================================

			// Create servant and register it with ORB
			BankObj bankObj = new BankObj(rootpoa, data);
			bankObj.setOrb(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bankObj);
			Bank href = BankHelper.narrow(ref);

			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			NameComponent path[] = ncRef.to_name("ABC");
			ncRef.rebind(path, href);

			System.out.println("Bank server ready and waiting...");

			// Wait invocations from clients
			for (;;) {
				orb.run();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Server exiting...");
	}
}
