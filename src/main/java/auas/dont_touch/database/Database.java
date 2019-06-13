package auas.dont_touch.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import auas.read_only.domain.Abgabe;
import auas.read_only.domain.Blatt;
import auas.read_only.domain.Korrektor;

/**
 * Simuliert eine Datenbankschnittstelle.
 */

@Repository
public class Database {

	private final Map<UUID, KorrektorEntity> korrektoren;
	private final Map<Integer, BlattEntity> blaetter = new HashMap<>();

	public Database() {
		korrektoren = FakeEntryGenerator.generateKorrektoren();
		List<BlattEntity> generatedBlaetter = FakeEntryGenerator.generateBlaetter();
		for (BlattEntity blatt : generatedBlaetter) {
			saveEntity(blatt);
		}
	}

	public List<Blatt> getBlaetter() {
		return blaetter.values().stream().map(this::toBlatt).collect(Collectors.toList());
	}

	public Blatt getBlatt(int id) {
		return toBlatt(blaetter.get(id));
	}

	public List<Korrektor> getKorrektoren() {
		return korrektoren.values().stream().map(this::toKorrektor).collect(Collectors.toList());
	}

	public void save(Blatt blatt) {
		int id = blatt.getId();
		blaetter.put(id, new BlattEntity(blatt));
	}

	// =========================

	private void saveEntity(BlattEntity blatt) {
		blaetter.put(blatt.getId(), blatt);
	}

	private Korrektor toKorrektor(KorrektorEntity entity) {
		if (entity == null) {
			return null;
		}
		return new Korrektor(entity.getId(), entity.getName(), entity.getStunden());
	}

	private Abgabe toAbgabe(AbgabeEntity entity) {
		Korrektor korrektor = toKorrektor(entity.getKorrektor());
		return new Abgabe(entity.getId(), korrektor);
	}

	private Blatt toBlatt(BlattEntity entity) {
		List<Abgabe> abgaben = entity.getAbgaben().stream().map(this::toAbgabe).collect(Collectors.toList());
		return new Blatt(entity.getId(), abgaben);
	}

}
