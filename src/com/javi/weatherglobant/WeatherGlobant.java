package com.javi.weatherglobant;

import java.net.URL;

public class WeatherGlobant {
	private String endPoint ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22cordoba%2C%20argentina%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		URL url = new URL(endPoint);
		
		
		CurrentConditions condicionesActuales = new CurrentConditions();
		Forecast forecast = new Forecast();
	}

}

class Wind {
	private double chill;
	private int direction;
	private int speed;


	public double getChill() {
		return chill;
	}

	public void setChill(double chill) {
		this.chill = chill;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}

class Atmosphere {
	private double humidity;
	private double pressure;
	private int rising;
	private double visibility;

	public Atmosphere() {

	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public int getRising() {
		return rising;
	}

	public void setRising(int rising) {
		this.rising = rising;
	}

	public double getVisibility() {
		return visibility;
	}

	public void setVisibility(double visibility) {
		this.visibility = visibility;
	}

}

class Astronomy {
	private String sunrise;
	private String sunset;

	public Astronomy() {

	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

}

class CurrentConditions {
	//private Wind viento;
	private double temp;
	private String text;
	private String date;
	
	
	public CurrentConditions() {

	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	protected Wind viento = new Wind();
	protected Atmosphere atmosfera = new Atmosphere();
	protected Astronomy astronomia = new Astronomy();
	
	public void mostrarViento(){
		System.out.println(viento.getChill());
		System.out.println(viento.getDirection());
		System.out.println(viento.getSpeed());
	}
	
	public void mostrarAtmosfera(){
		System.out.println(atmosfera.getHumidity());
		System.out.println(atmosfera.getPressure());
		System.out.println(atmosfera.getRising());
		System.out.println(atmosfera.getVisibility());
	}
	
	public void mostrarAstronomia(){
		System.out.println(astronomia.getSunrise());
		System.out.println(astronomia.getSunset());
	}
	

}

class Forecast {
	private String date, day, text;
	private double high, low;

	public Forecast() {

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

}
