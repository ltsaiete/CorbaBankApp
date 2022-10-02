package app;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import BankApp.*;

public class BankObj extends BankPOA {
	private POA rootpoa;
	private ORB orb;
	private Data data;

	BankObj(POA rootpoa, Data data) {
		this.rootpoa = rootpoa;
		this.data = data;
	}

	public void setOrb(ORB orb) {
		this.orb = orb;
	}

	@Override
	public Account create() {
		// TODO Auto-generated method stub
		AccountObj servant = new AccountObj(this.data);
		org.omg.CORBA.Object objRef;
		try {
			objRef = rootpoa.servant_to_reference(servant);
			Account acc = AccountHelper.narrow(objRef);
			return acc;
		} catch (ServantNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
