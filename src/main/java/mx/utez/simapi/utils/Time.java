package mx.utez.simapi.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import mx.utez.simapi.models.Tiempo;

public class Time {
    public static Tiempo getTime() {
        Tiempo tiempo = new Tiempo();

        // Establecer la zona horaria de México
        ZoneId mexicoTimeZone = ZoneId.of("America/Mexico_City");
        ZonedDateTime mexicoTime = ZonedDateTime.now(mexicoTimeZone).minusHours(1);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = mexicoTime.format(timeFormatter);
        tiempo.setHora(time);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = mexicoTime.format(dateFormatter);
        tiempo.setFecha(date);

        return tiempo;
    }
}
