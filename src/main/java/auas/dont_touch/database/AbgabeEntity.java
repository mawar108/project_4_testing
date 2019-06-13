package auas.dont_touch.database;

import java.util.UUID;

import auas.read_only.domain.Abgabe;
import auas.read_only.domain.Korrektor;

class AbgabeEntity {
	private final UUID id;
	private KorrektorEntity korrektor;

	AbgabeEntity() {
		this.id = UUID.randomUUID();
	}

	AbgabeEntity(Abgabe abgabe) {
		this.id = abgabe.getId();
		Korrektor korrektor = abgabe.getKorrektor();
		this.korrektor = KorrektorEntity.safeCreate(korrektor);
	}

	UUID getId() {
		return id;
	}

	KorrektorEntity getKorrektor() {
		return korrektor;
	}

	void setKorrektor(KorrektorEntity korrektor) {
		this.korrektor = korrektor;
	}
}
