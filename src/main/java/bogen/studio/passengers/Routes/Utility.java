package bogen.studio.passengers.Routes;

import bogen.studio.passengers.Model.User;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class Utility {

    public static ObjectId getUserId(Principal principal) {
        return ((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();
    }

}
