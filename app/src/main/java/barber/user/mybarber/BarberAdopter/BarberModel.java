package barber.user.mybarber.BarberAdopter;

public class BarberModel {
    String name,picUrl,childrenKey;
    double rating;
    int selectedPosition;

    public BarberModel(String name, String picUrl, String childrenKey, double rating, int selectedPosition) {
        this.name = name;
        this.picUrl = picUrl;
        this.childrenKey = childrenKey;
        this.rating = rating;
        this.selectedPosition = selectedPosition;
    }

    public String getChildrenKey() {
        return childrenKey;
    }

    public void setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
