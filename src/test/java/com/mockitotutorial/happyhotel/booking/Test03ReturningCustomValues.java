package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;

class Test03ReturningCustomValues {

	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	
	private RoomService roomServiceMock;
	
	private BookingDAO bookingDAOMock;
	
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		
		this.paymentServiceMock = mock(PaymentService.class);
		
		this.roomServiceMock = mock(RoomService.class);
		
		this.bookingDAOMock = mock(BookingDAO.class);
		
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(this.paymentServiceMock, 
				                                 this.roomServiceMock, 
				                                 this.bookingDAOMock, 
				                                 this.mailSenderMock);
	}
	
	@Test
	void should_CountAvailablePlaces_When_OneRoomAvailable() {
		
		// Given
		when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 2)));
		
		int expected = 2;
		
		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
	
	@Test
	void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
		
		// Given
		List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 4));
		
		when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
		
		int expected = 6;
		
		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
}