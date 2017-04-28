package com.javi.weatherglobant;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherGlobant {
	public static String urlString = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22cordoba%2C%20argentina%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

	public static void main(String[] args) throws Exception {

		try {

			climaActual hoy = new climaActual();
			localidad localidad = new localidad();
			traductor traductor = new traductor();
//			float temperatura;

			// TODO Auto-generated method stub

			URL url = new URL(urlString);
			Scanner lector = new Scanner(url.openStream());
			String JSON = new String();

			while (lector.hasNext()) {
				JSON += lector.nextLine();

			}
			JSONObject objeto = new JSONObject(JSON);

			localidad.setCiudad(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("location").getString("city"));
			localidad.setPais(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("location").getString("country"));
			// localidad.setRegion(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("region"));

			hoy.setSensacionTemperatura(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("wind").getInt("chill"));
			hoy.setDireccion(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("wind").getString("direction"));
			hoy.setVelocidad(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("wind").getString("speed"));
			hoy.setTemperatura(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getJSONObject("condition").getInt("temp"));
			hoy.setFecha(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getString("pubDate"));
			hoy.setTexto(objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getJSONObject("condition").getString("text"));

			System.out.println("CLIMA ACTUAL PARA: " + localidad.getCiudad() + ", " + localidad.getPais());


			System.out.println("Fecha Actualizacion: " + hoy.getFecha());
			System.out.println("Temperatura: " + hoy.cambiarTemperatura(hoy.getTemperatura())+ " *");
			System.out.println(traductor.traduceCondiciones(hoy.getTexto()));
//			System.out.println(hoy.getTexto());

			System.out.println("\n*** CLIMA PROXIMOS DIAS ****\n");
			ArrayList<JSONObject> listaForecast = new ArrayList<JSONObject>();

			JSONObject forecast = new JSONObject();
			for (int i = 1; i < 6; i++) {
				forecast = objeto.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
						.getJSONObject("item").getJSONArray("forecast").getJSONObject(i);
				listaForecast.add(forecast);
			}

			for (int i = 0; i < listaForecast.size(); i++) {
				System.out.println("Fecha: " + listaForecast.get(i).getString("day") + " "
						+ listaForecast.get(i).getString("date"));
				System.out.println("Temp. Minima: " + hoy.cambiarTemperatura(listaForecast.get(i).getInt("low"))+" *");
				System.out.println("Temp. Maxima: " + hoy.cambiarTemperatura(listaForecast.get(i).getInt("high"))+" *");

//				System.out.println(listaForecast.get(i).getString("text"));
								System.out.println("Condiciones: " + traductor.traduceCondiciones(listaForecast.get(i).getString("text")));
				System.out.println("**********************");
			}
		} catch (Exception e) {
			System.out.println("No es posible conectar al WS. Verifique Conexion");
		}

	}

}

class localidad {
	public localidad() {

	}

	String ciudad;
	String pais;
	String region;

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRegion() {
		return "Region: " + region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}

class climaActual {

	public climaActual() {

	}

	String texto;

	String velocidad;
	String direccion;
	int sensacionTemperatura;
	int temperatura;
	String fecha;

	String humedad;
	String presion;
	String rising;
	String visibilidad;
	String amanecer;
	String anochecer;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public void setSensacionTemperatura(int chill) {
		this.sensacionTemperatura = chill;
		cambiarTemperatura();
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;

	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	public String getSensacionTemperatura() {

		return "Sensacion termica:" + sensacionTemperatura;
	}

	public String getDireccion() {
		return "Direccion de viento: " + direccion;
	}

	public String getVelocidad() {
		return "Velocidad del viento: " + velocidad;
	}

	public void cambiarTemperatura() {
		sensacionTemperatura = ((sensacionTemperatura - 32) * 5) / 9;

	}

	public int cambiarTemperatura(int temperatura) {
		temperatura = ((temperatura - 32) * 5) / 9;
		return temperatura;
	}
}

class clima {
	ArrayList<JSONObject> listaForecast = new ArrayList<JSONObject>();

	public clima(ArrayList<JSONObject> listaForecast) {
		this.listaForecast = listaForecast;

	}

}

class traductor {
	private String texto;

	public traductor() {

	}

	public String traduceCondiciones(String condiciones) {

		if (condiciones.equals("Scattered Thunderstorms")) {
			texto = "Tormentas Electricas Dispersas";
		} else if (condiciones.equals("Sunny")) {
			texto = "Soleado";
		} else if (condiciones.equals("Rain")) {
			texto = "Luvioso";
		}

		else if (condiciones.equals("Cloudy")) {
			texto = "Nublado";
		}
		
		else if(condiciones.equals("Showers")){
			texto = "Chaparrones";
		}
		else if(condiciones.equals("Mostly Sunny")){
			texto = "Mayormente Soleado";
		}

		else {
			texto = "Estado no contemplado";
		}

		return texto;
	}

}
