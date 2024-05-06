import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material.TextButton
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import androidx.compose.material.Text
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.a5046protoytpe.R

//import androidx.compose.runtime.Composable
//import androidx.compose.ui.viewinterop.AndroidView
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapView
//import com.google.android.gms.maps.OnMapReadyCallback

@Composable
fun DateSelector(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = remember { calendar.get(Calendar.YEAR) }
    val month = remember { calendar.get(Calendar.MONTH) }
    val day = remember { calendar.get(Calendar.DAY_OF_MONTH) }

    TextButton(onClick = {
        DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                // Use the date chosen by the user
                onDateSelected("$dayOfMonth/${monthOfYear + 1}/$year")
            },
            year,
            month,
            day
        ).show()
    }) {
        Text("Choose Date")
    }
}

//@Composable
//fun ActivitySummary(selectedDate: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = selectedDate,
//            style = MaterialTheme.typography.headlineMedium
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        // This canvas draws a simplified waveform representation.
//        Canvas(modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)) {
//            val path = Path().apply {
//                moveTo(0f, size.height / 2)
//                cubicTo(
//                    size.width / 4, 0f,
//                    size.width * 3 / 4, size.height,
//                    size.width, size.height / 2
//                )
//            }
//            drawPath(path, color = Color.Blue, style = Stroke(width = 8.dp.toPx()))
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        TotalKilocalories()
//        Statistic("Calories", "480", Color.Green)
//        Statistic("Time", "24:06", Color.Red)
//        Statistic("Kilometers", "4.84", Color.Red)
//    }
//}

@Composable
fun ActivitySummary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Thu, 08 July",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(64.dp))
        // This canvas draws a simplified waveform representation.
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)) {
            val path = Path().apply {
                moveTo(0f, size.height / 2)
                cubicTo(
                    size.width / 4, 0f,
                    size.width * 3 / 4, size.height,
                    size.width, size.height / 2
                )
            }
            drawPath(path, color = Color.Blue, style = Stroke(width = 8.dp.toPx()))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TotalKilocalories()
        Statistic("Calories", "480", Color.Green)
        Statistic("Time", "24:06", Color.Red)
        Statistic("Kilometers", "4.84", Color.Red)
    }
}

@Composable
fun TotalKilocalories() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "4880",
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Green, shape = MaterialTheme.shapes.small)
                .padding(8.dp), // Use padding to align the content inside Box
        ) {
            Text(
                text = "+14%",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center) // Correct way to align content
            )
        }
    }
}


@Composable
fun Statistic(label: String, value: String, indicatorColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(indicatorColor, shape = MaterialTheme.shapes.small),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun MapImage() {
    // Replace 'map' with the correct drawable resource name for your map image.
    Image(
        painter = painterResource(id = R.drawable.map),
        contentDescription = "Map Image",
        modifier = Modifier
            .height(200.dp) // Set to desired height for the image
            .fillMaxWidth() // The image will fill the width of the parent
 // This will clip the image to the bounds of the parent if necessary
    )
}

//@Composable
//fun MapViewContainer(onMapReady: (GoogleMap) -> Unit) {
//    val mapView = rememberMapViewWithLifecycle()
//
//    AndroidView({ mapView }) { mapView ->
//        mapView.getMapAsync(OnMapReadyCallback { googleMap ->
//            onMapReady(googleMap)
//        })
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewActivitySummary() {
    ActivitySummary()
}
