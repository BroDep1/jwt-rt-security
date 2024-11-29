package com.broded.jwtrtsecurity.contoller;

import com.broded.jwtrtsecurity.dto.request.RefreshRequest;
import com.broded.jwtrtsecurity.dto.request.SignInRequest;
import com.broded.jwtrtsecurity.dto.response.JwtRtAuthResponse;
import com.broded.jwtrtsecurity.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign-in")
    @Operation(summary = "Вход пользователя")
    public JwtRtAuthResponse signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для входа", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignInRequest.class),
                            examples = {
                                    @ExampleObject(name = "Вход по GUID", value = "{\"guid\": \"07e76e45-7401-41ab-9c54-4de0c1f79946\"}")
                            }))
            @RequestBody SignInRequest signInRequest, HttpServletRequest httpServletRequest){
        return authService.signIn(signInRequest, httpServletRequest);
    }

    @PostMapping("refresh")
    @Operation(summary = "Рефреш операция")
    public JwtRtAuthResponse refresh(@RequestBody RefreshRequest refreshRequest, HttpServletRequest httpServletRequest){
        return authService.refresh(refreshRequest, httpServletRequest);
    }
}
