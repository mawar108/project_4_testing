package auas.dont_touch.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

class FakeEntryGenerator {

	static String generateName() {
		String vorname = selectRandom(VORNAMEN);
		String nachname = selectRandom(NACHNAMEN);
		return vorname + " " + nachname;
	}

	private static String selectRandom(String[] array) {
		int index = random.nextInt(array.length);
		return array[index];
	}

	private final static Random random = new Random();

	private final static String[] VORNAMEN = { "Sarah", "Anna", "Lea", "Laura", "Julia", "Lisa", "Lena", "Marie",
			"Hannah",
			"Lara", "Vanessa", "Annika", "Johanna", "Antonia", "Katharina", "Jan", "Lukas", "Niklas", "Tim", "Finn",
			"Jonas", "Leon", "Felix", "Florian", "Philipp", "Tom", "Jannik", "Luca", "Jakob", "Lennard" };

	private final static String[] NACHNAMEN = { "Müller", "Schmidt", "Schneider", "Fischer", "Meyer", "Weber", "Wagner",
			"Becker", "Schulz", "Hoffmann", "Schäfer", "Koch", "Bauer", "Richter", "Klein", "Schröder", "Wolf",
			"Neumann", "Schwarz", "Zimmermann" };

	static List<AbgabeEntity> generateAbgaben() {
		ArrayList<AbgabeEntity> abgaben = new ArrayList<>();
		int anzahl = random.nextInt(100) + 100;
		for (int i = 0; i < anzahl; i++) {
			abgaben.add(new AbgabeEntity());
		}
		return abgaben;
	}

	static List<BlattEntity> generateBlaetter() {
		int number = random.nextInt(5) + 2;
		List<BlattEntity> blaetter = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			blaetter.add(generateBlatt(i));
		}
		return blaetter;
	}

	static BlattEntity generateBlatt(int id) {
		List<AbgabeEntity> abgaben = generateAbgaben();
		BlattEntity blatt = new BlattEntity(id);
		blatt.setAbgaben(abgaben);
		return blatt;
	}

	static KorrektorEntity generateKorrektor() {
		String name = generateName();
		int stunden = generateStunden();
		return new KorrektorEntity(name, stunden);
	}

	static Map<UUID, KorrektorEntity> generateKorrektoren() {
		Map<UUID, KorrektorEntity> korrektoren = new HashMap<>();
		int anzahl = random.nextInt(10) + 10;
		for (int i = 0; i < anzahl; i++) {
			KorrektorEntity korrektor = generateKorrektor();
			korrektoren.put(korrektor.getId(), korrektor);
		}
		return korrektoren;
	}

	static int generateStunden() {
		int select = random.nextInt(100);
		if (select < 10) {
			return 17;
		}
		if (select < 20) {
			return 7;
		}
		return 10;
	}

}
