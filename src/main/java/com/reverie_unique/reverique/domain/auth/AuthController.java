package com.reverie_unique.reverique.domain.auth;

import com.reverie_unique.reverique.domain.auth.dto.LoginDTO;
import com.reverie_unique.reverique.domain.auth.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){this.authService = authService;}

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenDTO request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
}