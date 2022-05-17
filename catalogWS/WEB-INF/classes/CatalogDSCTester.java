import java.sql.*;
import java.util.*; // ArrayList and List

public class CatalogDSCTester
{

	/*
		Perform Some Quick Tests
	*/
	public static void main(String [] args) throws Exception
	{
		CatalogDSC dsc = new CatalogDSC();

		// First refresh the database
		// Then open MySQL to observe the results

		// Test get:
		Product product =  dsc.get("p10");
		System.out.println("product: " + product);

		// Test getAll:
		// List<Product> products = dsc.getAll();
		// System.out.println("products: " + products);

		// Test add:
		// dsc.add(new Product("p100", "Plasma TV", 100.50, true));

		// Test edit:
		// dsc.edit(new Product("p100", "whatever", 222.22, false));

		// p200 does not exist
		// dsc.edit(new Product("p200", "whatever", 222.22, false));

		// Test delete:
		// dsc.delete("p100");
	}
}
