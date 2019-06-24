package de.hhu.propra.auas.domain;

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
	public void setUp(){
		this.blattServiceMock= mock(BlattService.class);
		this.korrektorServiceMock= mock(KorrektorService.class);
		this.abgabeMock=mock(Abgabe.class);
		this.blattMock=mock(Blatt.class);
		this.korrektorMock=mock(Korrektor.class);
		this.zuordnungsService= new ZuordnungsService(blattServiceMock, korrektorServiceMock);
		//this.zuordnungsService= mock(ZuordnungsService.class);
	}


	@Test
	public void einKorrektorKeineAbgabe() {
		LinkedList <Korrektor> korrektors= new LinkedList<>();
		Korrektor willi= new Korrektor(UUID.randomUUID(), "Willi",10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		LinkedList<Abgabe> abgaben= new LinkedList<>();
		Blatt blatt= new Blatt(1,abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);

		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);


		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 0,blatt.anzahlZugeordneteAbgaben());
	}

	@Test
	public void einKorrektorEineAbgabe() {
		LinkedList <Korrektor> korrektors= new LinkedList<>();
		Korrektor willi= new Korrektor(UUID.randomUUID(), "Willi",10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		LinkedList<Abgabe> abgaben= new LinkedList<>();
		abgaben.add(new Abgabe());
		Blatt blatt= new Blatt(1,abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);

		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);


		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 1,blatt.anzahlZugeordneteAbgaben());
	}

	@Test
	public void einKorrektorElfAbgaben_ZehnStunden() {
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "Willi", 10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		LinkedList<Abgabe> abgaben = new LinkedList<>();
		for (int i = 0; i < 11; i++) {
			abgaben.add(new Abgabe());
		}
		Blatt blatt = new Blatt(1, abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);

		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);


		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 10, blatt.anzahlZugeordneteAbgaben());
	}

	@Test
	public void zweiKorrektorElfAbgaben_prüftErstenKorrektorErhältAbgaben() {
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		int anzahlAbgaben= 11;
		LinkedList<Abgabe> abgaben = new LinkedList<>();
		for (int i = 0; i < anzahlAbgaben; i++) {
			abgaben.add(new Abgabe());
		}
		Blatt blatt = new Blatt(1, abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);

		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);


		zuordnungsService.abgabenZuordnen(1);

		int anzahlAbagbenAnKorrektor= 0;	// hilfsvariable
		for(int i=0; i<anzahlAbgaben; i++) {
			if (abgaben.get(i).getKorrektor() != null && abgaben.get(i).getKorrektor().getName() == "willi"){
				anzahlAbagbenAnKorrektor = anzahlAbagbenAnKorrektor + 1;
			}
		}
		assertEquals((long) 10, (long) anzahlAbagbenAnKorrektor);

	}

	@Test
	public void zweiKorrektorElfAbgaben_prüftZweitenKorrektorErhältAbgaben() {
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		LinkedList<Abgabe> abgaben = new LinkedList<>();
		int anzahlAbagaben= 11;
		for (int i = 0; i < anzahlAbagaben; i++) {
			abgaben.add(new Abgabe());
		}
		Blatt blatt = new Blatt(1, abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);

		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);


		zuordnungsService.abgabenZuordnen(1);

		int anzahlAbagbenAnKorrektor= 0;	// hilfsvariable
		for(int i=0; i<anzahlAbagaben; i++) {
			if (abgaben.get(i).getKorrektor() != null && abgaben.get(i).getKorrektor().getName() == "hans"){
				anzahlAbagbenAnKorrektor = anzahlAbagbenAnKorrektor + 1;
			}
		}
		assertEquals((long) 1, (long) anzahlAbagbenAnKorrektor);

	}


}
