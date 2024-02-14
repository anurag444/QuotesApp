package com.example.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.quotesapp.screens.QuoteDetail
import com.example.quotesapp.screens.QuotesListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch{
            DataManager.loadAssetsFromFile(applicationContext)
        }
        setContent {
            App()
        }
    }
}

@Composable
fun App(){
    if (DataManager.isDataLoaded.value){
        if(DataManager.currentPage.value == Pages.HOME){
            QuotesListScreen(data = DataManager.data) {
                DataManager.switchPages(quote = it)
            }
        }else{
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
        }

    }
}

enum class Pages{
    HOME,
    DETAIL
}
