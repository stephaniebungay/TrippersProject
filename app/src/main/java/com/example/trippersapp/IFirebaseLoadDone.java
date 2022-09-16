package com.example.trippersapp;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Recommendation> recommendationList);
    void onFirebaseLoadFailed(String message);
}
