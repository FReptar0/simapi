package mx.utez.simapi.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestURL {
    public static void request(int action, String idEnfermera, String nombre) {
        try {

            if (action == 1) {
                // Establecer la URL de la petición GET
                URL url = new URL("http://192.168.1.83:3000/api/send-message/activate/" + idEnfermera + "/" + nombre);

                // Crear una conexión HttpURLConnection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // Establecer el método de la petición como GET
                con.setRequestMethod("GET");

                // Obtener la respuesta del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Imprimir la respuesta
                System.out.println(response.toString());
            } else if (action == 2) {
                // Establecer la URL de la petición GET
                URL url = new URL("http://192.168.1.83:3000/api/send-message/deactivate/" + idEnfermera + "/" + nombre);

                // Crear una conexión HttpURLConnection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // Establecer el método de la petición como GET
                con.setRequestMethod("GET");

                // Obtener la respuesta del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Imprimir la respuesta
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
