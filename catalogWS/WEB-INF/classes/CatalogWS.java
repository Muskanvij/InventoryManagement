//	This class implements RESTful web services, using servlets, to provide access
// to the Catalog data

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.google.gson.Gson;

public class CatalogWS extends HttpServlet
{
	CatalogDSC catalogDSC = new CatalogDSC();

	//	To get one product (by id) or all products
	//
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			//	Optional: Get request content type if we want to check
			String requestContentType = request.getContentType();
			System.out.println(">>> request content type: " + requestContentType);

			// Optional: Get request method if we want to check
			String requestMethod = request.getMethod();
			System.out.println(">>> request method: " + requestMethod);

			// Get id
			String id = request.getParameter("id");
			System.out.println(">>> id: " + id);


			//	Get method is used to find one product or all products
			if (id != null)	// to find one product
			{
				Product product = catalogDSC.get(id);

				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print( Helper.getJSON(product));
			}
			else
			//	Get method to get all products
			{
				List<Product> products = catalogDSC.getAll();

				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(Helper.getJSONList(products, Product.class));
			}

		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	//	To add a product
	//
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			//	Get request content type
			String requestContentType = request.getContentType();
			System.out.println(">>> request content type: " + requestContentType);

			// Get request method
			String requestMethod = request.getMethod();
			System.out.println(">>> request method: " + requestMethod);

			// Get id
			String id = request.getParameter("id");
			System.out.println(">>> id: " + id);

			// Get data
			BufferedReader in = request.getReader();
			StringBuffer dataSB = new StringBuffer();
			String line = in.readLine();
			while(line != null)
			{
				dataSB.append(line).append("\n");
				line = in.readLine();
			}
			String data = dataSB.toString();
			System.out.println(">>> data:\n*" + data +"*");

			// Convert Json to Product
			Product product = Helper.getObject(data, Product.class);

			catalogDSC.add(product);

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(data);
		}
		catch(Exception e)
		{
			response.sendError(800, e.getMessage());
		}
	} // end add

	//	To upadate a product's price on on sale status
	//
	public void doPut(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			//	Get request content type
			String requestContentType = request.getContentType();
			System.out.println(">>> request content type: " + requestContentType);

			// Get request method
			String requestMethod = request.getMethod();
			System.out.println(">>> request method: " + requestMethod);

			// Get id
			String id = request.getParameter("id");
			System.out.println(">>> id: " + id);

			// Get data
			BufferedReader in = request.getReader();
			StringBuffer dataSB = new StringBuffer();
			String line = in.readLine();
			while(line != null)
			{
				dataSB.append(line).append("\n");
				line = in.readLine();
			}
			String data = dataSB.toString();
			System.out.println(">>> data:\n*" + data +"*");

			// Convert Json to Product
			Product product = Helper.getObject(data, Product.class);

			catalogDSC.edit(product);

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(data);
		}
		catch(Exception e)
		{
			response.sendError(800, e.getMessage());
		}
	}// doPut


	// To delete a product
	//
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			//	Get request content type
			String requestContentType = request.getContentType();
			System.out.println(">>> request content type: " + requestContentType);

			// Get request method
			String requestMethod = request.getMethod();
			System.out.println(">>> request method: " + requestMethod);

			// Get id
			String id = request.getParameter("id");
			System.out.println(">>> id: " + id);

			//	This doDelete method is called when we have a DELETE request
			// Hence, we go ahead with the request and remove the product
			catalogDSC.delete(id);

			// We do not send back any data

		}
		catch(Exception e)
		{
			response.sendError(800, e.getMessage());
		}
	}
}

