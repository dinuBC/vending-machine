import com.vendingmachine.dao.ProductDAO;
import com.vendingmachine.machine.product.ProductEntity;

public class MainForHibernate {

	public static void main(String[] args) {
		ProductDAO productDAO = new ProductDAO();
		
		ProductEntity fantaProduct = new ProductEntity();
		fantaProduct.setName("Ursus");
		fantaProduct.setPrice(4.7);
		
		//productDAO.createProduct(fantaProduct);
		productDAO.deleteProduct(fantaProduct);
	}
}
