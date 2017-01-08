import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 6/23/15.
 */
public class State {
    private String state;
    private int population;
    private final List<String> cities = new ArrayList<>();

    public State() {
    }

    public State(String state, int population) {
        this.state = state;
        this.population = population;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void increasePopulation(int population) {
        this.population += population;
    }

    public List<String> getCities() {
        return cities;
    }

    public void addCity(String city) {
        cities.add(city);
    }
}
