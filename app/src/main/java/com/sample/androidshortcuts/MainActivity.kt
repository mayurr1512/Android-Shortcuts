package com.sample.androidshortcuts

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var shortcutManager: ShortcutManager
    private var dynamicShortcutCounter = 0 // To generate unique item IDs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shortcutManager = getSystemService(ShortcutManager::class.java)

        // Buttons for Dynamic Shortcuts
        val btnAddDynamicShortcut: Button = findViewById(R.id.btnAddDynamicShortcut)
        val btnRemoveDynamicShortcut: Button = findViewById(R.id.btnRemoveDynamicShortcut)
        val btnRemoveAllDynamicShortcuts: Button = findViewById(R.id.btnRemoveAllDynamicShortcuts)

        btnAddDynamicShortcut.setOnClickListener { addDynamicShortcut() }
        btnRemoveDynamicShortcut.setOnClickListener { removeLastDynamicShortcut() }
        btnRemoveAllDynamicShortcuts.setOnClickListener { removeAllDynamicShortcuts() }

        // Buttons for Pinned Shortcuts
        val btnPinItem1: Button = findViewById(R.id.btnPinItem1)
        val btnPinItem2: Button = findViewById(R.id.btnPinItem2)

        // Set text for pin buttons dynamically
        btnPinItem1.text = getString(R.string.pin_shortcut_button, btnPinItem1.tag.toString().toInt())
        btnPinItem2.text = getString(R.string.pin_shortcut_button, btnPinItem2.tag.toString().toInt())

        btnPinItem1.setOnClickListener { requestPinShortcut(btnPinItem1.tag.toString().toInt()) }
        btnPinItem2.setOnClickListener { requestPinShortcut(btnPinItem2.tag.toString().toInt()) }
    }

    private fun addDynamicShortcut() {
        dynamicShortcutCounter++
        val itemId = "item_$dynamicShortcutCounter"
        val shortLabel = "Item $dynamicShortcutCounter"
        val longLabel = "Open Item $dynamicShortcutCounter Details"

        // Create an Intent to open ItemDetailActivity
        val intent = Intent(this, ItemDetailActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = android.net.Uri.parse("shortcut_sample://item_detail?id=$itemId")
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear existing activities on new launch
        }

        val shortcut = ShortcutInfo.Builder(this, itemId)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_foreground)) // Use a relevant icon
            .setIntent(intent)
            .build()

        // Add the dynamic shortcut
        val dynamicShortcuts = shortcutManager.dynamicShortcuts
        // Limit the number of dynamic shortcuts, as per Android guidelines (max 5 total, including pinned dynamic ones)
        // You generally only have 4 dynamic slots if one static is present.
        if (dynamicShortcuts.size >= 4) { // Android usually allows 4-5 total visible shortcuts
            // Optional: Remove the oldest shortcut if at max limit
            // shortcutManager.removeDynamicShortcuts(listOf(dynamicShortcuts[0].id))
            Toast.makeText(this, "Maximum dynamic shortcuts reached. Consider removing old ones.", Toast.LENGTH_SHORT).show()
            return
        }

        // Add the new shortcut
        shortcutManager.addDynamicShortcuts(listOf(shortcut))
        Toast.makeText(this, "Dynamic Shortcut '$shortLabel' added", Toast.LENGTH_SHORT).show()
        // No explicit updateDynamicShortcuts needed here, addDynamicShortcuts handles it.
        // If you were reordering the entire list, you'd use setDynamicShortcuts.
    }

    private fun removeLastDynamicShortcut() {
        val dynamicShortcuts = shortcutManager.dynamicShortcuts
        if (dynamicShortcuts.isNotEmpty()) {
            // Remove the last added dynamic shortcut
            val shortcutIdToRemove = dynamicShortcuts.last().id
            shortcutManager.removeDynamicShortcuts(listOf(shortcutIdToRemove))
            Toast.makeText(this, "Dynamic Shortcut '$shortcutIdToRemove' removed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No dynamic shortcuts to remove", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeAllDynamicShortcuts() {
        shortcutManager.removeAllDynamicShortcuts()
        Toast.makeText(this, "All Dynamic Shortcuts removed", Toast.LENGTH_SHORT).show()
    }

    private fun requestPinShortcut(itemNum: Int) {
        val itemId = "pinned_item_$itemNum"
        val shortLabel = "Pinned Item $itemNum"
        val longLabel = "View Pinned Item $itemNum Details"

        val intent = Intent(this, ItemDetailActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = android.net.Uri.parse("shortcut_sample://item_detail?id=$itemId")
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (shortcutManager.isRequestPinShortcutSupported) {
            val pinShortcutInfo = ShortcutInfo.Builder(this, itemId)
                .setShortLabel(shortLabel)
                .setLongLabel(longLabel)
                .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background)) // Different icon for pinned
                .setIntent(intent)
                .build()

            // The result intent is passed to the launcher to be fired when the shortcut is pinned.
            // You can optionally use a PendingIntent to receive a callback in your app (optional).
            // For simplicity, we just request it.
            if (shortcutManager.requestPinShortcut(pinShortcutInfo, null)) {
                Toast.makeText(this, "Requesting to pin '$shortLabel'", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to request pin '$shortLabel'", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Pinning shortcuts not supported on this launcher.", Toast.LENGTH_LONG).show()
        }
    }
}