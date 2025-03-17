# SessionLogger Mobile Application
This is the source code for the SessionLogger Android mobile application.

## What is SessionLogger BLE mobile app?

SessionLogger is a mobile application designed to assist researchers in collecting labeled Bluetooth Low Energy (BLE) datasets for research purposes. It enables users to log essential metadata, such as data type and location labels, with a simple and intuitive interface. With seamless API integration, SessionLogger makes it easy to log collection session data and transmit them to a remote API server.

Currently, SessionLogger supports two types of data collection:

Static Data Collection: Records data at fixed reference points.
Dynamic Data Collection: Captures movement across different locations, such as transitions between areas.

## Why download SessionLogger?

If your research involves BLE data collection, SessionLogger simplifies the process. The app offers a user-friendly interface that allows you to easily configure parameters, select data types, and start collecting labeled data. Whether tracking static data points or recording dynamic movement patterns, SessionLogger provides a structured and reliable approach to data logging and transmission. Additionally, the source code of SessionLogger is publicly available, allowing for future collaboration and improvements.

## How does it work?

Once you launch the SessionLogger app, you are presented with an expandable panel designed to specify all necessary configurations for the collection session, namely: 
- Configure API settings:  Enter the API URL and port number where the collected data will be sent.
- Select Data Type: Choose between Static or Dynamic data collection and specify the collection session number. The
collection number indicates the Reference Point (RP) for 
static data or the trajectory number for dynamic data. 
- Set Location Label: Indicate whether you are inside or outside the target area using a simple toggle switch. When set to “OUT”, the switch is
turned off; when set to “IN”, the switch is turned on.

Once all fields are completed, tap the filled icon button to confirm the data entries and proceed. Upon the click, the expandable panel disappears, transitioning to a minimal data collection view to:
-  Start Data collection: Tap "Start Collecting" to log data. A timer starts, and you can dynamically update your location if needed.
- Stop Data Collection: When finished, tap "Stop Collecting" to end the session and send the final dataset to the API.

Each time you click the action button, a JSON object is sent to the API server with all user inputs and the timer value.

## Building SessionLogger from the source code
* Clone the project repository
* Open the project directory in [Android Studio](https://developer.android.com/studio)
* Wait for Gradle sync to finish
* In the Build Variants tool window, select the desired variant: release or debug
* Build the project with Build > Build Project and run it on an emulator or a connected mobile device with Run > Run 'app' or Run > Debug 'mobile'
* You can also build an APK installation package with Build > Build Bundle(s) / APK(s) > Build APK(s)... - the APK will be built, and a notification in Studio will display a link to its location on the disk.

## Development
The main application code lies in the <project_directory>/app/src/main/java/com/it2s/sessionlogger directory. The main application logic is structured into different packages:

* core – Contains core utilities and common logic.
* data – Manages data sources, including APIs and databases.
* domain – Defines use cases and business logic.
* presentation – Handles the UI and user interaction. The main screen logic can be found here.
* ui.theme – Contains styling and theme-related components.

The application follows the MVVM (Model-View-ViewModel) architecture, keeping business logic separate from UI components.

The MainActivity.kt file serves as the application's entry point, while BusTrackerApplication.kt handles global app configurations.

Resources such as layouts, images, and strings are stored in the <project_directory>/app/src/main/res directory.

## Additional Information
The app can be found on the [Google PlayStore]().

[Learn more about the SessionLogger mobile app]().

For more information about the activities of the Embedded Systems group at Instituto de Telecomunicações (Aveiro, Portugal), please visit [www.it.pt](https://www.it.pt/Groups/Index/37).

## License

> **Copyright 2025 [Instituto de Telecomunicações]**  
>  
> Licensed under the **Apache License, Version 2.0** (the "License");  
> You may not use this file except in compliance with the License.  
> You may obtain a copy of the License at:  
>  
> [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)  
>  
> Unless required by applicable law or agreed to in writing, software  
> distributed under the License is provided **"AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND**, either express or implied.  
> See the License for the specific language governing permissions  
> and limitations under the License.
