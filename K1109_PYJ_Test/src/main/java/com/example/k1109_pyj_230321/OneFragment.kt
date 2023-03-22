package com.example.ch11_jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.k1109_pyj_230321.MyApplication
import com.example.k1109_pyj_230321.databinding.FragmentOneBinding
import com.example.k1109_pyj_230321.model.PageListModel
import com.example.k1109_pyj_230321.recycler.MyAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis


class OneFragment : Fragment() {

    lateinit var binding: FragmentOneBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root

        }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 버튼 클릭 시 코루틴 작업.
        binding.button.setOnClickListener {
            // Channel : 큐 알고리즘과 비슷
            val channel = Channel<Int>()

            // 스코프는 작업 2방향으로 나눠서 작업
            // Dispatchers.Default -> 오래 걸리는 작업
            val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
            backgroundScope.launch {
                var sum = 0
                var time = measureTimeMillis {
                    for (i in 1..2_000_000_000) {
                        sum += i
                    }
                }
                Log.d("lsy", "time : $time")
                channel.send(sum)
            }

            // 사용자 이벤트를 처리하는 스코프
            // 메인 스레드, UI 스레드, 화면을 구현하는 부분
            val mainScope= GlobalScope.launch(Dispatchers.Main) {
                channel.consumeEach {
                    binding.resultView.text = "sum : $it"
                }
            }
        }

        val serviceKey = "w3MapgHp1sUz8L6CdOfUTYa4QYavseexy0ZhKfNzNLiFidOlgFhx0spx6MXsgcUJ"

        val networkService = (requireActivity().applicationContext as MyApplication).networkService

        val userListCall = networkService.getList(serviceKey)
        Log.d("lsy", "url:" + userListCall.request().url().toString())

        userListCall.enqueue(object : Callback<PageListModel> {
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                val userList = response.body()
                Log.d("lsy", "userList data 값 : ${userList?.body}")

                binding.recyclerView.adapter = MyAdapter(requireActivity(), userList?.body)
                binding.recyclerView.addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                )
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                call.cancel()
            }
        })
    }

}