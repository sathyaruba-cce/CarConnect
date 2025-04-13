package dao;

import java.util.List;
import entity.Reservation;
import exception.ReservationException;

public interface IReservationService {
    Reservation getReservationById(int reservationId) throws ReservationException;
    List<Reservation> getReservationsByCustomerId(int customerId) throws ReservationException;
    boolean createReservation(Reservation reservationData) throws ReservationException;
    boolean updateReservation(Reservation reservationData) throws ReservationException;
    boolean cancelReservation(int reservationId) throws ReservationException;
}
