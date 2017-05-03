package com.javi.weatherglobant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherGlobant {

	public static void main(String[] args) throws IOException {

		int error = 0;
		float temp=0;
		String texto="";
		float chill=0;
		int direction=0;
		int speed=0;
		String sunrise="";
		String sunset="";
		double humidity=0;
		double pressure=0;
		double visibility=0;

		do {
			Scanner SC = new Scanner(System.in);
			try {
				System.out.println("Ingrese breve descripcion de condiciones. Ej: Nublado. : ");
				texto = SC.next();
				System.out.println("Ingrese temperatura: ");
				temp = SC.nextFloat();
				System.out.println(("Ingrese sensacion termica: "));
				chill = SC.nextFloat();
				System.out.println("Ingrese direccion del viento: ");
				direction = SC.nextInt();
				System.out.println("Ingrese velocidad del viento: ");
				speed = SC.nextInt();
				System.out.println("Ingrese salida del sol: ");
				sunrise = SC.next();
				System.out.println("Ingrese puesta del sol: ");
				sunset = SC.next();
				System.out.println("Ingrese humedad: ");
				humidity = SC.nextDouble();
				System.out.println("Ingrese Presion Atmosferica: ");
				pressure = SC.nextDouble();
				System.out.println("Ingrese visibilidad: ");
				visibility = SC.nextDouble();

			} catch (Exception e) {
				System.out.println("Error: Datos incorrectos.");
				error = 1;
			}
		} while (error == 1);
		CurrentConditions condicionesActuales = new CurrentConditions(

				texto, temp, new Wind(chill, direction, speed), new Astronomy(sunrise, sunset),
				new Atmosphere(humidity, pressure, visibility));

		Forecast pronostico = new Forecast();
		//pronostico.borrarForecast();                   //LIMPIAR TABLA
		pronostico.cargarForecast();
		System.out.println("   CONDICIONES ACTUALES   \n");

		condicionesActuales.mostrarCondicionesActuales();

		System.out.println("\n   CLIMA PROXIMOS DIAS:   \n");

		pronostico.leerForecast();

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
		System.out.println("Temperatura: " + temp);
	}

	private void mostrarViento() {
		System.out.println("Sensacion Termica: " + (viento.getChill()));
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

	private String USR = "root";
	private String PSW = "1234";
	private String URL = "jdbc:mysql://localhost:3306/prueba?&useSSL=false";

	private String date, day, text;
	private double high, low;
	private ArrayList<Forecast> listado = new ArrayList<Forecast>();

	public Forecast() {

	}

	public Forecast(String date, String day, String text, double high, double low) {
		this.date = date;
		this.day = day;
		this.text = text;
		this.low = low;
		this.high = high;
	}

	public void cargarForecast() {
		int error = 0;
		double low=0;
		double high=0;
		
		Scanner sc = new Scanner(System.in);
		try {
			Connection conection = DriverManager.getConnection(URL, USR, PSW);
			PreparedStatement miSentencia = conection
					.prepareStatement("INSERT INTO forecast (date, day, text, high, low) VALUES(?,?,?,?,?)");

			for (int i = 0; i < 2; i++) {

				error = 0;
				System.out.println("  Ingrese siguiente dia de FORECAST  ");
				System.out.println("Ingrese nombre de dia: ");
				String day = sc.next();
				System.out.println("Ingrese fecha: ");
				String date = sc.next();
				System.out.println("Ingrese descripcion: ");
				String text = sc.next();

				do {
					try {
						Scanner scI = new Scanner(System.in);
						System.out.println("Ingrese temperatura Minima: ");
						low = scI.nextDouble();
						error = 0;
						System.out.println("Ingrese temperatura Maxima: ");
						high = scI.nextDouble();
						error = 0;
					} catch (Exception e) {
						System.out.println("Ingrese valores correctos");
						error = 1;
					}
				} while (error == 1);

				miSentencia.setString(1, date);
				miSentencia.setString(2, day);
				miSentencia.setString(3, text);
				miSentencia.setDouble(4, high);
				miSentencia.setDouble(5, low);
				miSentencia.execute();

			}
			miSentencia.close();

		} catch (Exception i) {
			System.out.println(i.getMessage());
		}

	}

	public void borrarForecast(){
		try {
			Connection conection = DriverManager.getConnection(URL,USR,PSW);
			Statement st = conection.createStatement();
			st.execute("DELETE FROM forecast");
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se puede borrar Forecast. Error:" +e.getMessage());		}
	}
	
	
	public void leerForecast() {
		try {
			Connection conection = DriverManager.getConnection(URL, USR, PSW);
			Statement st = conection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM forecast");
			//st.execute("DELETE FROM forecast");
			while (rs.next()) {

				System.out.println("\n" + rs.getString(2) + " " + rs.getString(1));
				System.out.println("Estado: " + rs.getString(3));
				System.out.println("Minima: " + rs.getFloat(5));
				System.out.println("Maxima: " + rs.getFloat(4));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en consulta SQL" + e.getMessage() + e.getSQLState());
			
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
