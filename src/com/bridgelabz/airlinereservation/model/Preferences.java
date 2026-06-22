package com.bridgelabz.airlinereservation.model;

public class Preferences {
    private String mealType;
    private String seatPreference;
    private String specialAssistance;
    private boolean emailNotifications;
    private boolean smsNotifications;

    public Preferences() {
        this.emailNotifications = true;
        this.smsNotifications = true;
    }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public String getSeatPreference() { return seatPreference; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }
    public String getSpecialAssistance() { return specialAssistance; }
    public void setSpecialAssistance(String specialAssistance) { this.specialAssistance = specialAssistance; }
    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
    public boolean isSmsNotifications() { return smsNotifications; }
    public void setSmsNotifications(boolean smsNotifications) { this.smsNotifications = smsNotifications; }

    @Override
    public String toString() {
        return "Preferences{" +
                "mealType='" + mealType + '\'' +
                ", seatPreference='" + seatPreference + '\'' +
                ", specialAssistance='" + specialAssistance + '\'' +
                ", emailNotifications=" + emailNotifications +
                ", smsNotifications=" + smsNotifications +
                '}';
    }
}
