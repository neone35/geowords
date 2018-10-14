# Geowords

## Overview
Allows to associate user input with markers on the map. Saves search history to repeat query and set/edit marker with long press on the map.

## Why this project
This is a technical task provided by "MR Digital Group" (GPSWOX) for Junior Android Developer position. The exact requirements were as follows:
1. Create 1st screen (activity) with appbar which contains input box and displays earlier search history in dropdown.
   * Dropdown item must contain word, partOfSpeech & time of search;
   * Click on the dropdown item invokes new call to API and opens second (detail) activity;
2. Create 2nd screen to display search details separated into two halfs of the screen:
   * top half contains WebView with Word API search output;
   * bottom half contains GoogleMap. Long press on the map places marker.
   * appbar must contain option to switch between default and one of the 5 custom icons (random) imported from material.io.
   * press on the marker opens custom InfoWindow which contains definition and partOfSpeech of the current word.
3. Additional (bonus) requirements:
   * search history persistence between app restarts.
   * material design guidelines.
   * layouts for tablets and/or landscape orientation.
   * MVVM/MVP or any other architecture.
   * clear project structure.

## Screenshots
coming soon

## What Did I Learn / Use?
- [Words API](https://www.wordsapi.com/docs/?javascript#introduction)
- MVP architecture pattern
- RxJava
> Including permissions & location