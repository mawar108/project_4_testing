package auas.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import auas.read_only.domain.Abgabe;
import auas.read_only.domain.Blatt;
import auas.read_only.domain.Korrektor;

@Service
public class ZuordnungsService {

	private BlattService blattService;
	private KorrektorService korrektorService;

	@Inject
	public ZuordnungsService(BlattService blattService, KorrektorService korrektorService) {
		this.blattService = blattService;
		this.korrektorService = korrektorService;
	}

	public void abgabenZuordnen(int id) {

		// Beispielimplementierung: Der erste Korrektor bekommt alle Abgaben
		// 1. Verstehen Sie die BlattService API. Schauen Sie
		// auch die hier nicht verwendeten Methoden an
		// 2. Löschen Sie diesen Code, bevor Sie die faire Verteilung
		// implementieren

		Blatt blatt = blattService.getBlatt(id);
		Korrektor korrektor = korrektorService.getAll().getFirst();

		List<Abgabe> abgaben = blatt.getUnzugeordneteAbgaben();

		for (Abgabe abgabe : abgaben) {
			blatt.abgabeZuordnen(abgabe, korrektor);
		}

		blattService.save(blatt);
	}

}
