public abstract class Notes implements Cloneable {
    String title;
    String bodyOfNote;
    User author;

    public Notes(String title, User author, String bodyOfNote) {
        this.title = title;
        this.author = author;
        this.bodyOfNote = bodyOfNote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyOfNote(String bodyOfNote) {
        this.bodyOfNote = bodyOfNote;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    protected Notes clone() throws CloneNotSupportedException {
        return (Notes) super.clone();
    }

    public String toString() {
        return (title == null ? "" : "\nНазвание заметки: " + title) + "\nАвтор заметки: " + author +
                (bodyOfNote == null ? "\n" : "\nСодержание: " + bodyOfNote + "\n");
    }
}
