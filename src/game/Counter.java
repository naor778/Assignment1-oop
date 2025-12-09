package game;

public class Counter {
    private int value;

    public Counter(int initialValue) {
        this.value = initialValue;
    }

    public void increase(int number) {
        this.value += number;
    }

    public void decrease(int number) {
        this.value -= number;
    }

    public int getValue() {
        return this.value;
    }

}
