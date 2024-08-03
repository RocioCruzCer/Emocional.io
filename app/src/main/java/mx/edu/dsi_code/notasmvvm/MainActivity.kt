package mx.edu.dsi_code.notasmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.dsi_code.notasmvvm.data.NotesDataSource
import mx.edu.dsi_code.notasmvvm.model.Note
import mx.edu.dsi_code.notasmvvm.screen.NoteScreen
import mx.edu.dsi_code.notasmvvm.ui.theme.NotasMVVMTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import mx.edu.dsi_code.notasmvvm.R.color.purple_200
import mx.edu.dsi_code.notasmvvm.ui.theme.Purple80
import mx.edu.dsi_code.notasmvvm.ui.theme.Purpura20

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasMVVMTheme {
                // A surface container using the 'background' color from the theme
               NotasMVVMTheme {
                   MaterialTheme{
                       //inicio surface
                       Surface(
                           modifier = Modifier.fillMaxSize(),
                           color = MaterialTheme.colorScheme.onPrimary
                       ) {
                           val systemUIController = rememberSystemUiController()
                           SideEffect {
                               systemUIController.setStatusBarColor(
                                   color = Purpura20,
                               )
                           }

                           val notes = remember{
                               mutableListOf<Note>()
                           }
                           NoteScreen(notas= notes,
                               onRemoveNote ={
                                             notes.remove(it)
                               },
                               onAddNote = {
                                   notes.add(it)
                               })
                       }
                       ///fin de surface
                   }
               }
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotasMVVMTheme {
        /*Pasamos los parametros necesarios para nuestro constructor*/
        /*Recuerda que el valor unit significa que non regresa ningun valor de retorno es
        * decir funciona como un void*/
      NoteScreen(notas= emptyList(), onRemoveNote ={}, onAddNote = {})
    }
}
























