package com.nandaadisaputra.coroutineflow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edPlace = findViewById<AutoCompleteTextView>(R.id.ed_place)

        edPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO lifecycleScope digunakan untuk membuat scope coroutine yang aware terhadap lifecycle.

                // Sehingga ketika lifecycle dihapus -seperti dalam keadaan onPause/onStop- maka Coroutine
                // juga akan dibersihkan. Alhasil, aplikasi terhindar dari memory leak.
                lifecycleScope.launch {
                    viewModel.queryChannel.value = s.toString()
                }
            }
        })
        viewModel.searchResult.observe(this) { placesItem ->
            val placesName = placesItem.map { it.placeName }
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.select_dialog_item, placesName)
            adapter.notifyDataSetChanged()
            edPlace.setAdapter(adapter)
        }
    }
}