package mx.utez.simapi.utils;

import lombok.Data;

@Data
public class CustomResponse<T> {
    T data;
    boolean error;
    int statusCode;
    String message;
}
