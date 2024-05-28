//package com.gyub.implementationtestexample
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.AsyncImage
//import com.gyub.implementationtestexampleexample.ui.theme.implementationtestexampleExampleTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            implementationtestexampleExampleTheme {
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    IssueList()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun IssueList(viewModel: MainViewModel = hiltViewModel()) {
//    LaunchedEffect(key1 = null) {
//        viewModel.fetchData("JakeWharton", "hugo")
//    }
//    val githubData = viewModel.githubData.collectAsState().value
//    when (githubData) {
//        is Result.Loading -> {
//            Text(text = "로딩중")
//        }
//
//        is Result.Success -> {
//            LazyColumn {
//                items(githubData.data) { item: GithubType ->
//                    when (item) {
//                        is GithubType.Issue -> IssueCard(issue = item)
//                        is GithubType.Banner -> BannerCard(banner = item)
//                    }
//                }
//            }
//        }
//    }
//
//}
//
//@Composable
//fun BannerCard(banner: GithubType.Banner) {
//    val data = banner.data
//    AsyncImage(model = data.url, contentDescription = "banner")
////    Image(painter = , contentDescription ="banner")
//}
//
//@Composable
//fun IssueCard(issue: GithubType.Issue) {
//    val data = issue.data
//    Row {
//        Text(text = "#${data.number}")
//        Text(text = data.title.orEmpty())
//    }
//}