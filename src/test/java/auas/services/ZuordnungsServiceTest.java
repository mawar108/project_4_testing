package auas.services;

import auas.dont_touch.database.Database;
import auas.read_only.domain.*;
import auas.services.*;
import org.junit.Before;
import org.junit.Test;


import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ZuordnungsServiceTest {
	private BlattService blattServiceMock;
	private KorrektorService korrektorServiceMock;
	private Database databaseMock;
	private Abgabe abgabeMock;
	private Blatt blattMock;
	private Korrektor korrektorMock;
	private ZuordnungsService zuordnungsService;


	@Before
	public void setUp() {
		this.blattServiceMock = mock(BlattService.class);
		this.korrektorServiceMock = mock(KorrektorService.class);
		this.abgabeMock = mock(Abgabe.class);
		this.blattMock = mock(Blatt.class);
		this.korrektorMock = mock(Korrektor.class);
		this.zuordnungsService = new ZuordnungsService(blattServiceMock, korrektorServiceMock);
	}

	@Test
	public void Test(){

	}

}