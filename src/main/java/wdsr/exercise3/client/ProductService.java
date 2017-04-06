package wdsr.exercise3.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import wdsr.exercise3.model.Product;
import wdsr.exercise3.model.ProductType;

public class ProductService extends RestClientBase {
	protected ProductService(final String serverHost, final int serverPort, final Client client) {
		super(serverHost, serverPort, client);
	}
	
	/**
	 * Looks up all products of given types known to the server.
	 * @param types Set of types to be looked up
	 * @return A list of found products - possibly empty, never null.
	 */
	public List<Product> retrieveProducts(Set<ProductType> types) {
        GenericType<List<Product>> listGenericType = new GenericType<List<Product>>() {};
        List<Product> productList = baseTarget.path("/products")
                .request(MediaType.APPLICATION_JSON)
                .get(listGenericType);
        List<Product> productsListByType = new ArrayList<>(20);
        for(Product p : productList) {
			if(types.contains(p.getType())) {
				productsListByType.add(p);
			}
		}
		return productsListByType;
	}
	
	/**
	 * Looks up all products known to the server.
	 * @return A list of all products - possibly empty, never null.
	 */
	public List<Product> retrieveAllProducts() {
        GenericType<List<Product>> listGenericType = new GenericType<List<Product>>() {};
        List<Product> productList = baseTarget.path("/products")
                .request(MediaType.APPLICATION_JSON)
                .get(listGenericType);
        return productList;
	}
	
	/**
	 * Looks up the product for given ID on the server.
	 * @param id Product ID assigned by the server
	 * @return Product if found
	 * @throws NotFoundException if no product found for the given ID.
	 */
	public Product retrieveProduct(int id) throws NotFoundException {
		Product product = baseTarget.path("/products/{id}").resolveTemplate("id", id)
				.request(MediaType.APPLICATION_JSON)
				.get(Product.class);
		return product;
	}	
	
	/**
	 * Creates a new product on the server.
	 * @param product Product to be created. Must have null ID field.
	 * @return ID of the new product.
	 * @throws WebApplicationException if request to the server failed
	 */
	public int storeNewProduct(Product product) throws WebApplicationException {
		//dopytac o ta metode
/*		GenericType<List<Product>> listGenericType = new GenericType<List<Product>>() {};
		List<Product> productList = baseTarget.path("/products")
				.request(MediaType.APPLICATION_JSON)
				.get(listGenericType);
		int id = productList.size()+1;
		if(product.getId() != null) {
			return -1;
		}
		product.setId(id);
		baseTarget.path("/products/").request().post(Entity.json(product));
		return id;*/
		return 0;

	}
	
	/**
	 * Updates the given product.
	 * @param product Product with updated values. Its ID must identify an existing resource.
	 * @throws NotFoundException if no product found for the given ID.
	 */
	public void updateProduct(Product product) throws NotFoundException {
		baseTarget.path("/products/{id}").resolveTemplate("id", product.getId()).request().put(Entity.json(product));
	}

	
	/**
	 * Deletes the given product.
	 * @param product Product to be deleted. Its ID must identify an existing resource.
	 * @throws NotFoundException if no product found for the given ID.
	 */
	public void deleteProduct(Product product) throws NotFoundException {
		baseTarget.path("/products/{id}").resolveTemplate("id", product.getId()).request().delete();
	}
}
