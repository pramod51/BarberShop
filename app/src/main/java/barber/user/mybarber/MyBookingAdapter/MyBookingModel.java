package barber.user.mybarber.MyBookingAdapter;

public class MyBookingModel {
    String name,address,phoneNumber,time,date,status,childKey,stylist;

    public MyBookingModel(String address, String phoneNumber, String time, String date, String status, String childKey, String stylist) {

        this.address = address;
        this.phoneNumber = phoneNumber;
        this.time = time;
        this.date = date;
        this.status = status;
        this.childKey = childKey;
        this.stylist = stylist;
    }



    public String getStylist() {
        return stylist;
    }

    public void setStylist(String stylist) {
        this.stylist = stylist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }
}
