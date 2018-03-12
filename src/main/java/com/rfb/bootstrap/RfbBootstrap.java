package com.rfb.bootstrap;

import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbEventAttendance;
import com.rfb.domain.RfbLocation;
import com.rfb.domain.User;
import com.rfb.repository.AuthorityRepository;
import com.rfb.repository.RfbEventAttendanceRepository;
import com.rfb.repository.RfbEventRepository;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

/**
 * Bootstrap data into DB
 */
@Component
public class RfbBootstrap implements CommandLineRunner {

    private final RfbEventRepository rfbEventRepository;

    private final RfbLocationRepository rfbLocationRepository;

    private final RfbEventAttendanceRepository rfbEventAttendanceRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    @Autowired
    public RfbBootstrap(final RfbEventRepository rfbEventRepository,
                        final RfbLocationRepository rfbLocationRepository,
                        final RfbEventAttendanceRepository rfbEventAttendanceRepository,
                        final UserRepository userRepository,
                        final PasswordEncoder passwordEncoder,
                        final AuthorityRepository authorityRepository) {
        this.rfbEventRepository = rfbEventRepository;
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventAttendanceRepository = rfbEventAttendanceRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    @Override
    public void run(final String... strings) throws Exception {

        // init RFB Locations
        if (rfbLocationRepository.count() == 0) {
            //only load data if no data loaded
            initData();
        }
    }

    private void initData() {
        User user = new User();
        user.setFirstName("Johnny");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setLogin("johnny");
        user.setEmail("johnny@runningforbrews.com");
        user.setActivated(true);
        user.addAuthority(authorityRepository.findOne("ROLE_ADMIN"));
        //user.addAuthority(authorityRepository.findOne("ROLE_ORGANIZER"));
        userRepository.save(user);

        //load data
        RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

        user.setHomeLocation(aleAndWitch);
        userRepository.save(user);

        RfbEvent aleEvent = getRfbEvent(aleAndWitch);

        getRfbEventAttendance(user, aleEvent);

        RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());

        RfbEvent ratcEvent = getRfbEvent(ratc);

        //getRfbEventAttendance(user, ratcEvent);

        RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());

        RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);

        //getRfbEventAttendance(user, stPeteBrewEvent);

        RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());

        RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);

        //getRfbEventAttendance(user, yardOfAleEvent);

        RfbLocation pourHouse = getRfbLocation("Tampa - Pour House", DayOfWeek.MONDAY.getValue());
        RfbLocation macDintons = getRfbLocation("Tampa - Mac Dintons", DayOfWeek.TUESDAY.getValue());

        RfbLocation satRun = getRfbLocation("Saturday Run for testing", DayOfWeek.SATURDAY.getValue());
    }


    private void getRfbEventAttendance(User user, RfbEvent rfbEvent) {
        RfbEventAttendance rfbAttendance = new RfbEventAttendance();
        rfbAttendance.setRfbEvent(rfbEvent);
        rfbAttendance.setUser(user);
        rfbAttendance.setAttendanceDate(LocalDate.now());

        System.out.println(rfbAttendance.toString());

        rfbEventAttendanceRepository.save(rfbAttendance);
        rfbEventRepository.save(rfbEvent);
    }

    private RfbEvent getRfbEvent(RfbLocation rfbLocation) {
        RfbEvent rfbEvent = new RfbEvent();
        rfbEvent.setEventCode(UUID.randomUUID().toString());
        rfbEvent.setEventDate(LocalDate.now()); // will not be on assigned day...
        rfbLocation.addRfbEvent(rfbEvent);
        rfbLocationRepository.save(rfbLocation);
        rfbEventRepository.save(rfbEvent);
        return rfbEvent;
    }

    private RfbLocation getRfbLocation(String locationName, int value) {
        RfbLocation rfbLocation = new RfbLocation();
        rfbLocation.setLocationName(locationName);
        rfbLocation.setRunDayOfWeek(value);
        rfbLocationRepository.save(rfbLocation);
        return rfbLocation;
    }
}
