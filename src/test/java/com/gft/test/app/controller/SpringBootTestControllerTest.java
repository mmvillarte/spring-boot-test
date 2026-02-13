package com.gft.test.app.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class SpringBootTestControllerTest {
    @InjectMocks
    SpringBootTestController springBootTestController;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHelloWorld() {
        Assertions.assertEquals(SpringBootTestController.HELLO_WORLD, springBootTestController.helloWorld());
    }
}