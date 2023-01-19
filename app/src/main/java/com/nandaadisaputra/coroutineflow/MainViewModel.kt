package com.nandaadisaputra.coroutineflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandaadisaputra.coroutineflow.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val accessToken =
        "pk.eyJ1IjoibmFuZGFhZGlzYXB1dHJhIiwiYSI6ImNsZDJjbW15NDA3ZTIzcG9naHNqcXNmNjEifQ.eTeu7JqNRBtowadhanwRPA"

    //MutableStateFlow merupakan flow yang berfungsu untuk menyimpan state data.
    // Komponen ini mirip dengan Livedata tetapi dalam bentuk Flow.
    // Berbeda dengan Flow yang merupakan cold stream, StateFlow
    // merupakan contoh dari hot stream. Sehingga ia akan selalu
    // membaca value walaupun tidak ada yang mengirimkan data.
    val queryChannel = MutableStateFlow("")

    val searchResult = queryChannel
        //TODO debounce : Memastikan agar eksekusi selanjutnya berjalan jika ada jeda 300 millisecond.

        // Hal ini akan sangat bermanfaat ketika melakukan pencarian.
        // Dengan menggunakan debounce, Anda mencegah aplikasi untuk melakukan
        // request API di setiap kali mengetikkan huruf, namun menunggu kata
        // tersebut diketik terlebih dahulu. Hal ini tentunya akan menghemat
        // bandwith dan memberikan user experience yang menyenangkan bagi pengguna.
        .debounce(300)
        .distinctUntilChanged()
        //filter : melakukan filter jika yang diketik hanya spasi atau masih kosong,
        // sehingga bisa mencegah melakukan request yang tidak berguna.
        .filter {
            it.trim().isNotEmpty()
        }
        //mapLatest : Berfungsi kala  melakukan transformasi flow menjadi data yang baru dengan hanya
        // menghasilkan nilai terakhir,sehingga ketika ada nilai baru maka data yang lama akan digantikan.
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accessToken).features
        }
        //asLiveData : Mengubah Flow menjadi LiveData, merupakan extension yang di dapat dari library lifecycle-livedata-ktx.
        .asLiveData()
}