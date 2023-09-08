package bogen.studio.passengers.Routes.API;

import my.common.commonkoochita.Router.Router;
import my.common.commonkoochita.Utility.ValidList;
import bogen.studio.passengers.DTO.PassengerDTO;
import bogen.studio.passengers.Service.PassengerService;
import bogen.studio.passengers.Validator.ObjectIdConstraint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.security.Principal;


@RestController
@RequestMapping(path = "/api/public/trip")
@Validated
public class TripAPIRoutes extends Router {

    @Autowired
    PassengerService passengerService;

    @PostMapping(value = "store")
    @ResponseBody
    public String store(Principal principal,
                        @RequestBody @Valid @Size(min = 1) ValidList<PassengerDTO> passengerDTOS) {
        return passengerService.store(new ObjectId(getUserId(principal)), passengerDTOS.get(0), passengerDTOS.subList(1, passengerDTOS.size()));
    }

    @GetMapping(value = "getTripPassengers/{tripId}")
    @ResponseBody
    public String getTripPassengers(Principal principal,
                                    @PathVariable @ObjectIdConstraint ObjectId tripId) {
        return passengerService.getTripPassengers(new ObjectId(getUserId(principal)), tripId);
    }

}
