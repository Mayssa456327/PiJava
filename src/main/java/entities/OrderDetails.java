package entities;

public class OrderDetails {
    private int id;
    private int myorder_id;
    private String product;
    private int quantity;
    private double price;
    private double total;

    public OrderDetails() {
    }

    public OrderDetails(int myorder_id, String product, int quantity, double price, double total) {
        this.myorder_id = myorder_id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public OrderDetails(int id, int myorder_id, String product, int quantity, double price, double total) {
        this.id = id;
        this.myorder_id = myorder_id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMyorder_id() {
        return myorder_id;
    }

    public void setMyorder_id(int myorder_id) {
        this.myorder_id = myorder_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", myorder_id=" + myorder_id +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}
