
public class Square {
    private boolean hasQueen;
    private boolean available;


    public Square() {
        this.available=true;
        this.hasQueen=false;
    }

    public Square(boolean hasQueen, boolean available) {
        this.hasQueen = hasQueen;
        this.available = available;
    }

    public boolean isHasQueen() {
        return hasQueen;
    }

    public void setHasQueen(boolean hasQueen) {
        this.hasQueen = hasQueen;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Square{" +
                "hasQueen=" + hasQueen +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return hasQueen == square.hasQueen &&
                available == square.available;
    }

}
