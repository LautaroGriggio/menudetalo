package com.dojan.MyGame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dojan.MyGame.MyGame;
import com.dojan.MyGame.io.Entradas;
import elementos.Imagen;
import elementos.Texto;
import utiles.Recursos;
import utiles.Render;

public class PantallaMenu implements Screen {

    private MyGame game; // Igual que en PantallaElegirAuto
    private Imagen fondo;
    private SpriteBatch b;

    private Texto[] opciones = new Texto[4];
    private String[] textos = {"Jugar", "Online", "Opciones", "Salir"};
    private int opc = 0;
    public float tiempo = 0;

    private Entradas entradas;

    @Override
    public void show() {
        b = Render.batch;
        game = (MyGame) Gdx.app.getApplicationListener(); // Inicializamos game

        fondo = new Imagen(Recursos.FONDOMENU);
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        entradas = new Entradas(this);
        Gdx.input.setInputProcessor(entradas);

        int startY = 400;
        int gap = 90;

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new Texto(Recursos.FUENTEMENU, 80, Color.WHITE);
            opciones[i].setTexto(textos[i]);
            opciones[i].setPosition(400, startY - (i * gap));
        }
    }

    @Override
    public void render(float delta) {
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Render.limpiarPantalla(0,0,0);

        b.begin();
        fondo.dibujar();

        // Dibujar opciones y colorear la seleccion
        for (int i = 0; i < opciones.length; i++) {
            if (i == opc) opciones[i].setColor(Color.RED);
            else opciones[i].setColor(Color.WHITE);

            opciones[i].dibujar();
        }

        b.end();

        tiempo += delta;

        // Controles arriba/abajo
        if (entradas.isAbajo() && tiempo > 0.09f) {
            tiempo = 0;
            opc++;
            if (opc > 3) opc = 0;
        }
        if (entradas.isArriba() && tiempo > 0.09f) {
            tiempo = 0;
            opc--;
            if (opc < 0) opc = 3;
        }

        // Confirmar selección
        if (entradas.isEnter()) {
            if (opc == 0) {
                game.setScreen(new PantallaElegirAuto(game));
            } else if (opc == 1) {
                game.setScreen(new PantallaOnline(game));
            } else if (opc == 2) {
                game.setScreen(new PantallaElegirModo(game)); // NUEVA pantalla de modos
            } else if (opc == 3) {
                Gdx.app.exit();
            }
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
