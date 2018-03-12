package com.rfb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

abstract class AbstractRepositoryTest {

    @Autowired
    RfbEventRepository rfbEventRepository;

    @Autowired
    RfbLocationRepository rfbLocationRepository;

    @Autowired
    RfbEventAttendanceRepository rfbEventAttendanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;
}
