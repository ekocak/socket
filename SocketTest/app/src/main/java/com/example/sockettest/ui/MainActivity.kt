package com.example.sockettest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sockettest.R
import com.example.sockettest.databinding.ActivityMainBinding
import com.example.sockettest.extensions.observeNonNull
import com.example.sockettest.ui.base.BaseActivity
import com.example.sockettest.ui.costumerservice.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private val chatAdapter by lazy { ChatAdapter(viewModel::onActionButtonClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //binding.messages.setAdapter(chatAdapter, this@MainActivity)
        binding.messages.adapter = chatAdapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.run {
            observeNonNull(lastMessages) {
                chatAdapter.submitList(it)
            }
        }
    }

}