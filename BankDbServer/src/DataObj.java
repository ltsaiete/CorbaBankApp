import java.sql.*;

import org.omg.CORBA.ORB;

import BankApp.Account;
import BankApp.DataPOA;

class DataObj extends DataPOA {
	private ORB orb;
	private PreparedStatement stmt;
	private Connection con;
	private ResultSet rs;

	public DataObj() {

	}

	private void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bank", "root", "");
			System.out.println("Connection established successfully!");
			// ResultSet rs = (ResultSet) stmt.executeQuery("select * from accounts");

			// System.out.println(rs);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void setOrb(ORB orb) {
		this.orb = orb;
	}

	@Override
	public int insert(String name, int pass) {
		// TODO Auto-generated method stub
		System.out.println(name + "/n" + pass);
		this.init();
		int number = 0;
		try {
			stmt = con.prepareStatement(
					"INSERT INTO accounts(name, pass, balance) values(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setInt(2, pass);
			stmt.setFloat(3, 0);

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					number = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			stmt.close();

		} catch (SQLException e) {
			// TODO: handle exception
		}

		return number;
	}

	@Override
	public Account read(int number) {
		// TODO Auto-generated method stub
		init();
		// try {
		String sql = "SELECT * FROM accounts where number = ?";

		// stmt = con.prepareStatement(sql);
		// stmt.setInt(1, number);
		// ResultSet result = stmt.executeQuery();
		// int pass = (Integer) null;

		// while (result.next()) {
		// String name = result.getString(2);
		// pass = result.getInt("pass");

		// System.out.println("11" + pass);
		// }

		// System.out.println("PASSSS" + pass);

		// stmt.close();

		// } catch (Exception e) {
		// // TODO: handle exception
		// System.out.println(e.getMessage());
		// }
		// Statement statement = conn.createStatement();

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, number);
			ResultSet result = stmt.executeQuery();

			int count = 0;

			while (result.next()) {
				String name = result.getString(2);
				String pass = result.getString(3);

				String output = "User #%d: %s - %s";
				System.out.println(String.format(output, ++count, name, pass));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public void update(float balance, int number) {
		// TODO Auto-generated method stub
		try {
			String sql = "UPDATE accounts SET balance=? WHERE number=?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, balance);
			stmt.setInt(2, number);

			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Balance updated successfully");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public int getPass(int number) {
		// TODO Auto-generated method stub
		init();
		String sql = "SELECT * FROM accounts where number = ?";
		int pass = 0;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, number);
			ResultSet result = stmt.executeQuery();

			int count = 0;

			while (result.next()) {
				String name = result.getString(2);
				pass = result.getInt(3);

				String output = "User #%d: %s - %s";
				System.out.println(String.format(output, ++count, name, pass));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return pass;
	}

	@Override
	public float getBalance(int number) {
		// TODO Auto-generated method stub
		init();
		String sql = "SELECT * FROM accounts where number = ?";
		float balance = -1;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, number);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				String name = result.getString(2);
				balance = result.getFloat(4);

				String output = "User: %s - %s";
				System.out.println(String.format(output, name, balance));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return balance;
	}

}
