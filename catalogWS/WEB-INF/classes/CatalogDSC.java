/*	Modified on 11-OCT-2016

	Methods provided:

	get(String id)
		- return null id product does not exist
	getAll()
		- return empty list if there are no products
	add(Product product)
		- throw exception if violate validation constraints
		- throw exception if id is not new
	edit(Product product)
		- update price or on sale
		- throw exception if violate validation constraints
		- throw exception if product does not exist
	delete(String id)
		- throw exception if id does not exist
*/

import java.sql.*;
import java.util.*; // ArrayList and List

public class CatalogDSC
{
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

	public void connect() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/CatalogDB";
		String user = "";
		String password = "";
		connection = DriverManager.getConnection(url, user, password);
		statement = connection.createStatement();
	}

	public void disconnect() throws SQLException
	{
		if(preparedStatement != null) preparedStatement.close();
		if(statement != null) statement.close();
		if(connection != null) connection.close();
	}

	// Retrieve a Product by id
	// Returns null if the product does not exist
	public Product get(String id) throws SQLException
	{
		connect();

		String queryString = "select * from Product where id = ?";
		preparedStatement = connection.prepareStatement(queryString);
		preparedStatement.setString(1, id);
		ResultSet rs = preparedStatement.executeQuery();

		Product product = null;

		if(rs.next())	// Product exists
		{
			// String id = rs.getString(1);
			String name = rs.getString(2);
			double price = rs.getDouble(3);
			boolean onSale = rs.getBoolean(4);
			product = new Product(id, name, price, onSale);
		}

		return product;

	}	// end of get (one)


	//	Retrieve all products
	// Return an empty list if there are no products
	public List<Product> getAll()
	throws SQLException
	{
		connect();

		String queryString = "select * from product";

		preparedStatement = connection.prepareStatement(queryString);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Product> products = new ArrayList<Product>();

		while (rs.next())
		{
			String id = rs.getString(1);
			String name = rs.getString(2);
			double price = rs.getDouble(3);
			boolean onSale = rs.getBoolean(4);

			products.add(new Product(id, name, price, onSale));
		}

		return products;
	}

	// add a product
	public void add(Product product) throws Exception
	{
		// pre: id is new
		boolean pre = get(product.getId()) == null;
		if(!pre)
		{
			String msg = "ERROR: " + product.getId() + " is not new!";
			throw new Exception(msg);
		}

		// post: add product
		connect();

		String insertString = "insert into Product values(?, ?, ?, ?)";
		preparedStatement = connection.prepareStatement(insertString);
		preparedStatement.setString(1, product.getId());
		preparedStatement.setString(2, product.getName());
		preparedStatement.setDouble(3, product.getPrice());
		preparedStatement.setBoolean(4, product.isOnSale());
		preparedStatement.executeUpdate();

		// release resource
		disconnect();
	}

	// edit a product
	public void edit(Product product) throws SQLException, Exception
	{
		// pre: product must exist
		boolean pre = get(product.getId()) != null;
		if(!pre)
		{
			String msg = "ERROR: Product with id " + product.getId() + " does not exist!";
			throw new Exception(msg);
		}

		// post: update price or on sale or both
		connect();

		String updateString =
			"update Product " +
			"set price = ?, " +
			"onSale = ? " +
			"where id = ? ";

		preparedStatement = connection.prepareStatement(updateString);
		preparedStatement.setDouble(1, product.getPrice());
		preparedStatement.setBoolean(2, product.isOnSale());
		preparedStatement.setString(3, product.getId());

		preparedStatement.executeUpdate();
		disconnect();
	}

	//	Delete a product
	public void delete(String id) throws Exception
	{
		// pre: product must exist
		Product product = get(id);
		boolean pre = (product != null);
		if(! pre)
		{
			String msg= "ERROR: Product with id " + id + " does not exist!";
			throw new Exception(msg);
		}

		// post: delete product
		connect();
		String deleteStatement =
			"delete from product " +
			"where id = ? ";
		preparedStatement = connection.prepareStatement(deleteStatement);
		preparedStatement.setString(1, id);
		preparedStatement.executeUpdate();

		disconnect();
	}



	/***************************************************************
								Perform Some Quick Tests
	***************************************************************/
	public static void main(String [] args) throws Exception
	{
		CatalogDSC dsc = new CatalogDSC();

		// refresh the database first

		// Product product =  dsc.get("p10");
		// System.out.println("product: " + product);

		// List<Product> products = dsc.getAll();
		// System.out.println("products: " + products);

		// dsc.add(new Product("p100", "Plasma TV", 100.50, true));

		// Name is too short
		dsc.add(new Product("p100", "TTT", 10, true));

		// dsc.edit(new Product("p100", "whatever", 222.22, false));

		// p200 does not exist
		// dsc.edit(new Product("p200", "whatever", 222.22, false));

		// dsc.delete("p100");
	}
}
