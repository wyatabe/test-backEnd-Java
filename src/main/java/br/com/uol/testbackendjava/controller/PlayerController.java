package br.com.uol.testbackendjava.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.service.PlayerGroupService;
import br.com.uol.testbackendjava.service.PlayerService;
import br.com.uol.testbackendjava.validator.OnCreate;
import br.com.uol.testbackendjava.validator.OnUpdate;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerGroupService playerGroupService;
	
	@GetMapping("/")
	public String list(@RequestParam(value = "search", required = false) String search, @PageableDefault(size = 10, sort = "id") Pageable pageable, Model model) {
		Page<Player> page = null;
		if (StringUtils.isEmptyOrWhitespace(search)) {
			page = playerService.findAll(pageable);
		} else {
			page = playerService.findByWord(search, pageable);
		}
		List<Sort.Order> sortOrders = page.getSort().stream().collect(Collectors.toList());
		if (sortOrders.size() > 0) {
			Sort.Order order = sortOrders.get(0);
			model.addAttribute("sortProperty", order.getProperty());
			model.addAttribute("sortDesc", order.getDirection() == Sort.Direction.DESC);
		}
		model.addAttribute("page", page);
		model.addAttribute("search", search != null ? search : "");
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
		return "redirect:/";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute @Validated({OnCreate.class}) Player player, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return add(player, model);
		}
		return save(player, model);
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated({OnUpdate.class}) Player player, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return add(player, model);
		}
		return save(player, model);
	}

	public String save(Player player, Model model) {
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
		return list(null, PageRequest.of(0, 10, Sort.by("id")), model);
	}
}
