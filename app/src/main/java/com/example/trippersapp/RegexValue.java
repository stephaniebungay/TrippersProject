package com.example.trippersapp;

import android.content.Context;

public class RegexValue {

        private Context context;

        public static final String MOBILENUMBER = "(^(09|\\+639|639)\\d{9})|(^(861)[0-9]*)$";
        public static final String EMAIL = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
        public static final String USERNAME = "^[a-zA-Z0-9_]*$";

}
