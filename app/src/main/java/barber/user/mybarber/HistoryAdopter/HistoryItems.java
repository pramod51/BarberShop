package barber.user.mybarber.HistoryAdopter;

public class HistoryItems {
    String id, date, time, description, shopName, shopPhone;
    boolean accepted, declined;

    public HistoryItems(String id, String date, String time, String description, String shopName, String shopPhone, boolean accepted, boolean declined) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.shopName = shopName;
        this.shopPhone = shopPhone;
        this.accepted = accepted;
        this.declined = declined;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }
}