Installations Instructions:

**Goal is to have the Google Places API launch as soon as the user taps the box to enter the company name. This Readme runs through 
the set up and install, with hopes that you can literally plug and play.  You will have to rename edittext fields accordingly.

NOTES: 99% of companies exist. Which means they will be listed by google in some form.  If they can't be found its a HIGH chance the user
entered the name wrong. For example Iplan Legal.  Look that up and it does not exist.  Yet There logo/sign is IPLANLEGAL.  type it in that way
and they come up. Not sure if we can add a "tips" or help section in regards to this.  On an extreme case, such as a brand new company, there is a
"Start Up" button.  This will relaunch the view and disable the API, so you can manually enter information.

Address -  Current app has it as one entire line.  This is doable since the API naturally produces address this way. I will provide two views and 
different code for each concept.

EXTRAS - We should take advantage of getting the company's website etc. Even if this is only store in the database and not visible.  It would be an 
assest from a management pov.

______________INSTRUCTIONS__________________________________________________    

1.  Gradle Scripts - build.gradle(Project...)

	ADD  classpath 'com.google.gms:google-services:3.1.0' 
	UNDER 
	classpath 'com.android.tools.build:gradle:2.3.3' (dependencies{ )

2.  Gradle Scripts - build.gradle(Module:app)

	ADD compile 'com.google.android.gms:play-services-places:11.0.2'
 	   compile 'com.google.android.gms:play-services-maps:11.0.2'
 	   compile 'com.google.android.gms:play-services-location:11.0.2'
 	   compile 'com.google.android.gms:play-services-base:11.0.2'

	UNDER
 	compile 'com.android.support:appcompat-v7:26.+'
	(dependencies{...)

3. Gradle Scripts - build.gradle(Module:app)

	ADD   apply plugin: 'com.google.gms.google-services'
	VERY BOTTOM OF SCRIPT.  OUTSIDE of Dependencies { }

4. SIGN UP FOR GOOGLE PLACES API - ADD GOOGLE-SERVICES.JSON to the project.

	Go to https://myaccount.google.com - sign in
	Once signed in go to: https://console.developers.google.com
	Beside the Google APIs logo there is a pull down menu. Click on it.  If you don't see Create project.
	Look to the far right and you will see a gear icon folder.  Click on it.  Now you will see the option to create.
	Type a name for your project. Click create.
	Now click the hamburger menu to the left of Google APIs and select Library.  Make sure your project name is now visible in the pull down as well.
	Select Google Places for Android. (you can type in Places in the search too)
	Select "enable"
	It will then say you need to create credentials.  Click the button.
	Select the API/Platform
	Select what credentials do I need.  It will then generate your API key with is needed for step 6.
	It will mention you should restrict your api.  Click on restrictions. 
	Click on Add Package name and fingerprint.
	Add your project name as listed in the manifest. 
	Then open a new window and go to https://developers.google.com/places/android-api/signup#release-cert
	Scroll down and look for the heading "Find your app's certificate information"
	We will be used the debug certificate until publishing
	**Any issues go here https://stackoverflow.com/questions/5488339/how-can-i-find-and-run-the-keytool
	**More Documentation: https://developers.google.com/places/android-api/start#api-key

	LASTLY YOU WILL NEED A GOOGLE-SERVICES JSON FILE - Go to https://developers.google.com/mobile/add
		I suggest adding the google auth.  Get to the point of where you can download the json.

	Now put the file under your app directory in the project.		
	

5. AndroidManifest.xml

	ADD <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
	AFTER
		<?xml version="1.0" encoding="utf-8"?>
		<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		 package="com.yourcompany.yourapppackage">
	BEFORE <application ...

6. AndroidManifest.xml

	ADD  <meta-data
		android:name="com.google.android.geo.API_KEY"
 	        android:value="Ajbvjv398fbv23bnfn33f8h8fh3393"/>  <-- YOUR API KEY FROM GOOGLE (It is married to youur package name)
	AFTER  Last </activity>
	BEFORE </application>

7.


TESTING:
*Things become more accurate with company name, city, country...

Gaylea Foods Mississauga - near by

Breezemaxweb mississuga -unit number

Metro Ontario Inc Dundas Street W  head office  -has a long address.

HELP: https://developers.google.com/places/android-api/


