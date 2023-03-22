package com.example.k1109_pyj_230321

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch11_jetpack.OneFragment
import com.example.ch11_jetpack.ThreeFragment
import com.example.ch11_jetpack.TwoFragment
import com.example.k1109_pyj_230321.databinding.ActivityMainBinding
import com.example.k1109_pyj_230321.model.PageListModel
import com.example.k1109_pyj_230321.recycler.MyAdapter
import com.example.k1109_pyj_230321.MyApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    // 프래그먼트 처리
    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 바인딩 작업
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 드로어 기능
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened,
            R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 프래그먼트 뷰 처리
        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
    }

    // 액션바 토글버튼 드로어 이벤트 처리 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}