package ru.mamapapa.task8;

public class Note {
    private String id;
    private String title;
    private String body;

    public Note() {
    }

    public Note(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
