package br.com.joaoszczypior.spring_boot_essentials.web;

import br.com.joaoszczypior.spring_boot_essentials.dtos.LoginRequestDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.RegisterRequestDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.TokenResponseDto;
import br.com.joaoszczypior.spring_boot_essentials.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public void register (@Valid @RequestBody RegisterRequestDto registerRequestDto) throws Exception{
        authenticationService.register(registerRequestDto);
    }

    @PostMapping(value = "/login")
    public TokenResponseDto login (@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return authenticationService.login(loginRequestDto);
    }
}
