public class Product
{
	public final int MISSING_PRICE = -1;


	private String id;
	private String name;
	private double price;
	private boolean onSale;

	public Product(String id, String name, double price, boolean onSale)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.onSale = onSale;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public double getPrice()
	{
		return price;
	}

	public boolean isOnSale()
	{
		return onSale;
	}

	public String toString()
	{
		return "Product[id: " + id
			+ ", name: " + name
			+ ", price: " + price
			+ ", onSale: " + onSale + "]";
	}

	//	To support building GUI
	// Specificall, to build the table model
	public static Object[] getColumnNames()
	{
		return new Object[]{"id", "name", "price", "onSale"};
	}

	// To perform some quick tests
	public static void main(String [] args) throws Exception
	{
		Product p = new Product("P10", "T", 0.00, true);
		System.out.println(p);
	}
}