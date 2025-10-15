package com.dojan.MyGame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dojan.MyGame.MyGame;
import elementos.Texto;
import elementos.Imagen;
import utiles.Render;
import com.dojan.MyGame.pantallas.PantallaMenu;

public class PantallaElegirMapa implements Screen {

    private MyGame game;
    private SpriteBatch b;
    private Imagen fondo;

    private Texto[] opciones = new Texto[2];
    private String[] textos = {"2WD", "4WD"};
    private int opc = 0; // indica cuál opción está seleccionada
    private float tiempo = 0;

    public PantallaElegirMapa(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        b = Render.batch;

        // Fondo
        fondo = new Imagen("fondos/fondoMapas.png");
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Crear opciones de texto
        int startX = Gdx.graphics.getWidth() / 2 - 150; // posición inicial del primer texto
        int y = Gdx.graphics.getHeight() / 2;
        int gap = 300; // separación entre opciones

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new Texto("fuentes/sewer.ttf", 80, Color.WHITE);
            opciones[i].setTexto(textos[i]);
            opciones[i].setPosition(startX + i * gap, y);
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0,0,0);
        tiempo += delta;

        b.begin();
        fondo.dibujar();

        // Dibujar opciones con color según selección
        for (int i = 0; i < opciones.length; i++) {
            if (i == opc) {
                opciones[i].setColor(Color.RED); // opción seleccionada
            } else {
                opciones[i].setColor(Color.WHITE); // opción no seleccionada
            }
            opciones[i].dibujar();
        }
        b.end();

        // Controles izquierda/derecha para moverse entre opciones
        if (tiempo > 0.15f) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                opc++;
                if (opc >= opciones.length) opc = 0;
                tiempo = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                opc--;
                if (opc < 0) opc = opciones.length - 1;
                tiempo = 0;
            }
        }

        // Confirmar selección (ENTER)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Opción de tracción seleccionada: " + textos[opc]);
            // Aquí podrías iniciar la pantalla del juego y pasar la opción
            // game.setScreen(new PantallaJuego(game, textos[opc]));
        }

        // Volver al menú principal (ESCAPE)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu());
        }
    }

    @Override
    public void resize(int width, int height) {
        fondo.setSize(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
