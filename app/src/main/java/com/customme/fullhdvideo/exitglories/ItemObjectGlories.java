package com.customme.fullhdvideo.exitglories;


public class ItemObjectGlories {

    private String name;
    private int photo;
    private int position;

    public ItemObjectGlories(){

    }

    public ItemObjectGlories(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public ItemObjectGlories(String name, int photo, int pos) {
        this.name = name;
        this.photo = photo;
        this.position = pos;
    }

    public void setPosition(int pos){

        this.position = pos;
    }

    public int getPosition(){
        return position;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
