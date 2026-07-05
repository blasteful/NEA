package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Audio {

        boolean muted = false;

        Sound click;
        Sound hover;


        public Audio(boolean muted) {
            this.muted = muted;
            click = Gdx.audio.newSound(Gdx.files.internal("Sounds/click.wav"));
            hover = Gdx.audio.newSound(Gdx.files.internal("Sounds/hover.wav"));
        }

        public void click() {
            if(!muted) {
                click.play();
            }
        }
        public void hover() {
            if(!muted) {
                hover.play();
            }

        }

        public void dispose() {
            click.dispose();
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }
    }
