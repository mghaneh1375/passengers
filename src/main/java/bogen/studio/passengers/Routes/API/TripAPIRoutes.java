package bogen.studio.passengers.Routes.API;

import bogen.studio.passengers.DTO.PassengerDTO;
import bogen.studio.passengers.Service.PassengerService;
import bogen.studio.passengers.Utility.ValidList;
import bogen.studio.passengers.Validator.ObjectIdConstraint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = "/api/public/trip")
@Validated
public class TripAPIRoutes {

    @Autowired
    PassengerService passengerService;

    @PostMapping(value = "store")
    @ResponseBody
    public String store(HttpServletRequest request,
                        @RequestBody @Valid @Size(min = 1) ValidList<PassengerDTO> passengerDTOS) {
        //todo: userId
        return passengerService.store(new ObjectId(), passengerDTOS.get(0), passengerDTOS.subList(1, passengerDTOS.size()));
    }

    @GetMapping(value = "getTripPassengers/{tripId}")
    @ResponseBody
    public String getTripPassengers(HttpServletRequest request,
                                    @PathVariable @ObjectIdConstraint ObjectId tripId) {
        //todo userId
        return passengerService.getTripPassengers(new ObjectId("64e0af975bfd9f7e5ec45dcf"), tripId, false);
    }

}
