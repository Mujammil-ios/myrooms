package com.mj.myrooms.object;

public class TabModel {
    private String id;
    private String name;
    private int count;
    private int image;
    private int imageSelected;
    private boolean select;

    public TabModel(String name, int image, int imageSelected) {
        this.name = name;
        this.image = image;
        this.imageSelected = imageSelected;
    }

    public TabModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public TabModel(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageSelected() {
        return imageSelected;
    }

    public void setImageSelected(int imageSelected) {
        this.imageSelected = imageSelected;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}

