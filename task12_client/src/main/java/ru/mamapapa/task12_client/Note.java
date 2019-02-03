package ru.mamapapa.task12_client;

public class Note {
    public static final String ID_FIELD_NAME = "id";
    public static final String TITLE_FIELD_NAME = "title";
    public static final String BODY_FIELD_NAME = "body";

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
