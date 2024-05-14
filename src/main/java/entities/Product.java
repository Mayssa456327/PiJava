package entities;


import services.ServiceCategory;

public class Product {
    private int id;
    private int category_id;
    private String name;
    private String slug;
    private String illustration;
    private String subtitle;
    private String description;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int category_id, String name, String slug, String illustration, String subtitle, String description, double price, int quantity) {
        this.category_id = category_id;
        this.name = name;
        this.slug = slug;
        this.illustration = illustration;
        this.subtitle = subtitle;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int id, int category_id, String name, String slug, String illustration, String subtitle, String description, double price, int quantity) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.slug = slug;
        this.illustration = illustration;
        this.subtitle = subtitle;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    ServiceCategory serviceCategory=new ServiceCategory();
    @Override
    public String toString() {
        return
                "Category=" + serviceCategory.getById(category_id).getName() +
                " Name='" + name + '\'' +
                " Slug='" + slug + '\'' +
                " subtitle='" + subtitle + '\'' +
                " description='" + description + '\'' +
                " price=" + price +
                " quantity=" + quantity +
                 "\n";
    }
}
