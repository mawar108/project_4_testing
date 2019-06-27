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
	private int nextKorrektorÜberhang=0;

	@Inject
	public ZuordnungsService(BlattService blattService, KorrektorService korrektorService) {
		this.blattService = blattService;
		this.korrektorService = korrektorService;
	}

	public void abgabenZuordnen(int id) {
		Blatt blatt = blattService.getBlatt(id);
		List<Korrektor> korrektoren  = (LinkedList) korrektorService.getAll();
		List<Abgabe> abgaben = blatt.getUnzugeordneteAbgaben();

		int nextAbgabe=0;
		for(int i= 0; i<korrektoren.size(); i++){ // gehe durch die Koorektorren durch
			for(int j=0; j<verteilungsFormel(korrektoren.get(i),abgaben);j++){ // Verteile die Abgaben
				blatt.abgabeZuordnen(abgaben.get(nextAbgabe), korrektoren.get(i));
				nextAbgabe++;
			}
		}

		while(blatt.getUnzugeordneteAbgaben().size()>0){
			blatt.abgabeZuordnen(abgaben.get(nextAbgabe), korrektoren.get(nextKorrektorÜberhang));
			nextAbgabe++;
			korrektorWechsel(korrektoren);
		}


		blattService.save(blatt);
	}

	private int gesamtStunden(){
		List<Korrektor> korrektoren  = (LinkedList) korrektorService.getAll();
		int gesamtStunden= 0;
		for(int i = 0; i<korrektoren.size();i++){
			gesamtStunden= gesamtStunden+korrektoren.get(i).getStunden();
		}
		return gesamtStunden;
	}


	private int verteilungsFormel(Korrektor korrektor,List<Abgabe> abgaben ){
		return (int)Math.floor((abgaben.size()/gesamtStunden())*korrektor.getStunden());
	}

	private void korrektorWechsel(List<Korrektor> korrektoren) {
		nextKorrektorÜberhang++;
		if (nextKorrektorÜberhang >= korrektoren.size()) {
			nextKorrektorÜberhang = 0;
		}
	}

}