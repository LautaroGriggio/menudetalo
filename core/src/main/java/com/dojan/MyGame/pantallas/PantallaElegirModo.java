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

public class PantallaElegirModo implements Screen {

    private MyGame game;       // Recibido desde el menú
    private SpriteBatch b;
    private Imagen fondo;

    private Texto[] opciones = new Texto[3];
    private String[] textos = {"F1", "Rally", "Drift"};
    private int opc = 0; // opción seleccionada
    private float tiempo = 0;

    // Constructor que recibe el juego, como en PantallaElegirAuto
    public PantallaElegirModo(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        b = Render.batch;

        // Fondo
        fondo = new Imagen("fondos/fondoOpciones.png"); // ajusta la ruta a tu fondo
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Crear opciones de texto, distribuidas horizontalmente
        int startX = Gdx.graphics.getWidth() / 2 - 300; // posición inicial del primer texto
        int y = Gdx.graphics.getHeight() / 2;           // altura central
        int gap = 300;                                   // separación entre opciones

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new Texto("fuentes/sewer.ttf", 80, Color.WHITE);
            opciones[i].setTexto(textos[i]);
            opciones[i].setPosition(startX + i * gap, y);
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);
        tiempo += delta;

        b.begin();
        fondo.dibujar();

        // Dibujar opciones con color según selección
        for (int i = 0; i < opciones.length; i++) {
            if (i == opc) opciones[i].setColor(Color.RED);
            else opciones[i].setColor(Color.WHITE);

            opciones[i].dibujar();
        }
        b.end();

        // Controles de selección con tiempo para evitar cambios rápidos
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
            System.out.println("Modo de juego seleccionado: " + textos[opc]);
            // Aquí podés iniciar la pantalla del juego, p.ej:
            // game.setScreen(new PantallaJuego(game, textos[opc]));
        }

        // Volver al menú (ESCAPE)
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
