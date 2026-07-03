package io.github.some_example_name;

public class Formulas {



    public float pressure(int hp, float total_cash, int towers, int wave, float currentcash) {
        float prediction = predicted_power(wave);
        float pressure_val = (float) (((100f - hp) / 100 * 0.3) + ((currentcash / total_cash) / 100f * 0.3f));
        return(pressure_val);

    }

    public float predicted_power(int wave) {
        float val = wave * 1.1f;
        return(val);
    }

}
