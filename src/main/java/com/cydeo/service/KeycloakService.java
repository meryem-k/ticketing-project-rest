package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO userDTO);//get user from body and create in keycloak

    void delete(String userName);


}
