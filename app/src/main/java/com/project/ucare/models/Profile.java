package com.project.ucare.models;

public class Profile {

    String id;
    String parent_id;
    String name;
    String birth_date;
    String gender;

    public Profile() {
    }

    public Profile(String id, String parent_id, String name, String birth_date, String gender) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.birth_date = birth_date;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
