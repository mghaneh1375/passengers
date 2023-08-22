package bogen.studio.passengers.Components;

import bogen.studio.passengers.Repository.TripRepository;
import bogen.studio.passengers.Utility.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobHandler {

    @Autowired
    TripRepository tripRepository;

    public void run() {
        new Thread(new Jobs(tripRepository)).start();
    }

}
