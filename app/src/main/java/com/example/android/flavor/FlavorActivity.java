/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.flavor;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * {@link FlavorActivity} This shows an array of AndroidFlavor objects, each object is created from the constructor
 * of the AndroidFlavor class
 * For each release, display the name, version number, and image.
 */
public class FlavorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavor);


        // Create an ArrayList of AndroidFlavor objects
        final ArrayList<AndroidFlavor> androidFlavors = new ArrayList<AndroidFlavor>();
        androidFlavors.add(new AndroidFlavor("Dog", "Euro 22.50", R.drawable.dog));
        androidFlavors.add(new AndroidFlavor("Cat", "Euro 12.40", R.drawable.cat));
        androidFlavors.add(new AndroidFlavor("Budgie", "Euro 5.00", R.drawable.budgie));
        androidFlavors.add(new AndroidFlavor("Rabbit", "Euro 6.00", R.drawable.rabbit));
        androidFlavors.add(new AndroidFlavor("Pony", "Euro 67.00", R.drawable.pony));
        androidFlavors.add(new AndroidFlavor("Peacock", "Euro 28.50", R.drawable.peacock));
        androidFlavors.add(new AndroidFlavor("Pig", "Euro 35.00", R.drawable.pig));
        androidFlavors.add(new AndroidFlavor("Hamster", "Euro 8.25", R.drawable.hamster));
        androidFlavors.add(new AndroidFlavor("Lamb", "Euro 22.00", R.drawable.lamb));
        androidFlavors.add(new AndroidFlavor("Snake", "Euro 31.00", R.drawable.snake));

        // Create an {@link AndroidFlavorAdapter}, whose data source is a list of
        // {@link AndroidFlavor}s. The adapter knows how to create list item views for each item
        // in the list.
        AndroidFlavorAdapter flavorAdapter = new AndroidFlavorAdapter(this, androidFlavors);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_flavor);
        listView.setAdapter(flavorAdapter);

        // Seting up setOnItemClickListener on the listView.
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            // When an item in the listView is clicked the onItemClick returns 'parent, view, int and id (number in the listView) of the item clicked
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // The following uses the 'id' of the listView item clicked to identify the selected object in the Arraylist 'androidFlavors'
                // and uses the getter method of that object to return the  name of the selected pet.
                String nameOfItemClicked = androidFlavors.get((int) id).getVersionName();
                String toastText = nameOfItemClicked;
                // The Toast below then displayes toastText (name of the selected pet)
                Toast.makeText(FlavorActivity.this, toastText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}