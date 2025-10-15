package com.dojan.MyGame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dojan.MyGame.MyGame;
import elementos.Imagen;
import utiles.Render;



public class PantallaElegirAuto implements Screen {

    private MyGame game; // referencia al juego
    private SpriteBatch b;
    private Imagen fondo;

    // Array de autos
    private Imagen[] autos;
    private int indiceAuto = 0;

    // Tiempo para controlar el cambio (evita repetición muy rápida)
    private float tiempo = 0;

    public PantallaElegirAuto(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        b = Render.batch;

        // Fondo
        fondo = new Imagen("fondos/fondoAutos.png");
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Cargar autos
        autos = new Imagen[]{
                new Imagen("autos/auto1.png"),
                new Imagen("autos/auto2.png"),
                new Imagen("autos/auto3.png")
        };
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);
        tiempo += delta;

        // Dibujar fondo y auto actual
        b.begin();
        fondo.dibujar();

        Imagen actual = autos[indiceAuto];
        float ancho = actualWidth(actual);
        float alto = actualHeight(actual);
        float x = (Gdx.graphics.getWidth() - ancho) / 2;
        float y = (Gdx.graphics.getHeight() - alto) / 2;

        actual.setSize(ancho, alto);
        actual.s.setPosition(x, y);
        actual.dibujar();
        b.end();

        // Controles izquierda / derecha
        if (tiempo > 0.15f) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                indiceAuto++;
                if (indiceAuto >= autos.length) indiceAuto = 0;
                tiempo = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                indiceAuto--;
                if (indiceAuto < 0) indiceAuto = autos.length - 1;
                tiempo = 0;
            }
        }

        // Confirmar selección (ENTER) → pasa a PantallaElegirMapa
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new PantallaElegirMapa(game));
        }

        // Volver al menú (ESCAPE)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu());
        }
    }

    private float actualWidth(Imagen img) {
        return Gdx.graphics.getWidth() * 0.5f;
    }

    private float actualHeight(Imagen img) {
        return Gdx.graphics.getHeight() * 0.5f;
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
