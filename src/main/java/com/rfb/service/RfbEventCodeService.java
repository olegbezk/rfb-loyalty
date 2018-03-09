package com.rfb.service;

import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbLocation;
import com.rfb.repository.RfbEventRepository;
import com.rfb.repository.RfbLocationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RfbEventCodeService {

    private final Logger log = LoggerFactory.getLogger(RfbEventCodeService.class);

    private final RfbLocationRepository rfbLocationRepository;

    private final RfbEventRepository rfbEventRepository;

    @Autowired
    public RfbEventCodeService(final RfbLocationRepository rfbLocationRepository,
                               final RfbEventRepository rfbEventRepository) {
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventRepository = rfbEventRepository;
    }

//    @Scheduled(cron = "*/60 * * * * *?") // run once per minute
//    @Scheduled(cron = "*/1 * * * * *?") // run once per second
    @Scheduled(cron = "00 * * * * *?") // run once per hour, a top of hour
    public void generateEventCodes() {
        log.debug("Generating Events");

        final List<RfbLocation> rfbLocations =
                rfbLocationRepository.findAllByRunDayOfWeek(LocalDate.now().getDayOfWeek().getValue());

        log.debug("Location Found for Events: " + rfbLocations.size());

        rfbLocations.forEach(location -> {
            log.debug("Checking Events for location: " + location.getId() );
            RfbEvent existingEvent = rfbEventRepository.findByRfbLocationAndEventDate(location, LocalDate.now());

            if(existingEvent == null) {
                log.debug("Event Not Found, Creating Event");
                //create event for day
                RfbEvent newEvent = new RfbEvent();
                newEvent.setRfbLocation(location);
                newEvent.setEventDate(LocalDate.now());
                newEvent.setEventCode(RandomStringUtils.randomAlphanumeric(10).toUpperCase());

                rfbEventRepository.save(newEvent);

                log.debug("Created Event:" + newEvent.toString());
            } else {
                log.debug("Event exists for day");
            }
        });
    }
}
