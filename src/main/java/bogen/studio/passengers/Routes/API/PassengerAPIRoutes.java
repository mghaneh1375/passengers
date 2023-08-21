package bogen.studio.passengers.Routes.API;

import bogen.studio.passengers.Service.PassengerService;
import bogen.studio.passengers.Validator.ObjectIdConstraint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/public/passenger")
@Validated
public class PassengerAPIRoutes {

    @Autowired
    PassengerService passengerService;

    @GetMapping(value = "list")
    @ResponseBody
    public String list(HttpServletRequest request) {
        // todo: userId
        return passengerService.list(new ObjectId());
    }

    @DeleteMapping(value = "remove/{id}")
    @ResponseBody
    public String remove(HttpServletRequest request,
                         @PathVariable @ObjectIdConstraint ObjectId id) {
        // todo: userId
        return passengerService.remove(new ObjectId(), id);
    }

}
