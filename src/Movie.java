public class Movie extends Notes implements Cloneable {
    public Movie(String title, User author, String bodyOfNote) {
        super(title, author, bodyOfNote);
    }

    @Override
    protected Movie clone() throws CloneNotSupportedException {
        return (Movie) super.clone();
    }
}
