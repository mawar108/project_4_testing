package auas.services;

import java.util.LinkedList;
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
	private int nextKorrektor = 0;

	@Inject
	public ZuordnungsService(BlattService blattService, KorrektorService korrektorService) {
		this.blattService = blattService;
		this.korrektorService = korrektorService;
	}

	public void abgabenZuordnen(int id) {
		Blatt blatt = blattService.getBlatt(id);
		List<Korrektor> korrektoren = (LinkedList) korrektorService.getAll();


		int abgabeAnzahl = 0;
		int korrektorAnzahl = korrektoren.size();

		Korrektor korrektor = korrektoren.get(nextKorrektor);
		List<Abgabe> abgaben = blatt.getUnzugeordneteAbgaben();

		for (int j = 0; j < korrektorAnzahl; j++) {
			for (int i = 0; i < korrektor.getStunden(); i++) {
				if (abgabeAnzahl < abgaben.size()) {
					blatt.abgabeZuordnen(abgaben.get(abgabeAnzahl), korrektor);
					abgabeAnzahl++;
				}
			}
			korrektor= korrektorWechsel(korrektoren);
		}

		blattService.save(blatt);
	}

	private Korrektor korrektorWechsel(List<Korrektor> korrektoren) {
		nextKorrektor++;
		if (nextKorrektor < korrektoren.size()) {
			return korrektoren.get(nextKorrektor);
		}
		else{
			nextKorrektor=0;
			return korrektoren.get(nextKorrektor);
		}
	}
}