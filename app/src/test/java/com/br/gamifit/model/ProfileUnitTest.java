package com.br.gamifit.model;

import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;

import org.junit.Test;

public class ProfileUnitTest {

    @Test(expected = LessThanFortyMinutesTrainingException.class)
    public void handleCheckOutThrowingExceptionTest(){

    }
}
