package com.mockitotutorial.happyhotel.booking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test12Bdd {

	@InjectMocks
	private BookingService bookingService;
	
	@Mock
	private PaymentService paymentServiceMock;
	
	@Mock
	private RoomService roomServiceMock;
	
	@Spy
	private BookingDAO bookingDAOMock;
	
	@Mock
	private MailSender mailSenderMock;
	
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;
	
	
	@Test
	void should_CountAvailablePlaces_When_OneRoomAvailable() {
		
		// Given
		given(this.roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Room 1", 2)));
		
		int expected = 2;
		
		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
	
	@Test
	void should_InvokePayment_When_Prepaid() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
		
		/* Check if any other method in this mock is invoked (In this case, paymentService on bookingService.makeBooking)*/
		verifyNoMoreInteractions(paymentServiceMock);
	}
}
