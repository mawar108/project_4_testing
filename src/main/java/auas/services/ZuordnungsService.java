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

	@Inject
	public ZuordnungsService(BlattService blattService, KorrektorService korrektorService) {
		this.blattService = blattService;
		this.korrektorService = korrektorService;
	}

	public void abgabenZuordnen(int id) {
		Blatt blatt = blattService.getBlatt(id);
		List<Korrektor> korrektoren= (LinkedList) korrektorService.getAll();

		int nextKorrektor=0;
		int abgabeAnzahl= 0;
		int korrektorAnzahl= korrektoren.size();

		Korrektor korrektor= korrektoren.get(nextKorrektor);
		List<Abgabe> abgaben = blatt.getUnzugeordneteAbgaben();

		for(int j= 0; j<korrektorAnzahl;j++){
			for (int i =0; i<korrektor.getStunden(); i++) {
				if(abgabeAnzahl<abgaben.size()) {
					blatt.abgabeZuordnen(abgaben.get(abgabeAnzahl), korrektor);
					abgabeAnzahl++;
				}
			}
			nextKorrektor++;
			if(nextKorrektor<korrektorAnzahl) {
				korrektor = korrektoren.get(nextKorrektor);
			}
		}

		blattService.save(blatt);
	}
}
