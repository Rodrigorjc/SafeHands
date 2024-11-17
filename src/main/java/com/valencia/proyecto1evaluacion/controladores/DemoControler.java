package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoControler {


    public DemoControler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<ResponseMessage> sayHello() {
        ResponseMessage response = new ResponseMessage("Hello from secured endpoint");
        return ResponseEntity.ok(response);
    }

    @Setter
    @Getter
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

    }

    private final JwtService jwtService;

    @GetMapping("/remaining-time")
    public long getRemainingTime(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtService.getRemainingTime(token);
    }

}
