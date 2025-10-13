package com.dojan.MyGame;

import com.badlogic.gdx.Game;
import com.dojan.MyGame.pantallas.PantallaMenu;

public class MyGame extends Game {

    @Override
    public void create() {
        // Arranca mostrando el menú
        setScreen(new PantallaMenu());
    }

    @Override
    public void render() {
        super.render(); // delega en la pantalla actual (PantallaMenu)
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
