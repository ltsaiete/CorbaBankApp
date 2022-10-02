package app;

import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import BankApp.Account;
import BankApp.Bank;
import BankApp.BankHelper;

public class App {
	public static void main(String[] args) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###.## MZN");
		// String[] argv = { "-ORBInitialPort", "900", "-ORBInitialHost",
		// "192.168.1.107" };
		String[] argv = { "-ORBInitialPort", "900", "-ORBInitialHost", "192.168.43.77" };
		ORB orb = ORB.init(argv, null);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		String name = "ABC";
		Bank bank = BankHelper.narrow(ncRef.resolve_str(name));

		Account acc = bank.create();
		byte opt = -1;
		while (opt != 0) {
			opt = Byte.parseByte(JOptionPane.showInputDialog(null, "*****BEM VINDO AO TEU BANCO*****\n"
					+ "1. Criar conta\n" +
					"2. Entrar\n" +
					"0. Sair\n"));
			// Scanner read = new Scanner(System.in);
			switch (opt) {
				case 1:
					String clientName = JOptionPane.showInputDialog(null, "Introduz o nome: ");
					int clientPass = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduza a password"));
					int acountNumber = acc.create(clientName, clientPass);
					JOptionPane.showMessageDialog(null, "Número da conta: "+acountNumber);
					break;

				case 2:
					int number = Integer.parseInt(JOptionPane.showInputDialog(null, "**********Entrar************\n"
							+ "Insira o número de conta: "));
					int pass = Integer.parseInt(JOptionPane.showInputDialog(null, "**********Entrar************\n"
							+ "Insira o PIN: "));

					boolean result = acc.login(number, pass);

					if (result) {
						int opt1 = -1;
						while (opt1 != 0) {
							opt1 = Byte.parseByte(JOptionPane.showInputDialog(null, "1. Depositar um valor\n"
									+ "2. Levantar um valor\n"
									+ "3. Saldo da conta.\n"
									+ "0. Terminar."));

							switch (opt1) {
								case 1:
									float amount = Float.parseFloat(JOptionPane.showInputDialog(null, "Digite o valor"));
									acc.deposit(amount, number);
									JOptionPane.showMessageDialog(null, "Deposito com sucesso");
									break;
								case 2:
									float amountToWithdraw = Float.parseFloat(JOptionPane.showInputDialog(null, "Digite o valor"));
									JOptionPane.showMessageDialog(null, acc.withdraw(amountToWithdraw, number));
									break;

								case 3:
									float balance = acc.balance(number);
									JOptionPane.showMessageDialog(null, "O saldo é: " + df.format(balance));
									break;

								case 0:
									break;
								default:
									JOptionPane.showMessageDialog(null, "Opcao inválida!");
									break;
							}

						}
					} else {
						JOptionPane.showMessageDialog(null, "Verifique os dados e tente novamente");
					}
					break;

				case 0:
					System.exit(0);
					break;

				default:
				JOptionPane.showMessageDialog(null, "Opcao inválida!");
					break;
			}
		}
	}
}
