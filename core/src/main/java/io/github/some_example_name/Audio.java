package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Audio {

        boolean muted = false;

        Sound click;
        Sound hover;
        Sound wavestart;
        Sound footsteps;
        Sound turret;
        Sound detonator;
        Sound spire;
        Sound wavecomplete;
        Sound footsteps2;
        Sound buy;
        Sound back;
        Sound losehp;

        public Audio(boolean muted) {
            this.muted = muted;
            wavecomplete = Gdx.audio.newSound(Gdx.files.internal("Sounds/wavecomplete.wav"));
            click = Gdx.audio.newSound(Gdx.files.internal("Sounds/click.wav"));
            hover = Gdx.audio.newSound(Gdx.files.internal("Sounds/hover.wav"));
            wavestart = Gdx.audio.newSound(Gdx.files.internal("Sounds/wavestart.wav"));
            footsteps = Gdx.audio.newSound(Gdx.files.internal("Sounds/footsteps.wav"));
            turret = Gdx.audio.newSound(Gdx.files.internal("Sounds/turret.wav"));
            detonator = Gdx.audio.newSound(Gdx.files.internal("Sounds/detonator.wav"));
            spire = Gdx.audio.newSound(Gdx.files.internal("Sounds/spire.wav"));
            footsteps2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/footsteps2.wav"));
            buy = Gdx.audio.newSound(Gdx.files.internal("Sounds/buy.wav"));
            back = Gdx.audio.newSound(Gdx.files.internal("Sounds/back.wav"));
            losehp = Gdx.audio.newSound(Gdx.files.internal("Sounds/losehp.wav"));
        }

        public void click() {
            if(!muted) {
                click.play(0.5f);
            }
        }

        public void losehp() {
            if(!muted) {
                losehp.play(0.5f);
            }
        }

        public void back() {
            if(!muted) {
                back.play(0.5f);
            }
        }

        public void footsteps2() {
            if(!muted) {
                footsteps2.play(0.2f);
            }
        }

        public void buy() {
            if(!muted) {
                buy.play(0.2f);
            }
        }

        public void wavecomplete() {
            if(!muted) {
                wavecomplete.play(0.5f);
            }
        }

        public void spire() {
            if(!muted) {
                spire.play(0.1f);
            }
        }

        public void hover() {
            if(!muted) {
                hover.play();
            }
        }

        public void wavestart() {
            if(!muted) {
                wavestart.play(0.4f);
            }
        }

        public void turret() {
            if(!muted) {
                turret.play(0.2f);
            }
        }

        public void footsteps() {
            if(!muted) {
                footsteps.play(0.2f);
            }
        }

        public void detonator() {
            if(!muted) {
                detonator.play(0.2f);
            }
        }

        public void dispose() {
            click.dispose();
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }
    }
