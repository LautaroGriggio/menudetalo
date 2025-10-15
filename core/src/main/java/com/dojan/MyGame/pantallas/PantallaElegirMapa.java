package com.dojan.MyGame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dojan.MyGame.MyGame;
import elementos.Imagen;
import utiles.Render;

public class PantallaElegirMapa implements Screen {

    private MyGame game; // referencia al juego (igual que en tus otras pantallas)
    private SpriteBatch b;
    private Imagen fondo;

    // Array de mapas
    private Imagen[] mapas;
    private int indiceMapa = 0;

    // Tiempo para controlar el cambio (evita repetición muy rápida)
    private float tiempo = 0;

    public PantallaElegirMapa(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        b = Render.batch;

        // Fondo (ajustá la ruta si tu archivo se llama distinto)
        fondo = new Imagen("fondos/fondoMapas.png");
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Cargar mapas (rutas de ejemplo - pon tus archivos en assets/mapas/)
        mapas = new Imagen[]{
                new Imagen("mapas/mapa1.png"),
                new Imagen("mapas/mapa2.png"),
                new Imagen("mapas/mapa3.png")
        };

        // Asegurarse que el sprite del mapa inicial esté centrado al mostrarse
        centrarMapaActual();
    }

    private void centrarMapaActual() {
        Imagen actual = mapas[indiceMapa];
        float ancho = Gdx.graphics.getWidth() * 0.6f;
        float alto = Gdx.graphics.getHeight() * 0.6f;
        float x = (Gdx.graphics.getWidth() - ancho) / 2f;
        float y = (Gdx.graphics.getHeight() - alto) / 2f;
        actual.setSize(ancho, alto);
        actual.s.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);
        tiempo += delta;

        b.begin();
        fondo.dibujar();

        // Dibujo del mapa actual (centrado)
        Imagen actual = mapas[indiceMapa];
        // en cada frame nos aseguramos que el tamaño/posición sean correctos
        float ancho = Gdx.graphics.getWidth() * 0.6f;
        float alto = Gdx.graphics.getHeight() * 0.6f;
        float x = (Gdx.graphics.getWidth() - ancho) / 2f;
        float y = (Gdx.graphics.getHeight() - alto) / 2f;
        actual.setSize(ancho, alto);
        actual.s.setPosition(x, y);
        actual.dibujar();

        b.end();

        // Controles: usar isKeyJustPressed para un único cambio por pulsación
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            indiceMapa++;
            if (indiceMapa >= mapas.length) indiceMapa = 0;
            tiempo = 0;
            centrarMapaActual();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            indiceMapa--;
            if (indiceMapa < 0) indiceMapa = mapas.length - 1;
            tiempo = 0;
            centrarMapaActual();
        }

        // Confirmar selección (ENTER)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Mapa seleccionado: " + indiceMapa);
            // Aquí podrías llamar a la pantalla de juego, por ejemplo:
            // game.setScreen(new PantallaJuego(game, indiceMapa));
        }

        // Volver al menú (ESCAPE)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu());
        }
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el tamaño del fondo si se redimensiona
        fondo.setSize(width, height);
        // re-centrar el mapa actual
        centrarMapaActual();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        // Si implementás dispose() en Imagen podés liberar recursos acá.
        // Por ahora no llamo nada porque en tu clase Imagen no hay dispose().
    }
}


