package com.example.shoppinglist

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShoppingList()
                }
            }
        }
    }
}

data class ShoppingListItemData(var itemName: String,
                            var itemQuantity: String,
                            var isEditing: Boolean = false)

@Composable
fun ShoppingListItem(item: ShoppingListItemData,
                     onEditClick: () -> Unit,
                     onDeleteClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp, 0.dp, 8.dp)
            .border(
                border = BorderStroke(2.dp, Color(0xFF108786)),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column() {
            Text("Name: ${item.itemName}", modifier = Modifier.padding(8.dp))

            Text("Quantity: ${item.itemQuantity}", modifier = Modifier.padding(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = {onEditClick()}, modifier = Modifier.padding(12.dp)) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }

            Button(onClick = {onDeleteClick()}, modifier = Modifier.padding(12.dp)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }
        }
    }
}

@Composable
fun ShoppingItemEditor() {

}

@Composable
fun ShoppingItemDelete() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    var itemsList by rememberSaveable { mutableStateOf(listOf<ShoppingListItemData>()) }
    var showDialogBox by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("1") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(onClick = {showDialogBox = true}, modifier = Modifier.padding(0.dp,12.dp,0.dp,8.dp)) {
            Text("Add Item")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            for(i in itemsList) {
                item {
                    ShoppingListItem(i, {}, {})
                }
            }
//             OR
//            items(itemsList){
//                ShoppingListItem(it, {}, {})
//            }
        }
    }

    if(showDialogBox){
        BasicAlertDialog(onDismissRequest = {showDialogBox = false}) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFFE6FBFB), // Background color
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text("Add Shopping Item",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black)

                OutlinedTextField(value = itemName,
                    onValueChange = {itemName = it},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    label = {Text("Enter Item Name")},
                    textStyle = TextStyle(
                        color = Color.Black,        // Set text color
                        fontSize = 20.sp,          // Set text size here (adjust as needed)
                        fontWeight = FontWeight.Normal // Optional: change font weight if desired
                    )
                )

                OutlinedTextField(value = itemQuantity,
                    onValueChange = {itemQuantity = it},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    label = {Text("Enter Quantity")},
                    textStyle = TextStyle(
                        color = Color.Black,        // Set text color
                        fontSize = 20.sp,          // Set text size here (adjust as needed)
                        fontWeight = FontWeight.Normal // Optional: change font weight if desired
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(onClick = {
                        if(itemName != "" && itemQuantity != ""){
                            var newItem = ShoppingListItemData(
                                itemName,
                                itemQuantity
                            )

                            itemsList = itemsList + newItem
                            itemName = ""
                            itemQuantity = "1"
                            showDialogBox = false
                        }
                    }) {
                        Text("Add")
                    }
                    Button(onClick = {
                        itemName = ""
                        itemQuantity = "1"
                        showDialogBox = false
                    }) {
                        Text("Cancle")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ShoppingListTheme {
        ShoppingList()
    }
}