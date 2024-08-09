package com.genthur.suitmediatest.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genthur.suitmediatest.R
import com.genthur.suitmediatest.data.remote.response.DataItem
import com.genthur.suitmediatest.databinding.ActivityThirdBinding
import com.genthur.suitmediatest.ui.adapter.UserAdapter
import com.genthur.suitmediatest.ui.viewmodel.MainViewModel
import com.genthur.suitmediatest.ui.viewmodel.ViewModelFactory
import com.genthur.suitmediatest.util.LoadingStateAdapter

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        setSupportActionBar(binding.toolbar)
        setTitle(null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.elevation = 0f

        userAdapter = UserAdapter()
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@ThirdActivity)
            adapter = userAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    userAdapter.retry()
                }
            )
        }

        mainViewModel.getLists().observe(this) {
            userAdapter.submitData(lifecycle, it)
            showLoading(false)

        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {
                val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
                intent.putExtra("EXTRA_FIRST_NAME", data.firstName)
                intent.putExtra("EXTRA_LAST_NAME", data.lastName)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}