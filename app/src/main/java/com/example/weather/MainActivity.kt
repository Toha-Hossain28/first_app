package com.example.weather

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherScreen()
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}

@Preview
@Composable
fun WeatherScreen(){
    Box(modifier =Modifier
        .fillMaxSize()
        .background(
            brush = Brush.
            horizontalGradient(
                colors = listOf(
                    Color(android.graphics.Color.parseColor("#59469d")),
                        Color(android.graphics.Color.parseColor("#643d67"))
                )
            )
        )
    )
    {
        Column(modifier = Modifier.fillMaxSize()){
            LazyColumn(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    Text(text= "Mostly Cloudy",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center
                    )

                    Image(painter = painterResource(id = R.drawable.cloudy_sunny),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                    )

                    //display date and time
                    Text(text = "Mon July 17 | 10:00 AM",
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top= 8.dp),
                        textAlign = TextAlign.Center
                    )

                    //display temperature details
                    Text(text = "25°",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(text = "H:27 L:18",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top= 8.dp),
                        textAlign = TextAlign.Center
                    )

                    // Box containing weather details
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .background(
                            color = colorResource(id = R.color.purple),
                            shape = RoundedCornerShape(25.dp)
                        )
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            WeatherDetailItems(icon = R.drawable.rain,
                                value = "22%",
                                label = "Rain"
                            )
                            WeatherDetailItems(icon = R.drawable.humidity,
                                value = "22%",
                                label = "Humidity"
                            )
                            WeatherDetailItems(icon = R.drawable.wind,
                                value = "22%",
                                label = "Wind Speed"
                            )
                        }
                    }
                    //Display "Today"
                    Text(text = "Today",
                        fontSize = 20.sp,
                        color = colorResource(R.color.white),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
                //display future hourly forecast using lazyRow
                item {
                    LazyRow(modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(items) { item ->
                            FutureModelViewHolder(item)
                        }
                    }
                }

                //Display "Future" label and next 7 day button
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Future",
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.white),
                            modifier = Modifier.weight(1f)
                        )
                        Text(text = "Next 7 Days >",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight.Bold

                        )
                    }
                }

                items(dailyItems){
                    FutureItem(item = it)
                }
            }
        }
    }
}
//Display each future daily forecast items
@Composable
fun FutureItem(item:FutureModel){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.day,
            color = colorResource(id = R.color.white),
            fontSize = 14.sp
        )
        Image(painter = painterResource(id = getDrawableResourceId(picPath = item.picPath)),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )
        Text(
            text = item.status,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = colorResource(R.color.white),
            fontSize = 14.sp
        )
        Text(
            text = "${item.lowTemp}°",
            modifier = Modifier
                .padding(end = 16.dp),
            color = colorResource(R.color.white),
            fontSize = 14.sp
        )
        Text(
            text = "${item.highTemp}°",
            color = colorResource(R.color.white),
            fontSize = 14.sp
        )

    }
}

@Composable
fun getDrawableResourceId(picPath:String):Int{
    return when(picPath){
        "storm"->R.drawable.storm
        "cloudy"->R.drawable.cloudy
        "windy"->R.drawable.windy
        "cloudy_sunny"->R.drawable.cloudy_sunny
        "sunny"->R.drawable.sunny
        "rainy"->R.drawable.rainy
        else -> R.drawable.sunny

    }
}

//Sample daily data
val dailyItems = listOf(
    FutureModel("Sat","sunny","sunny",25,20),
    FutureModel("Sun","cloudy","cloudy",25,20),
    FutureModel("Mon","wind","wind",25,20),
    FutureModel("Thu","rainy","rainy",25,20),
    FutureModel("Wed","storm","storm",25,20),
    FutureModel("Thr","cloudy_sunny","Cloudy Sunny",25,20)
)

//viewholder for hourly forecast items
@Composable
fun FutureModelViewHolder(model:hourlyModel){
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(
                color = colorResource(id = R.color.purple),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = model.hour,
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(painter = painterResource(id = when(model.picPath){
            "cloudy"->R.drawable.cloudy
            "sunny"->R.drawable.sunny
            "wind"->R.drawable.windy
            "rainy"->R.drawable.rainy
            "storm"->R.drawable.storm
            else->R.drawable.sunny
        }
        ),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${model.temp}",
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}



//Sample hourly Data
val items = listOf(
    hourlyModel("9 pm",28, "cloudy"),
    hourlyModel("10 pm",29, "sunny"),
    hourlyModel("11 pm",27, "wind"),
    hourlyModel("12 pm",25, "rainy"),
    hourlyModel("1 am",20, "storm")

)

@Composable
fun WeatherDetailItems(icon:Int,
                       value:String,
                       label:String){
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Text(text = label,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
    }
}