package com.example.layoutsinjectpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.widget.ConstraintSet
import coil.compose.rememberImagePainter
import com.example.layoutsinjectpackcompose.ui.theme.LayoutsInJectpackComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJectpackComposeTheme {
                // A surface container using the 'background' color from the theme
                ImageList()
            }
        }
    }
}
@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String){
    Row(modifier = modifier.height(IntrinsicSize.Min)
        .background(color = Color.White)){
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )
        Divider(color = Color.Blue, modifier = Modifier
            .fillMaxHeight()
            .width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}
@Preview
@Composable
fun TwoTextsPreview(){
    LayoutsInJectpackComposeTheme() {
        TwoTexts(text1 = "HIHI", text2 = "COMPOSE")
    }
}
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout() {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "THIS vERY vERYvERYvERYvERYvERYvERYvERYvERYvERYvERYvERYvERYvERY",
            Modifier.constrainAs(text){
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}
//@Preview
//@Composable
//fun largeConstraintPreview(){
//    LayoutsInJectpackComposeTheme {
//        LargeConstraintLayout()
//    }
//}
@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout() {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1){
                top.linkTo(parent.top,margin = 16.dp)
            }
        ){
            Text("버튼 1")
        }
        Text("Text",Modifier.constrainAs(text){
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(onClick = { /*TODO*/ },
                modifier = Modifier.constrainAs(button2){
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(barrier)
                }
        ) {
            Text("버튼 2")
        }
    }
}
//@Preview
//@Composable
//fun ConstraintLayoutContentPreview(){
//    LayoutsInJectpackComposeTheme {
//        ConstraintLayoutContent()
//    }
//}
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ){ measurables, constraints ->

        val rowWidths = IntArray(rows) { 0 }

        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->

            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }
            val width = rowWidths.maxOrNull()
                ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

            val height = rowHeights.sumOf { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

            val rowY = IntArray(rows) { 0 }
            for(i in 1 until rows) {
                rowY[i] = rowY[i-1] + rowHeights[i-1]
            }

            layout(width, height){
                val rowX = IntArray(rows) { 0 }

                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(
                        x = rowX[row],
                        y = rowY[row]
                    )
                    rowX[row] += placeable.width
                }
            }
        }
    }

@Composable
fun Chip(modifier: Modifier = Modifier, text: String){
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}
val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)
//@Preview
//@Composable
//fun ChipPreview(){
//    LayoutsInJectpackComposeTheme() {
//        Chip(text = "HI ~")
//    }
//}
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    ){
        Surface(
            modifier =  Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ){
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
    }


    }
}
@Composable
fun LayoutsCodelab(){
    Scaffold(
        topBar = {
        TopAppBar(
            modifier = Modifier.background(MaterialTheme.colors.error),
            title = {
                Text(text = "LayoutCodelab")
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Tab, contentDescription = null)
                    }

            },

        )
    }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}
@Composable
fun BodyContent(modifier: Modifier = Modifier){
//    Column(modifier = modifier) {
//        Text(text = "Hi there!")
//        Text(text = "Thanks for going through")
//    }
//    MyOwnColumn(modifier.padding(8.dp)) {
//        Text("MyOwnColumn")
//        Text("MyOwnColumn2")
//        Text("MyOwnColumn3")
//        Text("MyOwnColumn4")
//
//    }

        Row(modifier = modifier
            .background(color = Color.LightGray, shape = RectangleShape)
            .padding(16.dp)
            .size(200.dp)
            .horizontalScroll(rememberScrollState())){
            StaggeredGrid(modifier = modifier,rows = 5) {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }


}
@Composable
fun SimpleList(){
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100){
            Text("Item #$it")
        }
    }
}
@Composable
fun ImageListItem(index: Int){
    Row(verticalAlignment = Alignment.CenterVertically){

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
        contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}
@Composable
fun ImageList(){
    val listSize = 100
    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column(){
        Row{
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }

        }
        LazyColumn(state = scrollState){
            items(listSize){
                ImageListItem(it)
            }
        }
    }
}
fun Modifier.firstBaselineToTop(
    firstBaselineToTop : Dp
) = this.then(
    layout { measureable, constraints ->
        val placeable = measureable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY

        layout(placeable.width, height){
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = Modifier,
        content = content
    ){
        measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight){
            placeables.forEach{ placeable ->
                placeable.placeRelative(x = 0, y = yPosition)

                yPosition += placeable.height
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun TextWithPaddingToBaselinePreview(){
////        Text("Hi there", Modifier.firstBaselineToTop(32.dp))
//    LayoutsInJectpackComposeTheme() {
//        LayoutsCodelab()
//        Text("hi")
//    }
//
//}
//
//@Preview
//@Composable
//fun TextWithNormalPaddingPreview(){
//    LayoutsInJectpackComposeTheme() {
//        Text("Hi there",Modifier.padding(top = 32.dp))
//
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LayoutsInJectpackComposeTheme {
//        PhotographerCard()
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun LayoutsCodelabPreview(){
//    LayoutsInJectpackComposeTheme {
//     //   BodyContent()
//    }
//}
