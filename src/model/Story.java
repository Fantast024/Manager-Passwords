package model;

import javafx.beans.property.SimpleStringProperty;

public class Story {
    public SimpleStringProperty usernameProperty() {
        return username;
    }
    public SimpleStringProperty passwordProperty() {
        return password;
    }
    SimpleStringProperty username;
    SimpleStringProperty password;
    SimpleStringProperty comment;
    SimpleStringProperty date;

    public String getComment() {
        return comment.get();
    }
    public void setComment(String comment) {
        this.comment.set(comment);
    }
    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }

    public Story( String username, String password, String comment, String date) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.comment = new SimpleStringProperty(comment);
        this.date = new SimpleStringProperty(date);
    }
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getPassword(){return  password.get();}
    public void setPassword(String password){this.password.set(password);}
}
