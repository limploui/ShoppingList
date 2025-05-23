package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import androidx.compose.runtime.mutableStateListOf



//Mainactivity erbt von ComponentActivity. Dabei erbt die neue Klasse MainActivity ein Set an
// Funktionen wie savedInstanceState: Bundle?, die erweitert oder überschrieben werden können.
// Das wird hier auch direkt gemacht. Durch super wird der Zugriff auf die unteren Klassen möglich
//gemacht
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // weil kein primary constructor da ist, wird mit super (secondaryConstrcutor neue
        // Klassen initialisert oder Funtionen)
        super.onCreate(savedInstanceState)
        // App nimmt alles an Bildschirm ein
        enableEdgeToEdge()
        // App zeigt SimpleTextfield und VisualList
        setContent {
            Column(){
            ShoppingListTheme {
                SimpleTextField()
                VisualList()
                }
            }
            }
        }
    }

// Jedes Element der Liste ist Teil dieser Klasse
// Klasse enthält die Variablen Name und Amount
class Groceries{
    var name = ""
    var amount = ""
}

// braucht flexible Klasse, um das UI updaten zu können bei neuen
// Neue Variable, die Einkaufsliste ist in dem Fall eine Array List, aus Grocery-Klassen
var einkaufsListe = mutableStateListOf<Groceries>()

// Textfelder zum Eingeben von Element und der Anzahl davon
// Composable ist eine Annotation, die die Funktion zusammensetzbar macht, sie kann also Variablen
// Entgegennehmen und damit was machen.
@Composable
fun SimpleTextField() {
    // In einer Spalte werden die InputField für Text und Amount ausgegeben, der Button auch
    Column (modifier = Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            //ermöglicht am Handy nur Texteingabe
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Milch, Beeren, Brot...") }
        )
        var amount by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            // ermöglicht nur Eingabe von Zahlen am Handy als String, nciht als Int oder so
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Wie viel?") },
        )

        //Button der die eingegeben Werte aufnimmt und übergibt
        ElevatedButton(onClick = { addToList(amount.text, text.text)  },
        ){
            Text("Auf die Liste!")
        }
    }
}


// fügt neues Objekt zur Liste hinzu
// Die Keyboardtypes sind alles Strings, die Übergeben werden
fun addToList(amount: String, grocery: String) {
    if (amount != "" && grocery != "") {
        val newItem = Groceries()
        newItem.name = grocery
        newItem.amount = amount
        einkaufsListe.add(newItem)


    } else {
    return
    }
}

// soll die Liste visualieren indem es über diese iteriert
@Composable
fun VisualList() {
    Column(modifier = Modifier.padding(16.dp)) {
    /*Text(
        text = "Ich bin da!"

    )*/


        einkaufsListe.forEach { item ->
            Text(

                text = "${item.amount}x ${item.name}",
                modifier = Modifier.clickable { einkaufsListe.remove(item) }
            )


        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListPreview() {
    ShoppingListTheme {
        SimpleTextField()
        VisualList()
    }
}