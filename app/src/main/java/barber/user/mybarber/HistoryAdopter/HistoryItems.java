package barber.user.mybarber.HistoryAdopter;

public class HistoryItems {
    String address,date, time, name, phone,status;

    public HistoryItems(String address, String date, String time, String name, String phone, String status) {
        this.address = address;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}