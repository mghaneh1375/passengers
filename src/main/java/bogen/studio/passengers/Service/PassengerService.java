package bogen.studio.passengers.Service;

import bogen.studio.passengers.DTO.PassengerDTO;
import bogen.studio.passengers.Enum.AgeType;
import bogen.studio.passengers.Enum.Sex;
import bogen.studio.passengers.Enum.StayStatus;
import bogen.studio.passengers.Model.Passenger;
import bogen.studio.passengers.Model.Trip;
import bogen.studio.passengers.Repository.PassengerRepository;
import bogen.studio.passengers.Repository.TripRepository;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static my.common.commonkoochita.Utility.Statics.*;
import static my.common.commonkoochita.Utility.Utility.generateSuccessMsg;

@Service
public class PassengerService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    PassengerRepository passengerRepository;

    public String list(ObjectId userId) {

        List<Passenger> passengers = passengerRepository.findByUserId(userId);

        JSONArray jsonArray = new JSONArray();
        passengers.forEach(x -> {

            JSONObject jsonObject = new JSONObject(x);
            jsonObject.remove("_id");
            jsonObject.put("id", x.get_id().toString());

            jsonObject.put("sex", x.getSex().toFarsi());
            jsonObject.put("stayStatus", x.getStayStatus().toFarsi());
            jsonObject.put("ageType", x.getAgeType().toFarsi());

            jsonArray.put(jsonObject);
        });

        return generateSuccessMsg("data", jsonArray);
    }

    public String store(ObjectId userId, PassengerDTO createdBy, List<PassengerDTO> passengerDTO) {

        List<Passenger> modified = new ArrayList<>();
        List<ObjectId> ids = new ArrayList<>();

        String unique = createdBy.getStayStatus().equalsIgnoreCase(StayStatus.IRANI.getName()) ? createdBy.getNID() :
                createdBy.getStayStatus().equalsIgnoreCase(StayStatus.CITIZEN.getName()) ? createdBy.getCitizenNo() :
                        createdBy.getPassportNo();

        Passenger creator = passengerRepository.findByUnique(userId, unique);
        boolean isNew = creator == null;

        creator = populatePassenger(creator, createdBy);

        if(isNew) {
            creator.setUserId(userId);
            passengerRepository.insert(creator);
        }
        else
            modified.add(creator);

        for(PassengerDTO dto : passengerDTO) {

            unique = dto.getStayStatus().equalsIgnoreCase(StayStatus.IRANI.getName()) ? dto.getNID() :
                    dto.getStayStatus().equalsIgnoreCase(StayStatus.CITIZEN.getName()) ? dto.getCitizenNo() :
                    dto.getPassportNo();

            Passenger passenger = passengerRepository.findByUnique(userId, unique);

            isNew = passenger == null;

            passenger = populatePassenger(passenger, dto);
            if(isNew) {
                passenger.setUserId(userId);
                passengerRepository.insert(passenger);
            }
            else
                modified.add(passenger);

            ids.add(passenger.get_id());
        }

        Trip trip = null;

        if(ids.size() > 0) {

            trip = new Trip();
            trip.setUserId(userId);
            trip.setPersons(ids);
            trip.setCreatedBy(creator.get_id());
            trip.setExpireAt(System.currentTimeMillis() + 20 * 60 * 1000);

            tripRepository.insert(trip);
        }

        if(modified.size() > 0)
            passengerRepository.saveAll(modified);

        if(trip != null)
            return generateSuccessMsg("data", trip.get_id().toString());

        return JSON_NOT_VALID_PARAMS;
    }

    public String remove(ObjectId userId, ObjectId id) {

        Passenger passenger = passengerRepository.findByUserIdAndId(userId, id);
        if(passenger == null)
            return JSON_NOT_ACCESS;

        passenger.setRemovedAt(new Date());
        passengerRepository.save(passenger);

        return JSON_OK;
    }

    public Passenger populatePassenger(Passenger passenger, PassengerDTO passengerDTO) {

        if(passenger == null)
            passenger = new Passenger();

        passenger.setNameFa(passengerDTO.getNameFa());
        passenger.setLastNameFa(passengerDTO.getLastNameFa());

        passenger.setNameEn(passengerDTO.getNameEn());
        passenger.setLastNameEn(passengerDTO.getLastNameEn());

        passenger.setStayStatus(StayStatus.valueOf(passengerDTO.getStayStatus().toUpperCase()));
        passenger.setAgeType(AgeType.valueOf(passengerDTO.getAgeType().toUpperCase()));
        passenger.setSex(Sex.valueOf(passengerDTO.getSex().toUpperCase()));

        if (passengerDTO.getCitizenship() != null)
            passenger.setCitizenship(passengerDTO.getCitizenship());

        if(passengerDTO.getMail() != null)
            passenger.setMail(passengerDTO.getMail());

        if(passengerDTO.getPhone() != null)
            passenger.setPhone(passengerDTO.getPhone());

        if(passengerDTO.getNID() != null)
            passenger.setNID(passengerDTO.getNID());

        if(passengerDTO.getPassportNo() != null)
            passenger.setPassportNo(passengerDTO.getPassportNo());

        if(passengerDTO.getCitizenNo() != null)
            passenger.setCitizenNo(passengerDTO.getCitizenNo());

        return passenger;
    }

    public String getTripPassengers(ObjectId userId, ObjectId tripId) {

        Trip trip = tripRepository.findByUserIdAndId(userId, tripId, System.currentTimeMillis());

        if(trip == null)
            return JSON_NOT_ACCESS;

        List<Passenger> passengers = passengerRepository.findByIdsIn(trip.getPersons());

        JSONArray jsonArray = new JSONArray();
        passengers.forEach(x -> {

            JSONObject jsonObject = new JSONObject(x);

            jsonObject.put("sex", x.getSex().toFarsi());
            jsonObject.put("stayStatus", x.getStayStatus().toFarsi());
            jsonObject.put("ageType", x.getAgeType().toFarsi());

            jsonArray.put(jsonObject);
        });

        JSONObject result = new JSONObject()
                .put("passengers", jsonArray);

        Passenger creator = passengerRepository.findById(trip.getCreatedBy()).orElse(null);
        if(creator != null) {

            JSONObject jsonObject = new JSONObject(creator);
            jsonObject.remove("_id");
            jsonObject.remove("userId");

            jsonObject.put("sex", creator.getSex().toFarsi());
            jsonObject.put("stayStatus", creator.getStayStatus().toFarsi());
            jsonObject.put("ageType", creator.getAgeType().toFarsi());

            result.put("creator", jsonObject);
        }

        return generateSuccessMsg("data", result);

    }
}
