package com.example.emosensor.savedData;

import java.util.function.BiPredicate;

public class EmoWords {
    String time;
    String words;
    Boolean user;

    public EmoWords(String time, String words, Boolean user){
        this.time = time;
        this.words = words;
        this.user = user;
    }
    public String getTime(){
        return time;
    }

    public String getWords(){
        return words;
    }

    public Boolean getUser(){
        return user;
    }
}
