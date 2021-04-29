package barber.user.mybarber.TimeSlots;

public class TimeSlotModel {
    String time;
    int selectedPosition;

    public TimeSlotModel(String time, int selectedPosition) {
        this.time = time;
        this.selectedPosition = selectedPosition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
