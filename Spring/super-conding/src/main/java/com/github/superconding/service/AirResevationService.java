package com.github.superconding.service;

import com.github.superconding.repository.airlineTicket.AirlineTicket;
import com.github.superconding.repository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.superconding.repository.airlineTicket.AirlineTicketRepository;
import com.github.superconding.repository.passenger.Passenger;
import com.github.superconding.repository.passenger.PassengerReposiotry;
import com.github.superconding.repository.reservations.Reservation;
import com.github.superconding.repository.reservations.ReservationJdbcTemplateDao;
import com.github.superconding.repository.reservations.ReservationRepository;
import com.github.superconding.repository.users.UserEntity;
import com.github.superconding.repository.users.UserRepository;
import com.github.superconding.service.mapper.TicketMapper;
import com.github.superconding.service.mapper.exceptions.InvalidValueException;
import com.github.superconding.web.controller.dto.airline.ReservationRequest;
import com.github.superconding.web.controller.dto.airline.ReservationResult;
import com.github.superconding.web.controller.dto.airline.Ticket;
import com.github.superconding.web.dto.airline.ReservationRequest;
import com.github.superconding.web.dto.airline.ReservationResult;
import com.github.superconding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Mapper

public class AirReservationService {

    private final UserRepository userRepository;
    private final AirlineTicketRepository airlineTicketRepository;

//    private PassengerReposiotry passengerReposiotry;
//    private ReservationRepository reservationRepository;

    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        // 1. 유저를 userId 로 가져와서, 선호하는 여행지 도출
        // 2. 선호하는 여행지와 ticketType으로 AirlineTIcket table 질의 해서 필요한 AirlineTicket
        // 3. 이 둘의 정보를 조합해서 Ticket DTO를 만든다.

        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도", "왕복"));

        if ( ticketTypeSet.contains(ticketType) )
            throw new InvalidValueException("해당 TicketType " + "은 지원하지 않습니다.");

        UserEntity userEntity = userRepository.findUserById(userId);
        String likePlace = userEntity.getLikeTravelPlace();

        List<AirlineTicket> airlineTickets
                = airlineTicketRepository.findAllAirlineTicketsWithPlaceAndTicketType(likePlace, ticketType);

        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;
    }

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        // 1. Reservation Repository, Passenger Repository, Join table ( flight/airline_ticket ),

        // 0. userId,airline_ticke_id
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId= reservationRequest.getAirlineTicketId();

        // 1. Passenger I
        Passenger passenger = passengerReposiotry.findPassengerByUserId(userId);
        Integer passengerId= passenger.getPassengerId();

        // 2. price 등의 정보 불러오기
        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfos
                = airlineTicketRepository.findAllAirLineTicketAndFlightInfo(airlineTicketId);

        // 3. reservation 생성
        Reservation reservation = new Reservation(passengerId, airlineTicketId);
        ReservationJdbcTemplateDao reservationRepository = null;
        Boolean isSuccess = reservationRepository.saveReservation(reservation);

        // TODO: ReservationResult DTO 만들기
        List<Integer> prices = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getPrice).collect(Collectors.toList());
        List<Integer> charges = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getCharge).collect(Collectors.toList());
        Integer tax = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Integer totalPrice = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }

    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
    }
}
