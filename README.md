# Android Shortcuts Sample

This repository provides a comprehensive Android sample project demonstrating the implementation and usage of **Android App Shortcuts**. These shortcuts allow users to quickly access specific actions or content within your application directly from the launcher icon, enhancing user experience and engagement.

The sample showcases three main types of shortcuts:

1.  **Static Shortcuts:** Defined in an XML resource file, these are fixed shortcuts that appear consistently for your app.
2.  **Dynamic Shortcuts:** Created and managed programmatically at runtime, offering flexibility to adapt to user behavior or app content.
3.  **Pinned Shortcuts:** User-initiated shortcuts that can be "pinned" directly to the device's launcher screen for persistent, one-tap access.


## 🌟 Features

* **Static Shortcut:** A fixed "About Us" shortcut to demonstrate basic XML-based shortcut definition.
* **Dynamic Shortcuts:** Buttons to add and remove "Item Detail" shortcuts programmatically, showcasing how to manage a list of dynamic shortcuts based on recent activity or frequently accessed content.
* **Pinned Shortcuts:** Functionality to request pinning specific "Item Detail" shortcuts directly to the home screen, illustrating persistent shortcut creation.
* **Target Activities:** Dedicated activities (`AboutUsActivity`, `ItemDetailActivity`) to handle intents from the shortcuts and display relevant information.
* **Kotlin-based:** The entire project is implemented using Kotlin, showcasing modern Android development practices.


## 🛠️ Requirements

* Android Studio
* Android SDK (API 25+ recommended for Dynamic and Static Shortcuts, API 26+ required for Pinned Shortcuts)
* A device or emulator running Android 7.1 (API 25) or higher.


## 💡 How to Use and Test

Once the app is installed, you can test the different shortcut types:

### 1. Static Shortcut (About Us)

* **Long-press** on the app icon on your device's launcher (home screen).
* You should see a shortcut labeled **"About"** (or "About Us").
* Tap this shortcut to open the `AboutUsActivity` within the app.

### 2. Dynamic Shortcuts (Item Details)

* Open the `AndroidShortcutsSample` app.
* Tap the **"Add Dynamic Shortcut"** button multiple times.
* Now, **long-press** the app icon on your launcher again. You should see new shortcuts appearing, labeled "Item 1", "Item 2", etc. (The number of visible dynamic shortcuts depends on the launcher's capabilities, usually 4-5 in total including static ones).
* Tap any of these "Item" shortcuts to open the `ItemDetailActivity` displaying the corresponding Item ID.
* Experiment with the **"Remove Last Dynamic Shortcut"** and **"Remove All Dynamic Shortcuts"** buttons to see how the list updates on the launcher.

### 3. Pinned Shortcuts (Persistent Item Access)

* **Requires Android 8.0 (API 26) or higher.**
* Open the `AndroidShortcutsSample` app.
* Tap the **"Pin Item 1 Shortcut"** or **"Pin Item 2 Shortcut"** button.
* Your device's launcher will prompt you to confirm if you want to pin the shortcut to the home screen.
* Once confirmed, a new, independent icon for "Pinned Item 1" (or "Pinned Item 2") will appear directly on your home screen.
* Tapping this icon will launch the `ItemDetailActivity` for that specific item.
* **Note:** Pinned shortcuts are persistent. They remain on the home screen even if you uninstall the app (though they will become non-functional) or remove dynamic shortcuts. To remove a pinned shortcut, long-press it on the home screen and drag it to the "Remove" or "Uninstall" option, just like a regular app icon.

## 📚 Resources

* **Medium Article:** [Simplifying App Navigation with Android Shortcuts](https://medium.com/p/9ae7352ad969/edit)

---
