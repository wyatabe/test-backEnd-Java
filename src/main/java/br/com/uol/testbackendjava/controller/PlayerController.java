package br.com.uol.testbackendjava.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.service.PlayerGroupService;
import br.com.uol.testbackendjava.service.PlayerService;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerGroupService playerGroupService;
	
	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("players", playerService.findAll());		
		return "player-list";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Player player, Model model) {
		model.addAttribute("player", player);
		model.addAttribute("playerGroups", playerGroupService.findAll());
		return "player-form";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Player player = playerService.findOne(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
		return add(player, model);
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		playerService.delete(id);
		return list(model);
	}

	@PostMapping("/save")
	public String save(@ModelAttribute @Valid Player player, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return add(player, model);
		}
		if (StringUtils.isEmpty(player.getCodename())) {
			String codename = playerService.getCodename(player.getPlayerGroup());
			if (!StringUtils.isEmpty(codename)) {
				player.setCodename(codename);		
			} else {
				model.addAttribute("unavailablePlayerGroup", player.getPlayerGroup());
				return add(player, model);
			}
		}
		model.addAttribute(player.getId() == 0 ? "newPlayer" : "editPlayer", player);
		player = playerService.save(player);
		return list(model);
	}

}
