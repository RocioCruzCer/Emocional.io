package mx.edu.dsi_code.notasmvvm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import mx.edu.dsi_code.notasmvvm.ui.theme.Purple40

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteIputText(modifier: Modifier = Modifier,
                 text:String,
                 label:String,
                 maxline: Int = 1,
                 onTextChange:(String)->Unit,
                 onImeAction: ()-> Unit ={}
                 ){
    ///2.-controlamos la accion del teclado desde su controlador verdadero
   val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        maxLines = maxline, label = { Text(text =label )},
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }), modifier = modifier)
}

@Composable
fun NoteButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick:() -> Unit,
    enabled: Boolean = true
){
    Button(onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple40
        )
        ){
        Text(text = text)
    }
}

