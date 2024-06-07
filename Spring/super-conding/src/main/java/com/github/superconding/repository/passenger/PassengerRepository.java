package com.github.superconding.repository.passenger;

public interface PassengerRepository {
    Passenger findPassengerByUserId(Integer userId);
}
