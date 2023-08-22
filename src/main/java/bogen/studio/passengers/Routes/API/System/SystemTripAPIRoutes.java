package bogen.studio.passengers.Routes.API.System;

import bogen.studio.passengers.Service.PassengerService;
import bogen.studio.passengers.Validator.ObjectIdConstraint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/system/trip")
@Validated
public class SystemTripAPIRoutes {

    @Autowired
    PassengerService passengerService;

    @GetMapping(value = "getTripPassengers/{tripId}/{userId}")
    @ResponseBody
    public String getTripPassengers(HttpServletRequest request,
                                    @PathVariable @ObjectIdConstraint ObjectId tripId,
                                    @PathVariable @ObjectIdConstraint ObjectId userId) {
        //todo authorize
        return passengerService.getTripPassengers(userId, tripId);
    }

}
