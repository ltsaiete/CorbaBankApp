package app;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import BankApp.*;

public class AccountObj extends AccountPOA {

	private ORB orb;
	private float balance;
	private String name;
	private Data data;

	public AccountObj(Data data) {
		this.data = data;

	}

	public void setOrb(ORB orb) {
		this.orb = orb;
	}

	@Override
	public void deposit(float amount, int number) {
		// TODO Auto-generated method stub
		this.balance = this.data.getBalance(number);
		this.balance += amount;

		this.data.update(this.balance, number);
	}

	@Override
	public String withdraw(float amount, int number) {
		this.balance = this.data.getBalance(number);
		if (this.balance < amount) {
			return "Saldo insuficiente";
		}
		this.balance -= amount;
		this.data.update(this.balance, number);
		return "Levantamento com sucesso";
	}

	@Override
	public float balance(int number) {
		// TODO Auto-generated method stub
		this.balance = this.data.getBalance(number);
		return this.balance;
	}

	@Override
	public int create(String name, int pass) {
		// TODO Auto-generated method stub
		int number = this.data.insert(name, pass);
		return number;
	}

	@Override
	public boolean login(int number, int pass) {
		// TODO Auto-generated method stub
		boolean result = false;
		int realPass = this.data.getPass(number);
		if (realPass != 0 && realPass == pass) {
			result = true;
		}
		return result;
	}

}
