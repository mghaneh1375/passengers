package bogen.studio.passengers.Routes.API;

import bogen.studio.passengers.Service.PassengerService;
import bogen.studio.passengers.Validator.ObjectIdConstraint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static bogen.studio.passengers.Routes.Utility.getUserId;

@RestController
@RequestMapping(path = "/api/public/passenger")
@Validated
public class PassengerAPIRoutes {

    @Autowired
    PassengerService passengerService;

    @GetMapping(value = "list")
    @ResponseBody
    public String list(Principal principal) {
        return passengerService.list(getUserId(principal));
    }

    @DeleteMapping(value = "remove/{id}")
    @ResponseBody
    public String remove(Principal principal,
                         @PathVariable @ObjectIdConstraint ObjectId id) {
        return passengerService.remove(getUserId(principal), id);
    }

}
