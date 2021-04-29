package barber.user.mybarber.Cart;

public class CartItems {
    String imageUrl,title,price;
    String quantity,childrenKey;

    public CartItems(String imageUrl, String title, String price, String quantity, String childrenKey) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.childrenKey = childrenKey;
    }

    public String getChildrenKey() {
        return childrenKey;
    }

    public void setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
