package com.javi.weatherglobant;

public class WeatherGlobant {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Wind viento = new Wind();
		Atmosphere atmosfera = new Atmosphere();
		Astronomy astronomia = new Astronomy();
		CurrentConditions condicionesActuales = new CurrentConditions();

	}

}

class Wind {
	int chill;
	int direction;
	int speed;

	public Wind() {

	}

	public int getChill() {
		return chill;
	}

	public void setChill(int chill) {
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
	double humidity;
	double pressure;
	int rising;
	double visibility;

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
	String sunrise;
	String sunset;

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
	int temp;
	String text;
	String date;

	public CurrentConditions() {

	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
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

}

class Forecast {
	String date, day, text;
	int high, low;

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

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

}
