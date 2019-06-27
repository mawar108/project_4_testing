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

	public Blatt erstelleAbgaben(int abgabeAnzahl){
		LinkedList<Abgabe> abgaben = new LinkedList<>();
		for (int i = 0; i < abgabeAnzahl; i++) {
			abgaben.add(new Abgabe());
		}
		Blatt blatt = new Blatt(1, abgaben);
		when(blattServiceMock.getBlatt(1)).thenReturn(blatt);
		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);
		return blatt;
	}


	@Test
	public void einKorrektorKeineAbgabe() {
		//Korrektor wird erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "Willi", 10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt= erstelleAbgaben(0);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 0, blatt.anzahlZugeordneteAbgaben());
	}

	@Test
	public void einKorrektorZehnAbgaben_ZehnStunden() {
		//Korrektor wird erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "Willi", 10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt= erstelleAbgaben(10);
		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 10, blatt.anzahlZugeordneteAbgaben());
	}


	@Test
	public void einKorrektorElfAbgaben_ZehnStunden() {
		//Korrektor wird erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "Willi", 10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben erstllen
		Blatt blatt= erstelleAbgaben(11);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 11, blatt.anzahlZugeordneteAbgaben());
	}

	@Test
	public void zweiKorrektor20Abgaben_prüftErstenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt= erstelleAbgaben(20);

		zuordnungsService.abgabenZuordnen(1);

		int anzahlAbagbenAnKorrektor = 0;
		List<Abgabe> zugeordneteAbgaben = blatt.getZugeordneteAbgaben();
		for (Abgabe abgabe : zugeordneteAbgaben) {
			if (abgabe.getKorrektor().getName() == willi.getName()) {
				anzahlAbagbenAnKorrektor++;
			}
		}
		assertEquals((long) 10, (long) anzahlAbagbenAnKorrektor);
	}

	@Test
	public void zweiKorrektor20Abgaben_prüftEsrKorrektorErhältAbgaben() {
		//Korrektor werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt= erstelleAbgaben(20);

		zuordnungsService.abgabenZuordnen(1);

		int anzahlAbagbenAnKorrektor = 0;
		List<Abgabe> zugeordneteAbgaben = blatt.getZugeordneteAbgaben();
		for (Abgabe abgabe : zugeordneteAbgaben) {
			if (abgabe.getKorrektor().getName() == hans.getName()) {
				anzahlAbagbenAnKorrektor++;
			}
		}

		assertEquals((long) 10, (long) anzahlAbagbenAnKorrektor);

	}
}