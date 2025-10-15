package com.dojan.MyGame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dojan.MyGame.MyGame;
import elementos.Imagen;
import utiles.Render;

public class PantallaOnline implements Screen {

    private MyGame game;
    private SpriteBatch b;
    private Imagen fondo;

    private BitmapFont font;
    private GlyphLayout layout;
    private StringBuilder nombreJugador = new StringBuilder();

    public PantallaOnline(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        b = Render.batch;

        fondo = new Imagen("fondos/fondoOnline.png");
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont(); // fuente básica
        layout = new GlyphLayout();

        // Configurar input para escribir directamente
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (character >= 32 && character <= 126 && nombreJugador.length() < 12) {
                    nombreJugador.append(character);
                }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                // Borrar con BACKSPACE
                if (keycode == Input.Keys.BACKSPACE && nombreJugador.length() > 0) {
                    nombreJugador.deleteCharAt(nombreJugador.length() - 1);
                }

                // Confirmar con ENTER
                if (keycode == Input.Keys.ENTER) {
                    System.out.println("Nombre confirmado: " + nombreJugador.toString());
                    // game.setScreen(new PantallaLobby(game, nombreJugador.toString()));
                }

                // Volver al menú
                if (keycode == Input.Keys.ESCAPE) {
                    Gdx.input.setInputProcessor(null);
                    game.setScreen(new PantallaMenu());
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);

        b.begin();
        fondo.dibujar();

        // Texto centrado manualmente con GlyphLayout
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        // "Ingrese su nombre"
        layout.setText(font, "Ingrese su nombre:");
        font.draw(b, layout, centerX - layout.width / 2, centerY + 100);

        // Nombre que el jugador escribe
        layout.setText(font, nombreJugador.toString());
        font.draw(b, layout, centerX - layout.width / 2, centerY);

        // Instrucciones
        layout.setText(font, "(ENTER para confirmar, ESC para volver)");
        font.draw(b, layout, centerX - layout.width / 2, 100);

        b.end();
    }

    @Override
    public void resize(int width, int height) {
        fondo.setSize(width, height);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
}
