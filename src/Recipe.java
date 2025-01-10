import java.util.NoSuchElementException;

public class Recipe extends Notes implements Cloneable, Correctable{
    public Recipe(String title, User author, String bodyOfNote) {
        super(title, author, bodyOfNote);
    }

    @Override
    public void correct(String currentWord, String newWord) {
        boolean flag = false;
        String[] array = this.bodyOfNote.split(" ");
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(currentWord)) {
                array[i] = newWord;
                flag = true;
                break;
            }
        }
        if (!flag) throw new NoSuchElementException("\nУказанное слово не найдено\n");
        this.bodyOfNote = String.join(" ", array);
    }

    @Override
    protected Recipe clone() throws CloneNotSupportedException {
        return (Recipe) super.clone();
    }
}
