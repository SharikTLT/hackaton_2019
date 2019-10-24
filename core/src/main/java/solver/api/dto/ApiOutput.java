package solver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;


public class ApiOutput {

    @Getter
    @Setter
    @JsonProperty("goto")
    Long goTo;

    @Getter
    @Setter
    String car;

    @Getter
    @Setter
    Boolean nomoney;

    @Getter
    @Setter
    String token;

    public ApiOutput() {
    }

    public ApiOutput(Long point, String car) {
        this.goTo = point;
        this.car = car;
    }

    public ApiOutput(Long goTo, String car, Boolean nomoney) {
        this.goTo = goTo;
        this.car = car;
        this.nomoney = nomoney;
    }

    public ApiOutput(String token){
        this.token = token;
    }

    public static ApiOutput goTo(Long point, String car){
        return new ApiOutput(point, car);
    }

    public static ApiOutput goTo(Long point, String car, Boolean nomoney){
        return new ApiOutput(point, car, nomoney);
    }

    public static ApiOutput reconnect(String token){
        return new ApiOutput(token);
    }
}