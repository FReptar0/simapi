package mx.utez.simapi.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Horario {
    private List<String> matutino;
    private List<String> vespertino;
    private List<String> nocturno;
}
