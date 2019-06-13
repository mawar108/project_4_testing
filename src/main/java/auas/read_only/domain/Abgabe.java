package auas.read_only.domain;

import java.util.UUID;

public class Abgabe {
	private Korrektor korrektor;
	private final UUID id;

	public Abgabe(UUID id, Korrektor korrektor) {
		this.id = id;
		this.korrektor = korrektor;
	}

	public Abgabe() {
		this(UUID.randomUUID(), null);
	}

	public UUID getId() {
		return id;
	}

	public boolean isZugeordnet() {
		return korrektor != null;
	}

	public Korrektor getKorrektor() {
		return korrektor;
	}

	void zuordnen(Korrektor korrektor) {
		this.korrektor = korrektor;
	}

}
