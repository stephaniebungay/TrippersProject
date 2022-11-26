package com.example.trippersapp;

import android.util.Log;

import com.google.gson.Gson;

    public class Logger {

        private static final String FILTER = "mLOG";
        private static final String VIEW = "APP VIEW";


        /**
         * @param tag       The tag is the name of class or activity
         * @param title     The title is the name of method name.
         * @param message   Information or Error message
         */
        public static void LogView (String tag,String title,String message){
            Log.d(FILTER + " -> " + VIEW + " -> " + tag,
                    title + " -> " + message);


        }

        /**
         * @param tag       The tag is the name of class or activity
         * @param title     The title is the name of method name.
         * @param message   Information or Error message
         */
        public static void LogError (String tag,String title,String message){
            Log.e(FILTER + " -> " + VIEW + " -> " + tag,
                    title + " -> " + message);

        }


        public static String jsonConverter(Object object){
            return new Gson().toJson(object);
        }
    }

