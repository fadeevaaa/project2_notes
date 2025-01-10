public class Purchace extends Notes implements Cloneable {
    public Purchace(String title, User author, String bodyOfNote) {
        super(title, author, bodyOfNote);
    }

    @Override
    protected Purchace clone() throws CloneNotSupportedException {
        return (Purchace) super.clone();
    }
}
