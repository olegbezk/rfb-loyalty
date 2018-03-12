package com.rfb.repository;

import com.rfb.RfbLoyaltyApp;
import com.rfb.bootstrap.RfbBootstrap;
import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfbLoyaltyApp.class)
public class RfbEventRepositoryTest extends AbstractRepositoryTest {

    @Before
    public void setUp() {
        new RfbBootstrap(rfbEventRepository, rfbLocationRepository,
                rfbEventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    public void findAllByRfbLocationAndDate() {
        final RfbLocation byLocationName = rfbLocationRepository.findByLocationName("St Pete - Ale and the Witch");

        assertNotNull(byLocationName);

        final RfbEvent byRfbLocationAndEventDate =
                rfbEventRepository.findByRfbLocationAndEventDate(byLocationName, LocalDate.now());

        assertNotNull(byRfbLocationAndEventDate);
    }
}
