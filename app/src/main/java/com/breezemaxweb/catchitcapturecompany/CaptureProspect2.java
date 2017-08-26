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

public class CaptureProspect2 extends AppCompatActivity {
    //VERSION 1  -- FULL ADDRESS
    private final String TAG = "ADD_COMPANY_v2";
    private final int PLACE_AUTOCOMPLETE_REQUEST = 1002;

    String address, web, phone, address1, company;
    EditText companyText, addressText, companyPhoneText, companyWebText;
    Button nextStepBtn;
    Button startup;


    //VERSION 1  -- FULL ADDRESS
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
            //NOTE ADDRESS CONTAINS CITY
            company = chosenPlace.getName().toString();
            if(chosenPlace.getAddress() != null) {
                address = chosenPlace.getAddress().toString();
            }
            phone = chosenPlace.getPhoneNumber().toString();

            if (chosenPlace.getWebsiteUri() != null) {
                web = chosenPlace.getWebsiteUri().toString();
            }


                companyText.setText(company);
                addressText.setText(address);
                companyPhoneText.setText(phone);
                companyWebText.setText(web);
            }



        }


    /**
     * Standard Android Activity lifecycle methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_prospect2);

        // RENAME ACCORDINGLY ALL EDITTEXTS -- Match Up With What You Have
        companyText = (EditText) findViewById(R.id.companyText);
        addressText = (EditText) findViewById(R.id.addressText);
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

            public void onClick(final View arg0) {
                //Activates The PlacePicker API
                selectAPlace();

            }
        });

        //Listening to button event to continue  Also Verifies the company NAME and CITY are not null. OPTIONAL
        nextStepBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent.
                company = companyText.getText().toString();



                if( company.matches(" ") ) {
                    //Post error message asking to fill in boxes.  User has minimum requirements
                    // per page.  Requires a city and company name
                    new ErrorMissingContent_Fragment().show(getFragmentManager(), "Error");

                }
                else{
                    //Move to next page.
                    //Starting a new Intent.
                    Intent nextScreen = new Intent(getApplicationContext(), CaptureProspect.class);


                    // starting new activity Location Activity
                    startActivity(nextScreen);

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
