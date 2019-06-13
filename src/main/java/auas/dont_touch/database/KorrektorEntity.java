package auas.dont_touch.database;

import java.util.UUID;

import auas.read_only.domain.Korrektor;

class KorrektorEntity {
	private final UUID id;
	private final int stunden;
	private final String name;

	public KorrektorEntity(String name, int stunden) {
		this.name = name;
		this.stunden = stunden;
		id = UUID.randomUUID();
	}

	private KorrektorEntity(Korrektor korrektor) {
		this.name = korrektor.getName();
		this.stunden = korrektor.getStunden();
		this.id = korrektor.getId();
	}

	public static KorrektorEntity safeCreate(Korrektor korrektor) {
		if (korrektor == null) {
			return null;
		} else {
			return new KorrektorEntity(korrektor);
		}

	}

	public UUID getId() {
		return id;
	}

	String getName() {
		return name;
	}

	int getStunden() {
		return stunden;
	}

}
