package br.com.uol.testbackendjava.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.model.PlayerGroup;
import br.com.uol.testbackendjava.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository repository;
	
	public Page<Player> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Optional<Player> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Player save(Player player) {
		return repository.save(player);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Page<Player> findByWord(String word, Pageable pageable) {
		return repository.findByWord("%"+word.toLowerCase()+"%", pageable);
	}
	
	public boolean exists(String field, String value) {
		if (field.equals("name")) {
			return repository.existsByName(value);
		}
		if (field.equals("email")) {
			return repository.existsByEmail(value);
		}
		if (field.equals("telephone")) {
			return repository.existsByTelephone(value);
		}
		return false;
	}
		
	public String getCodename(PlayerGroup playerGroup) {
		String codename = "";
		List<String> codenameList = new ArrayList<String>();
		URL url = null;
		HttpURLConnection urlConnection = null;
		
		try {
			url = new URL(playerGroup.getUrl());
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
	
			if (playerGroup.getUrl().endsWith("xml")) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputStream);
				NodeList nodes = doc.getElementsByTagName("codinome");
	
				for (int i = 0; i < nodes.getLength(); i++) {
					codenameList.add(nodes.item(i).getFirstChild().getTextContent());
				}
			}
	
			if (playerGroup.getUrl().endsWith("json")) {
				JsonParser jp = new JsonParser();
				JsonElement root = jp.parse(new InputStreamReader(inputStream));
				JsonObject rootobj = root.getAsJsonObject();
				JsonArray vingadores = rootobj.get("vingadores").getAsJsonArray();
	
				for (JsonElement pa : vingadores) {
					JsonObject codinome = pa.getAsJsonObject();
					codenameList.add(codinome.get("codinome").getAsString());
				}
			}
			
			inputStream.close();
			
			List<Player> playerList = repository.findByPlayerGroup(playerGroup);
			
			if (playerList != null && !playerList.isEmpty()) {
				List<String> codenamePlayers = playerList.stream().map(p -> p.getCodename()).collect(Collectors.toList());
				codenameList = codenameList.stream().filter(c -> !codenamePlayers.contains(c)).collect(Collectors.toList());
			}
			
			if (!codenameList.isEmpty()) {
				codename = codenameList.get(0);
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return codename;
	}
	
}