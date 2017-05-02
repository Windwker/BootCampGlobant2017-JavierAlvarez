package com.javi.weatherglobant;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherGlobant {
	private static String endPoint = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22cordoba%2C%20argentina%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		URL url = new URL(endPoint);
		Scanner sc = new Scanner(url.openStream());
		String JSON = new String();

		while (sc.hasNext()) {
			JSON += sc.next();
		}

		JSONObject objeto = new JSONObject(JSON);
		CurrentConditions condicionesActuales = new CurrentConditions(

				objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item")
						.getJSONObject("condition").getString("text"),
				(float) objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
						.getJSONObject("item").getJSONObject("condition").getDouble("temp"),
				new Wind(
						(float) objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("wind").getDouble("chill"),
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("wind").getInt("direction"),
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("wind").getInt("speed")),
				new Astronomy(
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("astronomy").getString("sunrise"),
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("astronomy").getString("sunset")),
				new Atmosphere(
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("atmosphere").getDouble("humidity"),
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("atmosphere").getDouble("pressure"),
						objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
								.getJSONObject("atmosphere").getDouble("visibility")));

		Forecast forecast = new Forecast(((objeto.getJSONObject("query").getJSONObject("results")
				.getJSONObject("channel").getJSONObject("item").getJSONArray("forecast"))));

		System.out.println("***CONDICIONES ACTUALES***\n");
		condicionesActuales.mostrarCondicionesActuales();
		System.out.println("\n***CLIMA PROXIMOS DIAS:***\n");
		forecast.leerForecast();

	}

}

class Wind {
	private float chill;
	private int direction;
	private int speed;

	public Wind(float chill, int direction, int speed) {
		this.chill = chill;
		this.direction = direction;
		this.speed = speed;

	}

	public float getChill() {
		return chill;
	}

	public void setChill(float chill) {
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
	private double visibility;

	public Atmosphere(double humidity, double pressure, double visibility) {
		this.humidity = humidity;
		this.pressure = pressure;

		this.visibility = visibility;

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

	public Astronomy(String sunrise, String sunset) {
		this.sunrise = sunrise;
		this.sunset = sunset;

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
	private Wind viento;
	private Astronomy astronomia;
	private Atmosphere atmosfera;
	private float temp;
	private String text;
	private String date;

	public CurrentConditions(String text, float temp, Wind viento, Astronomy astronomia, Atmosphere atmosfera) {
		this.text = text;
		this.temp = temp;
		this.viento = viento;
		this.astronomia = astronomia;
		this.atmosfera = atmosfera;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
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

	private void mostrarText() {
		System.out.println(Auxiliares.traduceCondiciones(text));
	}

	private void mostrarTemp() {
		System.out.println("Temperatura: " + Auxiliares.cambiarTemperatura(temp));
	}

	private void mostrarViento() {
		System.out.println("Sensacion Termica: " + Auxiliares.cambiarTemperatura(viento.getChill()));
		System.out.println("Direccion de viento: " + viento.getDirection());
		System.out.println("Velocidad de viento: " + viento.getSpeed());
	}

	private void mostrarAtmosfera() {
		System.out.println("Humedad: " + atmosfera.getHumidity());
		System.out.println("Presion Atmosferica: " + atmosfera.getPressure());
		System.out.println("Visibilidad: " + atmosfera.getVisibility());
	}

	private void mostrarAstronomia() {
		System.out.println("Salida del sol: " + astronomia.getSunrise());
		System.out.println("Puesta del sol: " + astronomia.getSunset());
	}

	public void mostrarCondicionesActuales() {
		mostrarText();
		mostrarTemp();
		mostrarViento();
		mostrarAtmosfera();
		mostrarAstronomia();
	}

}

class Forecast {
	private String date, day, text;
	private double high, low;
	JSONArray forecast;

	public Forecast(JSONArray forecast) {
		this.forecast = forecast;
	}

	public void leerForecast() {

		for (int i = 1; i < 6; i++) {
			System.out.println("\n" + forecast.getJSONObject(i).getString("day") + " "
					+ forecast.getJSONObject(i).getString("date"));
			System.out.println(
					"Minima: " + Auxiliares.cambiarTemperatura((float) forecast.getJSONObject(i).getDouble("low")));
			System.out.println(
					"Maxima: " + Auxiliares.cambiarTemperatura((float) forecast.getJSONObject(i).getDouble("high")));
			System.out.println("Estado: " + Auxiliares.traduceCondiciones(forecast.getJSONObject(i).getString("text")));

		}

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

class Auxiliares {
	static String text;

	public static float cambiarTemperatura(float temperatura) {
		temperatura = ((temperatura - 32) * 5) / 9;

		return Math.round(temperatura);
	}

	public static String traduceCondiciones(String condiciones) {

		if (condiciones.equals("ScatteredThunderstorms")) {
			text = "Tormentas Electricas Dispersas";
		} else if (condiciones.equals("Sunny")) {
			text = "Soleado";
		} else if (condiciones.equals("Rain")) {
			text = "Luvioso";
		}

		else if (condiciones.equals("Cloudy")) {
			text = "Nublado";
		}

		else if (condiciones.equals("Showers")) {
			text = "Chaparrones";
		} else if (condiciones.equals("MostlySunny")) {
			text = "Mayormente Soleado";
		}

		else if (condiciones.equals("Thunderstorms")) {
			text = "Tormentas Electricas";
		}

		else if (condiciones.equals("PartlyCloudy")) {
			text = "Parcialmente Nublado";
		} else if (condiciones.equals("MostlyCloudy")) {
			text = "Mayormente Nublado";
		}

		else {
			text = condiciones;
		}

		return text;
	}

}
