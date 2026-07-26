package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.config.TokenProvider;
import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.RolesEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IRolesRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.LoginRequestDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.RegisterRequestDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.TokenResponseDto;
import br.com.joaoszczypior.spring_boot_essentials.enums.RoleTypeEnum;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IAlunosRepository alunosRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public void register(RegisterRequestDto dto) throws BadRequestException {
        AlunosEntity alu =  alunosRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if(alu != null) {
            throw new BadRequestException("E-mail já cadastrado no sistema!");
        }

        RolesEntity role = rolesRepository.findByNome(RoleTypeEnum.ROLE_ALUNO.name())
                .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                                .nome(RoleTypeEnum.ROLE_ALUNO.name())
                        .build()));

        AlunosEntity aluno = AlunosEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .roles(Set.of(role))
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        alunosRepository.save(aluno);
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) throws BadRequestException {
        try {
            // Aqui a implementação parece ser simples pois utiliza uma linha apenas de Código para efetuar o Login
            // Mas ocorre esse fluxo por de baixo dos Panos:
            // authentication provider -> userDetailServiceImpl -> passwordEnconder.matches() -> autenticado
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
            String token = tokenProvider.gerarToken(authentication);
            return new TokenResponseDto(token, expirationTime);

        } catch (BadCredentialsException e) {
            throw new BadRequestException("Credencias inválidas");
        } catch (Exception e) {
            throw e;
        }
    }
}
