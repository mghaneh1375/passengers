package bogen.studio.passengers.Utility;


import bogen.studio.passengers.Repository.TripRepository;

import java.util.*;

import static bogen.studio.commonkoochita.Utility.Statics.ONE_HOUR_MSEC;

public class Jobs implements Runnable {

    TripRepository tripRepository;

    public Jobs(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new RemoveExpiredTrips(), 0, ONE_HOUR_MSEC);
    }

    private class RemoveExpiredTrips extends TimerTask {

        @Override
        public void run() {

            tripRepository
                    .removeExpiredReservations(System.currentTimeMillis());
        }
    }
}
