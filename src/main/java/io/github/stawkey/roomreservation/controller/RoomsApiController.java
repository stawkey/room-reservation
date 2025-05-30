package io.github.stawkey.roomreservation.controller;


import io.github.stawkey.roomreservation.api.RoomsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.roomReservation.base-path:/api}")
public class RoomsApiController implements RoomsApi {

    private final NativeWebRequest request;

    @Autowired
    public RoomsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
