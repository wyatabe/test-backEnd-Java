package br.com.uol.testbackendjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import br.com.uol.testbackendjava.controller.PlayerController;
import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.model.PlayerGroup;
import br.com.uol.testbackendjava.service.PlayerGroupService;
import br.com.uol.testbackendjava.service.PlayerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerControllerUnitTest {

    private static PlayerController playerController;
    private static PlayerService mockedPlayerService;
    private static PlayerGroupService mockedPlayerGroupService;
    private static BindingResult mockedBindingResult;
    private static Model mockedModel;

    @BeforeClass
    public static void setUpPlayerControllerInstance() {
        mockedPlayerService = mock(PlayerService.class);
        mockedPlayerGroupService = mock(PlayerGroupService.class);
        mockedBindingResult = mock(BindingResult.class);
        mockedModel = mock(Model.class);
        playerController = new PlayerController(mockedPlayerService, mockedPlayerGroupService);
    }

    @Test
    public void whenCalledCreateAndValidPlayer_thenCorrect() {
    	PlayerGroup playerGroup = new PlayerGroup(1);
    	Player player = new Player(0, "Wagner", "kenji.yatabe@gmail.com", "(11) 97374-6730", playerGroup);
        when(mockedBindingResult.hasErrors()).thenReturn(false);
        when(mockedPlayerService.getCodename(playerGroup)).thenReturn("Batman");
        assertThat(playerController.create(player, mockedBindingResult, mockedModel)).isEqualTo("player-list");
    }

    @Test
    public void whenCalledCreateAndInValidPlayer_thenCorrect() {
    	PlayerGroup playerGroup = new PlayerGroup(1);
    	Player player = new Player(0, "Wagner", "kenji.yatabe@gmail.com", "(11) 97374-6730", playerGroup);
        when(mockedBindingResult.hasErrors()).thenReturn(true);
        when(mockedPlayerService.getCodename(playerGroup)).thenReturn("Batman");
        assertThat(playerController.create(player, mockedBindingResult, mockedModel)).isEqualTo("player-form");
    }

    @Test
    public void whenCalledUpdateUserAndValidPlayer_thenCorrect() {
    	Player player = new Player(1, "Wagner", "kenji.yatabe@gmail.com", "(11) 97374-6730", new PlayerGroup(1), "Batman");
        when(mockedBindingResult.hasErrors()).thenReturn(false);
        assertThat(playerController.update(player, mockedBindingResult, mockedModel)).isEqualTo("player-list");
    }

    @Test
    public void whenCalledUpdateUserAndInValidPlayer_thenCorrect() {
    	Player player = new Player(1, "", "kenji.yatabe@gmail.com", "(11) 97374-6730", new PlayerGroup(1), "Batman");
        when(mockedBindingResult.hasErrors()).thenReturn(true);
        assertThat(playerController.update(player, mockedBindingResult, mockedModel)).isEqualTo("player-form");
    }
    
    @Test
    public void whenCalledDelete_thenCorrect() {
        assertThat(playerController.delete(1l, mockedModel)).isEqualTo("redirect:/");
    }
    
}