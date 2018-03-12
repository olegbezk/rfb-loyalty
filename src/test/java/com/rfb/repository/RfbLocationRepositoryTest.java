package com.rfb.repository;

import com.rfb.RfbLoyaltyApp;
import com.rfb.bootstrap.RfbBootstrap;
import com.rfb.domain.RfbLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfbLoyaltyApp.class)
public class RfbLocationRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void setUp() {
        new RfbBootstrap(rfbEventRepository, rfbLocationRepository,
                rfbEventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    public void findAllByRunDayOfWeek() {
        final List<RfbLocation> mondayLocations =
                rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.MONDAY.getValue());

        final List<RfbLocation> tuesdayLocations =
                rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.TUESDAY.getValue());

        final List<RfbLocation> wednesdayLocations =
                rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.WEDNESDAY.getValue());

        assertEquals(2, mondayLocations.size());
        assertEquals(2, tuesdayLocations.size());
        assertEquals(1, wednesdayLocations.size());
    }
}
