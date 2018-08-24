package Reusables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Defaults {
	static int DEFAULT_SALARY = 200;
	static int DEFAULT_CUSTOMER_RIAL_CREDIT = 500000;
	static int DEFAULT_COMPANY_RIAL_CREDIT = 500000;
	static int ZERO = 0;
}

public class DBManager {
	Connection conn;

	private void setEmployeesToDefault() {
		String salary_qurey = "update employee_employee set current_salary=" + String.valueOf(Defaults.DEFAULT_SALARY) + ";";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(salary_qurey);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setManagersToDefault() {
		String query = String.format("update main_wallet_user as mw set rial_credit= %s, dollar_cent_credit= %s, euro_cent_credit= %s where exists(select wallet_user_ptr_id from manager_manager where wallet_user_ptr_id= mw.genuser_ptr_id);",
				String.valueOf(Defaults.DEFAULT_COMPANY_RIAL_CREDIT),
				String.valueOf(Defaults.ZERO), String.valueOf(Defaults.ZERO));
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setCustomersToDefault() {
		String query = String.format("update main_wallet_user as mw set rial_credit= %s, dollar_cent_credit= %s, euro_cent_credit= %s where exists(select wallet_user_ptr_id from customer_customer where wallet_user_ptr_id= mw.genuser_ptr_id);",
				String.valueOf(Defaults.DEFAULT_CUSTOMER_RIAL_CREDIT),
				String.valueOf(Defaults.ZERO), String.valueOf(Defaults.ZERO));
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteManagers() {
		String customers_delete = "delete from manager_manager WHERE genuser_ptr_id=(select user_ptr_id from main_genuser where user_ptr_id=(" +
				"select id from auth_user where username like 'test_%'));";
		String genusers_delete = "delete from main_genuser where user_ptr_id = (select id from auth_user where username like 'test_%');";
		String users_delete = "delete from auth_user where username like 'test_%';";

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(customers_delete);
			stmt.executeUpdate(genusers_delete);
			stmt.executeUpdate(users_delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteEmployees() {
		String customers_delete = "delete from employee_employee WHERE genuser_ptr_id=(select user_ptr_id from main_genuser where user_ptr_id=(select id from auth_user where username like 'test_%'));";
		String genusers_delete = "delete from main_genuser where user_ptr_id in (select id from auth_user where username like 'test_%');";
		String users_delete = "delete from auth_user where username like 'test_%';";

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(customers_delete);
			stmt.executeUpdate(genusers_delete);
			stmt.executeUpdate(users_delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteCustomers() {
		String customers_delete = "delete from customer_customer WHERE genuser_ptr_id=(select user_ptr_id from main_genuser where user_ptr_id=(select id from auth_user where username like 'test_%'));";
		String genusers_delete = "delete from main_genuser where user_ptr_id = (select id from auth_user where username like 'test_%');";
		String users_delete = "delete from auth_user where username like 'test_%';";

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(customers_delete);
			stmt.executeUpdate(genusers_delete);
			stmt.executeUpdate(users_delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection connect() {
		String url = "jdbc:postgresql://localhost/sadproject";
		String user = "sad";
		String password = "sad";
		try {
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return this.conn;
	}

	public void deleteTestUsers() {
		deleteCustomers();
		deleteEmployees();
		deleteManagers();
	}


	public void setUsersToDefault() {
		setCustomersToDefault();
		setManagersToDefault();
		setEmployeesToDefault();
	}


	public void deleteTransactions(){

	}

	public void deleteUser(String username) {
	}

	public void setCustomerToDefault(String username) {
		String query = String.format("update customer_customer as c set rial_credit= %s, dollar_cent_credit=%s, euro_cent_credit=%s  where c.genuser_ptr_id=" +
						"(select id from auth_user where username= '%s');", String.valueOf(Defaults.DEFAULT_CUSTOMER_RIAL_CREDIT),
				String.valueOf(Defaults.ZERO), String.valueOf(Defaults.ZERO), username);
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setManagerToDefault(String username) {
		String query = String.format("update manager_manager as m set rial_credit= %s, dollar_cent_credit=%s, euro_cent_credit=%s  where m.genuser_ptr_id=" +
						"(select id from auth_user where username= '%s');", String.valueOf(Defaults.DEFAULT_COMPANY_RIAL_CREDIT),
				String.valueOf(Defaults.ZERO), String.valueOf(Defaults.ZERO), username);
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setEmployeeToDefault(String username) {
		String query = String.format("update employee_employee as e set current_salary= %s where e.genuser_ptr_id=" +
				"(select id from auth_user where username= '%s');", String.valueOf(Defaults.DEFAULT_SALARY), username);
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBManager manager = new DBManager();
		manager.connect();
		manager.deleteEmployees();
	}

}