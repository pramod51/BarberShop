package barber.user.mybarber.BookingAdopter;

public class BookingModel {
    String address,childrenKey,Name;
    int selectedPosition;

    public BookingModel(String address, String childrenKey, String name, int selectedPosition) {
        this.address = address;
        this.childrenKey = childrenKey;
        Name = name;
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChildrenKey() {
        return childrenKey;
    }

    public void setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
