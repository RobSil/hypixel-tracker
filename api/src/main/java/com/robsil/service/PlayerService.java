package com.robsil.service;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.data.repo.PlayerRepository;
import com.robsil.model.PlayerCreationDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Log4j2
@NoArgsConstructor
public class PlayerService {

    @Value("${mojang.apiUrl}")
    private String mojangApiUrl;

    @Value("${hypixel.apiUrl}")
    private String hypixelApiUrl;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ProfileService profileService;

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player getById(String id) {
        Player player = playerRepository.findById(id).orElse(null);

        if (player != null) {
            return player;
        } else {
            log.warn("PlayerService: can't find Player with such an ID: " + id);

            return null;
        }
    }

    public Player getByUuid(String uuid) {
        Player player = playerRepository.findByUuid(uuid).orElse(null);

        if (player != null) {
            return player;
        } else {
            log.warn("PlayerService: can't find player with such a uuid: " + uuid);

            return null;
        }
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player create(PlayerCreationDto playerCreationDto) {

        Player player = fetchPlayer(playerCreationDto);

//        return save(player);
        player = save(player);

        if (player.getId() != null && !(player.getId().isBlank())) {

            //todo: fetch profiles and save them.
        }

        return player;
    }

    private Player fetchPlayer(PlayerCreationDto playerCreationDto) {

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(mojangApiUrl + "/users/profiles/minecraft/" + playerCreationDto.getNickname()))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                                                        HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();

                JSONObject jsonObject = new JSONObject(body);

                String nickname = jsonObject.getString("name");
                String id = jsonObject.getString("id");

                return Player.builder().uuid(id).nickname(nickname).build();
            } else if (response.statusCode() == 204) {
                return null;
            } else {
                log.warn("playerService fetchPlayer: something went wrong.");
            }
        } catch (Exception e) {
            e.printStackTrace();

            log.warn("playerService fetchPlayer: something went wrong.");
            return null;
        }

        return null;
    }

    public void deleteById(String id) {
        playerRepository.deleteById(id);
    }

    public void deleteByHypixelId(String hypixelId) {
        playerRepository.deleteByUuid(hypixelId);
    }

    public List<Profile> updatePlayerProfiles(String playerId) {

        Player player = getById(playerId);

        if (player != null) {
            return profileService.updatePlayerProfiles(player.getUuid());
        } else {
            return null;
        }
    }

    public void deleteAll() {
        playerRepository.deleteAll();
    }
}
