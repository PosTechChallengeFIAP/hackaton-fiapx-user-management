package com.fiapx.usermanagement.adapters.driver.controllers;

import com.fiapx.usermanagement.core.application.exceptions.UsernameAlreadyInUseException;
import com.fiapx.usermanagement.core.application.exceptions.ValidationException;
import com.fiapx.usermanagement.core.application.message.EMessageType;
import com.fiapx.usermanagement.core.application.message.MessageResponse;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.model.PasswordRequest;
import com.fiapx.usermanagement.core.domain.model.UserRequest;
import com.fiapx.usermanagement.core.domain.services.CreateUserUseCase.ICreateUserUseCase;
import com.fiapx.usermanagement.core.domain.services.UpdatePasswordUseCase.IUpdatePasswordUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "Endpoint to manage users" )
public class UserController {

    @Autowired
    private ICreateUserUseCase createUserUseCase;

    @Autowired
    private IUpdatePasswordUseCase updatePasswordUseCase;


    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    @Operation(summary = "Create User", description = "This endpoint is used to create a new user",
            tags = {"User"},
            responses ={
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = {
                                    @Content(schema = @Schema(implementation = MessageResponse.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity registerUser(@RequestBody UserRequest newUser){
        try {
            User user = createUserUseCase.execute(newUser);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageResponse.type(EMessageType.SUCCESS).withMessage(
                            String.format("User '%s' was created.", user.getUsername())
                    ));
        } catch (ValidationException | DataIntegrityViolationException | UsernameAlreadyInUseException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/user/password")
    @Operation(summary = "Update user password", description = "This endpoint is used to update user password.",
            tags = {"User"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = MessageResponse.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity updatePassword(@RequestBody PasswordRequest newPassword, Authentication authentication){
        try{
            updatePasswordUseCase.execute(newPassword.getPassword(), authentication.getName());

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.type(EMessageType.SUCCESS).withMessage("User password was successfully changed."));
        } catch (ValidationException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }

    }
}

