package com.example.dangn.gps;

public class Weather {
    private double mainTemp; // nhiệt độ (do k) k = c + 273,15
    private double mainTempMax;
    private double mainTempMin;
    private double mainPressure; //áp xuất
    private double mainHumidity;//độ ẩm
    private double windSpeed; //tốc độ gió
    private double windDeg; // hướng gió
    private String weatherIcon; // icon trong list thoi tiet
    private String weatherMain;// trạng thái thời tiết

    public Weather() {
    }

    public Weather(double mainTemp, double mainTempMax, double mainTempMin, double mainPressure, double mainHumidity, double windSpeed, double windDeg, String weatherIcon, String weatherMain) {
        this.mainTemp = mainTemp;
        this.mainTempMax = mainTempMax;
        this.mainTempMin = mainTempMin;
        this.mainPressure = mainPressure;
        this.mainHumidity = mainHumidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.weatherIcon = weatherIcon;
        this.weatherMain = weatherMain;
    }

    public double getMainTemp() {
        return (mainTemp  -273.15);
    }

    public void setMainTemp(double mainTemp) {
        this.mainTemp = mainTemp;
    }

    public double getMainTempMax() {
        return (mainTempMax  -273.15);
    }

    public void setMainTempMax(double mainTempMax) {
        this.mainTempMax = mainTempMax;
    }

    public double getMainTempMin() {
        return (mainTempMin  -273.15);
    }

    public void setMainTempMin(double mainTempMin) {
        this.mainTempMin = mainTempMin;
    }

    public double getMainPressure() {
        return mainPressure;
    }

    public void setMainPressure(double mainPressure) {
        this.mainPressure = mainPressure;
    }

    public double getMainHumidity() {
        return mainHumidity;
    }

    public void setMainHumidity(double mainHumidity) {
        this.mainHumidity = mainHumidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
}
