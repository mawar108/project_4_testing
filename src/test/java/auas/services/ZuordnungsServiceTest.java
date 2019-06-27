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

	private Blatt erstelleAbgaben(int abgabeAnzahl, int id) {
		LinkedList<Abgabe> abgaben = new LinkedList<>();
		for (int i = 0; i < abgabeAnzahl; i++) {
			abgaben.add(new Abgabe());
		}
		Blatt blatt = new Blatt(id, abgaben);
		when(blattServiceMock.getBlatt(id)).thenReturn(blatt);
		when(blattMock.getUnzugeordneteAbgaben()).thenReturn(abgaben);
		return blatt;
	}

	private int anzahlAbgabenAnKorrektor(Blatt blatt, Korrektor korrektor) {
		int anzahlAbagbenAnKorrektor = 0;
		List<Abgabe> zugeordneteAbgaben = blatt.getZugeordneteAbgaben();
		for (Abgabe abgabe : zugeordneteAbgaben) {
			if (abgabe.getKorrektor().getName() == korrektor.getName()) {
				anzahlAbagbenAnKorrektor++;
			}
		}
		return anzahlAbagbenAnKorrektor;
	}

	@Test
	public void einKorrektorKeineAbgabe() {
		//Korrektor wird erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "Willi", 10);
		korrektors.add(willi);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(0, 1);

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
		Blatt blatt = erstelleAbgaben(10, 1);
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
		Blatt blatt = erstelleAbgaben(11, 1);
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
		Blatt blatt = erstelleAbgaben(20, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 10, (long) anzahlAbgabenAnKorrektor(blatt, willi));
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
		Blatt blatt = erstelleAbgaben(20, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 10, (long) anzahlAbgabenAnKorrektor(blatt, hans));

	}

	@Test
	public void zweiKorrektor23Abgaben_prüftErstenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(23, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 12, (long) anzahlAbgabenAnKorrektor(blatt, willi));
	}

	@Test
	public void zweiKorrektor23Abgaben_prüftZweitenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 10);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 10);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(23, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 11, (long) anzahlAbgabenAnKorrektor(blatt, hans));

	}

	@Test
	public void zweiKorrektor27Abgaben_prüftErstenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 18, (long) anzahlAbgabenAnKorrektor(blatt, willi));

	}

	@Test
	public void zweiKorrektor27Abgaben_prüftZweitenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 11);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		korrektors.add(willi);
		korrektors.add(hans);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 10, (long) anzahlAbgabenAnKorrektor(blatt, hans));

	}

	@Test
	public void dreiKorrektor27Abgaben_prüftErstenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 17, (long) anzahlAbgabenAnKorrektor(blatt, willi));

	}

	@Test
	public void dreiKorrektor27Abgaben_prüftZweitenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1);

		assertEquals((long) 9, (long) anzahlAbgabenAnKorrektor(blatt, hans));
	}

	@Test
	public void dreiKorrektor27Abgaben_prüftDritenKorrektorErhältAbgaben() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1);


		assertEquals((long) 1, (long) anzahlAbgabenAnKorrektor(blatt, peter));

	}

	@Test
	public void zweiBlätter_prüftObGerechteVeteilungFürAlle_ErsterKorrektor() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt1 = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1); //Die Abgaben des ersten Blattes werden verteilt

		//Abgaben werden erstellt
		Blatt blatt2 = erstelleAbgaben(27, 2);

		zuordnungsService.abgabenZuordnen(2);// Die Abgaben des zweiten Blattes werden verteit

		assertEquals((long) 16, (long) anzahlAbgabenAnKorrektor(blatt2, willi));

	}


	@Test
	public void zweiBlätter_prüftObGerechteVeteilungFürAlle_ZweiterKorrektor() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt1 = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1); //Die Abgaben des ersten Blattes werden verteilt

		//Abgaben werden erstellt
		Blatt blatt2 = erstelleAbgaben(27, 2);

		zuordnungsService.abgabenZuordnen(2);// Die Abgaben des zweiten Blattes werden verteit

		assertEquals((long) 10, (long) anzahlAbgabenAnKorrektor(blatt2, hans));

	}

	@Test
	public void dreiBlätter_prüftObGerechteVeteilungFürAlle_DritterKorrektor() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt1 = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1); //Die Abgaben des ersten Blattes werden verteilt

		//Abgaben werden erstellt
		Blatt blatt2 = erstelleAbgaben(27, 2);

		zuordnungsService.abgabenZuordnen(2);// Die Abgaben des zweiten Blattes werden verteit

		//Abgaben werden erstellt
		Blatt blatt3 = erstelleAbgaben(27, 3);

		zuordnungsService.abgabenZuordnen(3);// Die Abgaben des zweiten Blattes werden verteit

		assertEquals((long) 2, (long) anzahlAbgabenAnKorrektor(blatt3, peter));

	}

	@Test
	public void vierBlätter_prüftObGerechteVeteilungFürAlle_ErterKorrektor() {
		//Korrektoren werden erstellt
		LinkedList<Korrektor> korrektors = new LinkedList<>();
		Korrektor willi = new Korrektor(UUID.randomUUID(), "willi", 12);
		Korrektor hans = new Korrektor(UUID.randomUUID(), "hans", 7);
		Korrektor peter = new Korrektor(UUID.randomUUID(), "peter", 1);
		korrektors.add(willi);
		korrektors.add(hans);
		korrektors.add(peter);
		when(korrektorServiceMock.getAll()).thenReturn(korrektors);

		//Abgaben werden erstellt
		Blatt blatt1 = erstelleAbgaben(27, 1);

		zuordnungsService.abgabenZuordnen(1); //Die Abgaben des ersten Blattes werden verteilt

		//Abgaben werden erstellt
		Blatt blatt2 = erstelleAbgaben(27, 2);

		zuordnungsService.abgabenZuordnen(2);// Die Abgaben des zweiten Blattes werden verteit

		//Abgaben werden erstellt
		Blatt blatt3 = erstelleAbgaben(27, 3);

		zuordnungsService.abgabenZuordnen(3);// Die Abgaben des zweiten Blattes werden verteit

		//Abgaben werden erstellt
		Blatt blatt4 = erstelleAbgaben(27, 4);

		zuordnungsService.abgabenZuordnen(4);// Die Abgaben des zweiten Blattes werden verteit

		assertEquals((long) 17, (long) anzahlAbgabenAnKorrektor(blatt4, willi));

	}

}