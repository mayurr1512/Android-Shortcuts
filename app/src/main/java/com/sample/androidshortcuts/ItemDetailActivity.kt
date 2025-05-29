package com.sample.androidshortcuts

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar?.title = getString(R.string.item_detail_activity_title)

        val tvItemId: TextView = findViewById(R.id.tvItemId)

        // Get the item ID from the intent
        val intent = intent
        if (intent != null && intent.data != null) {
            val itemId = intent.data?.getQueryParameter("id")
            if (itemId != null) {
                tvItemId.text = getString(R.string.item_id_placeholder, itemId)
            } else {
                tvItemId.text = "No Item ID received."
            }
        } else if (intent != null && intent.hasExtra("itemId")) { // Fallback for older intents if you pass directly
            val itemId = intent.getStringExtra("itemId")
            tvItemId.text = getString(R.string.item_id_placeholder, itemId)
        } else {
            tvItemId.text = "No Item ID received."
        }
    }
}