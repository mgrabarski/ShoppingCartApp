package grabarski.shoppingcartapp.data.model;

/**
 * Created by Mateusz Grabarski on 23/08/2017.
 */

public class LineItem extends Product {

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSumPrice() {
        return getSalePrice() * quantity;
    }
}
