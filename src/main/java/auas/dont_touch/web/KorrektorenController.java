package auas.dont_touch.web;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import auas.read_only.domain.Abgabe;
import auas.read_only.domain.Blatt;
import auas.read_only.domain.Korrektor;
import auas.services.BlattService;
import auas.services.KorrektorService;
import auas.services.ZuordnungsService;

@Controller
public class KorrektorenController {

	private ZuordnungsService zuordnung;
	private BlattService blattService;
	private KorrektorService korrektorService;

	private int letztesBlatt = -1;

	public KorrektorenController(ZuordnungsService zuordnung, BlattService blattService,
			KorrektorService korrektorService) {
		this.zuordnung = zuordnung;
		this.blattService = blattService;
		this.korrektorService = korrektorService;
	}

	@PostMapping("/zuordnen")
	public String zuordnen(int id) {
		letztesBlatt = Math.max(id, letztesBlatt);
		IntStream.range(0, id + 1).forEach(zuordnung::abgabenZuordnen);
		return "redirect:/";
	}

	@PostMapping("/hinzufuegen")
	public String hinzufuegen(Model model, int id) {
		Blatt blatt = blattService.getBlatt(id);
		blatt.addAbgabe(new Abgabe()); // Das wäre eigentlich User Input
		blattService.save(blatt);
		return "redirect:/";
	}

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("blaetter", blattService.getAllBlaetter());
		model.addAttribute("korrektoren", korrektorService.getAll());
		model.addAttribute("letztesBlatt", letztesBlatt);
		return "mainpage";
	}

	@GetMapping("/zuordnung")
	public String zuordnung(Model model, int id) {
		Blatt blatt = blattService.getBlatt(id);
		Collection<Korrektor> korrektoren = korrektorService.getAll();
		Map<Korrektor, Long> zugeordneteAbgaben = getZuordnungen(blatt);
		model.addAttribute("zuordnung", zugeordneteAbgaben);
		model.addAttribute("korrektoren", korrektoren);
		model.addAttribute("unzugeordnet", blatt.anzahlAllerAbgaben() - blatt.anzahlZugeordneteAbgaben());
		return "zuordnung";
	}

	private Map<Korrektor, Long> getZuordnungen(Blatt blatt) {
		return blatt.getZugeordneteAbgaben().stream()
				.collect(Collectors.groupingBy(Abgabe::getKorrektor, Collectors.counting()));
	}

}
