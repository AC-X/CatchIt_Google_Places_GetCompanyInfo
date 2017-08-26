package com.breezemaxweb.catchitcapturecompany;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.StringTokenizer;

public class CaptureProspect extends AppCompatActivity {
    //VERSION 1  -- FULL ADDRESS
    private final String TAG = "ADD_COMPANY";
    private final int PLACE_AUTOCOMPLETE_REQUEST = 1001;

    String address, web, phone, address1, address2, tmppost, company, city, country, zipcode, state;
    EditText companyText, addressText, address1Text, cityText, stateText, zipcodeText, countryText, companyPhoneText, companyWebText;
    Button nextStepBtn;
    Button startup;

//********************************PLACES API V1 *******************************************************
    // Called when the user clicks the Company Name EditText
    // in the main activity. Invokes the Autocomplete UI
    protected void selectAPlace() {
        try {
            Intent intent;

            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);

            // Start the Autocomplete Activity. the result will be returned
            // in the onActivityResult function
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST);
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    // Updates the TextView in the main Activity with the selected Place details
    private void updateUI(Place chosenPlace) {


        if (chosenPlace == null) {
            //placeData.setText("No place selected");
        }
        else {
//IF any of the .toString() come back null, the app will fail, unless checked.
            company = chosenPlace.getName().toString();
            if(chosenPlace.getAddress() != null) {
                address = chosenPlace.getAddress().toString();
            }
            phone = chosenPlace.getPhoneNumber().toString();

            if (chosenPlace.getWebsiteUri() != null) {
                web = chosenPlace.getWebsiteUri().toString();
            }


            //Breaks up / Parses address String.  Not All Addresses Set Up The Same Or Are
            // Missing Data. countTokens is filtering based on a certain number of items.
            // 4 =  Address, City, Province, Postal Code*, Country   *Which is 2 items has to
            // be reconnectedas one item.
            //Filtering Based on country, may be required.

            StringTokenizer tokens = new StringTokenizer(address, ",");
            if (tokens.countTokens() == 4) {

                address1 = tokens.nextToken();
                city = tokens.nextToken();
                tmppost = tokens.nextToken();
                country = tokens.nextToken();

                StringTokenizer tokens2 = new StringTokenizer(tmppost, " ");
                //IF statement checks for non Canadian postal code, base on # of spaces in tmppost
                //3 spaces (ON L5X 1X1) is the correct format.
                if (tokens2.countTokens() == 3){
                    state = tokens2.nextToken();
                    zipcode = tokens2.nextToken() + " " + tokens2.nextToken();
                }

                else{

                    state = tokens2.nextToken();
                    zipcode = tokens2.nextToken();
                }

                companyText.setText(company);
                addressText.setText(address1);
                cityText.setText(city);
                stateText.setText(state);
                countryText.setText(country);
                zipcodeText.setText(zipcode);
                companyPhoneText.setText(phone);
                companyWebText.setText(web);
            }
//Works on BreezeMaxWeb look up
            else if (tokens.countTokens() == 5){
                address2 = tokens.nextToken();
                address1 = tokens.nextToken();
                city = tokens.nextToken();
                tmppost = tokens.nextToken();
                country = tokens.nextToken();
                StringTokenizer tokens2 = new StringTokenizer(tmppost, " ");
                state = tokens2.nextToken();
                zipcode = tokens2.nextToken() + " " + tokens2.nextToken();


                companyText.setText(company);
                addressText.setText(address1);
                address1Text.setText(address2);
                cityText.setText(city);
                countryText.setText(country);
                stateText.setText(state);
                zipcodeText.setText(zipcode);
                companyPhoneText.setText(phone);
                companyWebText.setText(web);

            }

            else{
                companyText.setText(company);
                companyPhoneText.setText(phone);
                companyWebText.setText(web);
                addressText.setText(address);
            }



        }
    }
//*************************************END PLACES API v 1.0 ****************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_prospect);

        // RENAME ACCORDINGLY ALL EDITTEXTS -- Match Up With What You Have
        companyText = (EditText) findViewById(R.id.companyText);
        addressText = (EditText) findViewById(R.id.addressText);
        address1Text = (EditText) findViewById(R.id.address1Text);
        cityText = (EditText) findViewById(R.id.cityText);
        zipcodeText = (EditText) findViewById(R.id.zipcodeText);
        stateText = (EditText) findViewById(R.id.stateText);
        countryText = (EditText) findViewById(R.id.countryText);
        companyWebText = (EditText) findViewById(R.id.companyWebText);
        companyPhoneText = (EditText) findViewById(R.id.companyPhoneText);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
        startup = (Button) findViewById(R.id.newcomp_button);

        startup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                companyText.setOnClickListener(null);
                startup.setTextColor(Color.BLACK);
            }
        });



        companyText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //companyText.setOnClickListener(null);
                //Activates The PlacePicker API
                selectAPlace();

            }
        });

//Listening to button event to continue  Also Verifies the company NAME and CITY are not null. OPTIONAL
        nextStepBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent.
                company = companyText.getText().toString();
                city = cityText.getText().toString();

                if( city.matches("") || company.matches(" ") ) {
                    //Post error message asking to fill in boxes.  User has minimum requirements
                    // per page.  Requires a city and company name
                    new ErrorMissingContent_Fragment().show(getFragmentManager(), "Error");

                }
                else{
                    //Move to next page.
                    //Starting a new Intent.
                    Intent nextScreen = new Intent(getApplicationContext(), CaptureProspect2.class);



                    startActivity(nextScreen);
                    finish();

                }

            }
        });
    }
    //*************************** PLACES API ERROR CHECK FUNCTION***********************************
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());

                updateUI(place);
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status.toString());
            }
            else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
                updateUI(null);
            }
        }




    }
}
